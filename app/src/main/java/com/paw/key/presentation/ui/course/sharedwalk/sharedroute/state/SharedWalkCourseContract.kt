package com.paw.key.presentation.ui.course.sharedwalk.sharedroute.state

import android.graphics.Bitmap
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.naver.maps.geometry.LatLng
import com.paw.key.R
import com.paw.key.core.util.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class SharedWalkCourseState(
    val uiState: UiState<PersistentList<LatLng>> = UiState.Loading,
    val poiPoints: PersistentList<LatLng> = persistentListOf(),

    val bitmap: Bitmap? = null,

    // 현재 걸음 수
    val steps: Long = 0,
    val totalDistance: Float = 0f,

    val initialSensorSteps: Long? = null,
    val prevSteps: Long = 0,
    val isWalking: Boolean = false,

    val initialLocationState : UiState<LatLng> = UiState.Loading,
    val currentLocation: LatLng? = null,
    val lastLocation: LatLng? = null,
    val cameraState : Boolean = false,
    val isLocationTracking: Boolean = false,

    val isTrackingEnabled : Boolean = false,
    val isRecording : Boolean = false, // 기록 중 상태관리

    val shouldCaptureMap: Boolean = false
)

sealed class SharedWalkCourseSideEffect {
    data class ShowSnackBar(val message: String) : SharedWalkCourseSideEffect()
    data object NavigateUp: SharedWalkCourseSideEffect()
    data object NavigateNext: SharedWalkCourseSideEffect()
}

sealed class WalkCourseRecord (
    @StringRes val titleResId: Int
) {
    data object DistanceRecord : WalkCourseRecord(R.string.course_record_distance)

    data object TimeRecord : WalkCourseRecord(R.string.course_record_time)

    data object StepsRecord : WalkCourseRecord(R.string.course_record_step)
}