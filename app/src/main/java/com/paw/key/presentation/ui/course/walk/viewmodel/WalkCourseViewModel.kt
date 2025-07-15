package com.paw.key.presentation.ui.course.walk.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.vectormap.LatLng
import com.paw.key.core.util.PhotoUtils
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.domain.model.entity.walkcourse.CoordinateEntity
import com.paw.key.domain.model.entity.walkcourse.WalkCourseEntity
import com.paw.key.domain.repository.WalkSharedResultRepository
import com.paw.key.domain.repository.walkcourse.WalkCourseRepository
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseSideEffect
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class WalkCourseViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val walkSharedResultRepository : WalkSharedResultRepository,
    private val walkCourseRepository: WalkCourseRepository
) : ViewModel() {
    private val _state = MutableStateFlow(WalkCourseState())
    val state : StateFlow<WalkCourseState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<WalkCourseSideEffect>()
    val sideEffect: MutableSharedFlow<WalkCourseSideEffect>
        get() = _sideEffect

    private val _totalTime = MutableStateFlow(0L)
    val totalTime: StateFlow<Long> = _totalTime.asStateFlow()

    fun incrementTotalTime() {
        _totalTime.update {
            it + 1000L
        }
    }

    fun addInitLocation(location: LatLng) {
        val currentList = state.value.poiPoints.toMutableList()
        currentList.add(location)
        _state.value = _state.value.copy(
            poiPoints = currentList.toPersistentList()
        )
    }

    // 서버 통신
    fun postWalkCourseData(userId: Int) = viewModelScope.launch {
        val bitmap = state.value.bitmap
        if (bitmap == null) {
            _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar("산책 이미지가 없습니다."))
            return@launch
        }

        try {
            // PhotoUtils 사용
            val imagePart = PhotoUtils.createBitmapMultipart(
                bitmap = bitmap,
                partName = "trackingImage"
            )

            if (imagePart == null) {
                _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar("이미지 변환 실패"))
                return@launch
            }

            val routeEntity = WalkCourseEntity(
                coordinates = state.value.poiPoints.map { // la, lo
                    CoordinateEntity(it.longitude, it.latitude)
                },
                distance = state.value.totalDistance.toInt(),
                duration = (_totalTime.value).toInt(),
                startedAt = state.value.startedAt,
                endedAt = state.value.endedAt,
                stepCount = state.value.steps.toInt()
            )

            val result = walkCourseRepository.postWalkCourse(
                userId = userId,
                image = imagePart,
                routeRequestDto = routeEntity.toDto()
            )

            result.onSuccess { response ->
                _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar("루트 업로드 완료: routeId=${response}"))
                Log.d("WalkCourseViewModel", "routeId = ${response}")
            }.onFailure { throwable ->
                _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar("업로드 실패: ${throwable.message}"))
                Log.e("WalkCourseViewModel", "업로드 실패", throwable)
            }

        } catch (e: Exception) {
            _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar("오류 발생: ${e.localizedMessage}"))
            Log.e("WalkCourseViewModel", "예외 발생", e)
        }
    }


    // Todo : = updateState 로 일관되게 정리하기
    fun updateLocationAndCalculateDistance(newLocation: LatLng, accuracy: Float) {
        val MIN_ACCURACY_THRESHOLD = 25f // 미터 단위 (이보다 높은 정확도일 때만 사용)
        if (accuracy > MIN_ACCURACY_THRESHOLD) {
            return
        }

        _state.update { currentUiState ->
            val oldLocation = currentUiState.lastLocation
            var distanceIncrement = 0f

            if (oldLocation != null) {
                val oldAndroidLocation = Location("prev_location").apply {
                    latitude = oldLocation.latitude
                    longitude = oldLocation.longitude
                }

                val newAndroidLocation = Location("current_location").apply {
                    latitude = newLocation.latitude
                    longitude = newLocation.longitude
                }

                /*val calculatedDistance = oldAndroidLocation.distanceTo(newAndroidLocation)

                val MIN_DISTANCE_THRESHOLD = 1f // 미터 단위
                if (calculatedDistance >= MIN_DISTANCE_THRESHOLD) {
                    distanceIncrement = calculatedDistance
                }*/
                distanceIncrement = oldAndroidLocation.distanceTo(newAndroidLocation)
            }

            val updatedPoiPoints: PersistentList<LatLng> =
                if (currentUiState.poiPoints.isEmpty() && currentUiState.lastLocation == null) {
                    // 첫 위치일 경우 무조건 추가
                    currentUiState.poiPoints.add(newLocation)
                } else if (distanceIncrement > 0) { // (이동이 있었으면) 추가
                    currentUiState.poiPoints.add(newLocation)
                } else {
                    // 이동 거리가 0이거나 이전 위치가 없는 경우 (첫 위치가 이미 추가된 후)
                    currentUiState.poiPoints
                }

            val newTotalDistance = currentUiState.totalDistance + distanceIncrement

            currentUiState.copy(
                lastLocation = newLocation,
                currentLocation = newLocation,
                totalDistance = newTotalDistance,
                poiPoints = updatedPoiPoints
            )
        }
    }

    fun onSensorDataChanged(totalStepsFromSensor: Long) {
        updateState {
            val initial = initialSensorSteps
            val currentCalculatedSteps: Long
            val currentIsWalking: Boolean

            if (initial == null) {
                currentCalculatedSteps = 0L
                currentIsWalking = false

                copy(
                    initialSensorSteps = totalStepsFromSensor,
                    steps = currentCalculatedSteps,
                    prevSteps = currentCalculatedSteps,
                    isWalking = currentIsWalking
                )
            } else {
                currentCalculatedSteps = totalStepsFromSensor - initial

                currentIsWalking = if (currentCalculatedSteps > prevSteps) {
                    true
                } else if (currentCalculatedSteps == prevSteps && prevSteps > 0) {
                    isWalking
                } else {
                    false
                }

                copy(
                    steps = currentCalculatedSteps,
                    prevSteps = currentCalculatedSteps, // 현재 걸음 수를 이전 걸음 수로 저장
                    isWalking = currentIsWalking
                )
            }
        }
    }

    fun updateState(reducer: WalkCourseState.() -> WalkCourseState) {
        Log.e("updateState", "updateState called")
        _state.update {
            it.reducer()
        }
    }

    fun mapCaptureCompleted() {
        updateState {
            copy(shouldCaptureMap = false)
        }
    }

    fun onMapCaptured(bitmap: Bitmap?) {
        if (bitmap == null) {
            return
        }
        updateState {
            copy(bitmap = bitmap)
        }

        viewModelScope.launch {
            try {
                walkSharedResultRepository.saveResult(
                    bitmap = state.value.bitmap,
                    totalTime = _totalTime.value,
                    distance = state.value.totalDistance,
                    steps = state.value.steps.toInt(),
                    points = state.value.poiPoints.toList()
                )
                Log.d("WalkCourseViewModel", "state : ${state.value}")

                _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar("산책 지도 이미지가 저장되었습니다."))
                Log.d("WalkCourseViewModel", "Map captured bitmap saved to DataStore.")
            } catch (e: Exception) {
                _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar("산책 지도 이미지 저장 실패: ${e.localizedMessage}"))
                Log.e("WalkCourseViewModel", "Error saving captured bitmap: ${e.localizedMessage}")
            }  finally {
                mapCaptureCompleted() // 캡처 시도 후, 성공/실패 여부와 관계없이 플래그 리셋
            }
        }
    }

    fun onStopTrackingEvent() {
        viewModelScope.launch {
            val currentWalkState = _state.value

            try {
                walkSharedResultRepository.saveResult(
                    bitmap = currentWalkState.bitmap,
                    totalTime = _totalTime.value,
                    distance = currentWalkState.totalDistance,
                    steps = currentWalkState.steps.toInt(),
                    points = currentWalkState.poiPoints.toList()
                )
                Log.e("WalkCourseViewModel", PreferenceDataStore.getTotalTime(context).toString())
                _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar("산책 기록이 성공적으로 저장되었습니다."))
            } catch (e: Exception) {
                Log.e("WalkCourseViewModel", "Error saving all walk summary data: ${e.message}", e)
                _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar("산책 기록 저장 실패: ${e.localizedMessage}"))
            } finally {
                walkSharedResultRepository.saveResult(
                    bitmap = currentWalkState.bitmap,
                    totalTime = _totalTime.value,
                    distance = currentWalkState.totalDistance,
                    steps = currentWalkState.steps.toInt(),
                    points = currentWalkState.poiPoints.toList()
                )
            }
        }
    }
}