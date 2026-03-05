package com.paw.key.presentation.ui.course.walkcourse.viewmodel

import android.location.Location
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.paw.key.core.extension.toLatLng
import com.paw.key.core.util.UiState
import com.paw.key.domain.repository.WalkSharedResultRepository
import com.paw.key.domain.repository.walk.WalkRepository
import com.paw.key.presentation.ui.course.navigation.WalkCourse
import com.paw.key.presentation.ui.course.util.RealTimeLocationListener
import com.paw.key.presentation.ui.course.walkcourse.model.toEntity
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
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WalkCourseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val walkRepository: WalkRepository,
    private val walkSharedResultRepository: WalkSharedResultRepository,
) : ViewModel(), RealTimeLocationListener {
    // Todo : saveStateHandle로 isShared 받아서 처리하기
    private val routeId = savedStateHandle.toRoute<WalkCourse>().routeId
    private val _state = MutableStateFlow(WalkCourseState())
    val state: StateFlow<WalkCourseState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<WalkCourseSideEffect>()
    val sideEffect: SharedFlow<WalkCourseSideEffect> = _sideEffect.asSharedFlow()

    private var timerJob: Job? = null
    private var pointSyncJob: Job? = null // 5초 마다 서버 보낼 용도

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
                recordingState = newRecordingState,
                isStopTracking = false
            )
        }
        startTimer()
        startPointSyncTimer()
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
        stopPointSyncTimer()
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

    // 5초마다 좌표 서버 전송 타이머 시작
    private fun startPointSyncTimer() {
        if (pointSyncJob?.isActive == true) return

        pointSyncJob = viewModelScope.launch {
            while (true) {
                delay(5000L)
                syncCurrentLocation()
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun stopPointSyncTimer() {
        pointSyncJob?.cancel()
        pointSyncJob = null
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

    // 실제 서버 통신 함수 5초
    private fun syncCurrentLocation() {
        val currentState = _state.value

        // 기록 중이 아니거나 현재 위치가 없으면 보내지 않음
        if (!currentState.recordingState.isRecording) return
        val currentLocation = currentState.mapState.currentLocation ?: return

        val currentTimestamp = (System.currentTimeMillis() / 1000).toInt()
        val walkPointEntity = currentLocation.toEntity(
            routeId = routeId,
            timestamp = currentTimestamp
        )

        // 5초 뒤 다시 시도되기 때문에 에러 로그 발생 후 무시
        viewModelScope.launch {
            walkRepository.pointWalk(walkPointEntity)
                .onFailure { throwable ->
                    Timber.e(throwable)
                }
        }
    }

    // Todo: 서버 내용 확인하고 넘기기
    fun stopTracking() {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    recordingState = currentState.recordingState.copy(
                        isRecording = false,
                        endedAt = LocalDateTime.now().toString()
                    )
                )
            }

            val currentState = _state.value

            walkRepository.finishWalk(
                routeId = routeId,
                walkFinish = currentState.toEntity()
            ).onSuccess {
                _state.update {
                    it.copy(
                        isStopTracking = true
                    )
                }

                if (_state.value.isStopTracking) {
                    _sideEffect.emit(WalkCourseSideEffect.NavigateComplete(routeId))
                }
            }.onFailure {
                Timber.e(it)
                _sideEffect.emit(WalkCourseSideEffect.ShowSnackBar("산책 종료 실패"))
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

    override fun onCleared() {
        super.onCleared()
        stopTimer()
        stopPointSyncTimer()
    }

    companion object {
        private const val LOCATION_ACCURACY_THRESHOLD = 25f
    }
}
