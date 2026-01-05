package com.paw.key.presentation.ui.course.walkcourse.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.paw.key.R
import com.paw.key.presentation.ui.course.walkcourse.model.MapState
import com.paw.key.presentation.ui.course.walkcourse.model.RecordingState
import com.paw.key.presentation.ui.course.walkcourse.model.StepCounterState

@Immutable
data class WalkCourseState(
    val recordingState: RecordingState = RecordingState(),
    val mapState: MapState = MapState(),
    val stepCounterState: StepCounterState = StepCounterState(),
    val totalTimeMillis: Long = 0L,
)

sealed class WalkCourseSideEffect {
    data class ShowSnackBar(val message: String) : WalkCourseSideEffect()
    data class ShowToastMessage(val message: String) : WalkCourseSideEffect()
    data object NavigateUp: WalkCourseSideEffect()
    data class NavigateNext(val regionId: Int): WalkCourseSideEffect()
}

sealed class WalkCourseRecord (
    @StringRes val titleResId: Int
) {
    data object DistanceRecord : WalkCourseRecord(R.string.course_record_distance)

    data object TimeRecord : WalkCourseRecord(R.string.course_record_time)

    data object StepsRecord : WalkCourseRecord(R.string.course_record_step)
}