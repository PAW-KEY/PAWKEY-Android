package com.paw.key.presentation.ui.course.entire.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.paw.key.R

class EntireCourseContract {
    @Immutable
    data class EntireCourseState(
        val pagerState : Int = 2,
        val selectedTabIndex : Int = 0,
        val courseTabs : List<CourseTab> = listOf(
            CourseTab.MapTab,
            CourseTab.ListTab,
        ),
        val isEnabled : Boolean = false,

        val isLocationPermissionGranted: Boolean = false,
        val isRecognitionPermissionGranted: Boolean = false,
        val isLocationServiceEnabled: Boolean = false,
    )

    sealed class EntireCourseSideEffect {
        data class ShowSnackBar(val message: String) : EntireCourseSideEffect()
        data object NavigateUp: EntireCourseSideEffect()
        data object NavigateNext: EntireCourseSideEffect()
    }

    sealed class CourseTab (
        @StringRes val titleResId: Int
    ) {
        data object MapTab : CourseTab(R.string.course_tab_title_map)

        data object ListTab : CourseTab(R.string.course_tab_title_list)
    }
}