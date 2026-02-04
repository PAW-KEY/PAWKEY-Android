package com.paw.key.presentation.ui.course.walkcourse.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.core.extension.toLatLng
import com.paw.key.core.util.PhotoUtils
import com.paw.key.core.util.UiState
import com.paw.key.domain.model.entity.walkcourse.CoordinateEntity
import com.paw.key.domain.model.entity.walkcourse.WalkCourseEntity
import com.paw.key.domain.repository.WalkSharedResultRepository
import com.paw.key.domain.repository.walkcourse.WalkCourseRepository
import com.paw.key.presentation.ui.course.util.RealTimeLocationListener
import com.paw.key.presentation.ui.course.walkcourse.state.WalkCourseSideEffect
import com.paw.key.presentation.ui.course.walkcourse.state.WalkCourseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WalkCourseViewModel @Inject constructor(
    private val walkSharedResultRepository: WalkSharedResultRepository,
    private val walkCourseRepository: WalkCourseRepository
) : ViewModel(), RealTimeLocationListener {
    private val _state = MutableStateFlow(WalkCourseState())
    val state: StateFlow<WalkCourseState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<WalkCourseSideEffect>()
    val sideEffect: SharedFlow<WalkCourseSideEffect> = _sideEffect.asSharedFlow()

    private var timerJob: Job? = null

    private var initialSensorSteps: Long = -1L
    private var lastLocation: Location? = null

    fun onPermissionsGranted() {
        if (_state.value.mapState.initialState is UiState.Loading) {
            _state.update {
                it.copy(
                    mapState = it.mapState.copy(
                        initialState = UiState.Success(true)
                    )
                )
            }
            startTracking()
        }
    }

    fun showToastMessage(
        message: String
    ) {
        viewModelScope.launch {
            _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar(message))
        }
    }

    fun startTracking() {
        _state.update { currentState ->
            val newRecordingState = currentState.recordingState.copy(
                isRecording = true,
                startedAt = LocalDateTime.now().toString()
            )

            currentState.copy(
                recordingState = newRecordingState
            )
        }
        startTimer()
    }

    fun pauseTracking() {
        _state.update { currentState ->
            val newRecordingState = currentState.recordingState.copy(
                isRecording = false,
                endedAt = LocalDateTime.now().toString()
            )

            currentState.copy(
                recordingState = newRecordingState
            )
        }
        stopTimer()
    }

    private fun startTimer() {
        if (timerJob?.isActive == true) return
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                _state.update { currentState ->
                    val newTimeMills = currentState.totalTimeMillis + 1000L
                    currentState.copy(
                        totalTimeMillis = newTimeMills
                    )
                }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }

    fun fetchTrackingEnable() {
        _state.update { currentState ->
            currentState.copy(
                mapState = currentState.mapState.copy(
                    isTrackingEnabled = !currentState.mapState.isTrackingEnabled
                )
            )
        }
    }

    fun disableTracking() {
        _state.update { currentState ->
            if (currentState.mapState.isTrackingEnabled) {
                currentState.copy(
                    mapState = currentState.mapState.copy(isTrackingEnabled = false)
                )
            } else {
                currentState
            }
        }
    }

    fun onRawStepData(totalStepsFromSensor: Long) {
        if (initialSensorSteps == -1L) {
            initialSensorSteps = totalStepsFromSensor
        }

        val sessionSteps = totalStepsFromSensor - initialSensorSteps

        _state.update { currentState ->
            currentState.copy(
                stepCounterState = currentState.stepCounterState.copy(
                    sessionSteps = sessionSteps
                )
            )
        }
    }

    // 서버 통신
    fun postWalkCourseData(userId: Int) = viewModelScope.launch {
        val bitmap = _state.value.mapState.capturedMapBitmap
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
                coordinates = _state.value.mapState.poiPoints.map {
                    CoordinateEntity(it.latitude, it.longitude)
                },
                distance = _state.value.mapState.totalDistance.toInt(),
                duration = (_state.value.totalTimeMillis / 1000).toInt(),
                startedAt = _state.value.recordingState.startedAt,
                endedAt = _state.value.recordingState.endedAt,
                stepCount = _state.value.stepCounterState.sessionSteps.toInt()
            )

            val result = walkCourseRepository.postWalkCourse(
                userId = userId,
                image = imagePart,
                routeRequestDto = routeEntity.toDto()
            )

            result.onSuccess { response ->
                _sideEffect.emit(WalkCourseSideEffect.NavigateNext(response.regionId))
            }.onFailure { throwable ->
                _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar("업로드 실패: ${throwable.message}"))
            }

        } catch (e: Exception) {
            _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar("오류 발생: ${e.localizedMessage}"))
        }
    }

    // Todo: 서버 내용 확인하고 넘기기
    fun stopTracking() {
        viewModelScope.launch {
            /*val currentWalkState = _state.value

            try {
                walkSharedResultRepository.saveResult(
                    bitmap = currentWalkState.mapState.capturedMapBitmap,
                    totalTime = currentWalkState.totalTimeMillis,
                    distance = currentWalkState.mapState.totalDistance,
                    steps = currentWalkState.stepCounterState.sessionSteps.toInt(),
                    points = currentWalkState.mapState.poiPoints.toList()
                )
                _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar("산책 기록이 성공적으로 저장되었습니다."))
                _sideEffect.emit(WalkCourseSideEffect.NavigateReview)
            } catch (e: Exception) {
                _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar("산책 기록 저장 실패: ${e.localizedMessage}"))
            }*/

            _state.update {
                it.copy(
                    isStopTracking = true
                )
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        if (location.accuracy > LOCATION_ACCURACY_THRESHOLD) {
            return
        }


        val newLatLng = location.toLatLng()
        val lastPoint = lastLocation

        // 최초 위치 수신 시
        if (lastPoint == null) {
            _state.update {
                it.copy(
                    mapState = it.mapState.copy(
                        currentLocation = newLatLng,
                        poiPoints = it.mapState.poiPoints.add(newLatLng)
                    )
                )
            }
            lastLocation = location // 마지막 기록 위치로 설정
            return
        }

        // 이후 위치 수신 시
        val distance = lastPoint.distanceTo(location)

        // Todo : 3m 이상 이동 시 경로 추가로 되어있는데 추후 어떻게 할 건지 확인
        if (distance >= 3.0f) {
            _state.update { currentState ->
                val newTotalDistance = currentState.mapState.totalDistance + distance
                currentState.copy(
                    mapState = currentState.mapState.copy(
                        currentLocation = newLatLng,
                        totalDistance = newTotalDistance,
                        poiPoints = currentState.mapState.poiPoints.add(newLatLng)
                    )
                )
            }
            lastLocation = location
        } else {
            _state.update {
                it.copy(
                    mapState = it.mapState.copy(
                        currentLocation = newLatLng
                    )
                )
            }
        }
    }

    companion object {
        private const val LOCATION_ACCURACY_THRESHOLD = 25f
    }
}
