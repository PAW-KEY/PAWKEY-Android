package com.paw.key.presentation.ui.course.walkcourse.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.paw.key.R
import com.paw.key.presentation.ui.course.walkcourse.model.MapState
import com.paw.key.presentation.ui.course.walkcourse.model.RecordingState
import com.paw.key.presentation.ui.course.walkcourse.model.StepCounterState
import com.paw.key.presentation.ui.course.walkcourse.util.formatDistance
import com.paw.key.presentation.ui.course.walkcourse.util.formatTime

@Immutable
data class WalkCourseState(
    val recordingState: RecordingState = RecordingState(),
    val mapState: MapState = MapState(),
    val stepCounterState: StepCounterState = StepCounterState(),
    val totalTimeMillis: Long = 0L,
    val isStopTracking: Boolean = false // true는 stop됨, false는 다시 시작
) {
    val formattedTime: String
        get() = formatTime(this.totalTimeMillis)

    val formattedDistance: String
        get() = formatDistance(this.mapState.totalDistance)
}

sealed interface WalkCourseSideEffect {
    data class ShowSnackBar(val message: String) : WalkCourseSideEffect
    data class ShowToastMessage(val message: String) : WalkCourseSideEffect
    data object NavigateUp: WalkCourseSideEffect
    data class NavigateNext(val regionId: Int): WalkCourseSideEffect

    data object NavigateReview: WalkCourseSideEffect
}

sealed class WalkCourseRecord (
    @StringRes val titleResId: Int
) {
    data object DistanceRecord : WalkCourseRecord(R.string.course_record_distance)

    data object TimeRecord : WalkCourseRecord(R.string.course_record_time)

    data object StepsRecord : WalkCourseRecord(R.string.course_record_step)
}
