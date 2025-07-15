package com.paw.key.presentation.ui.mypage.state

import androidx.compose.runtime.Immutable

object SavedListContract {
    @Immutable
    data class SavedListState(
        val courseList: List<CourseCardData> = emptyList()
    )

    @Immutable
    data class CourseCardData(
        val title: String,
        val petName: String,
        val date: String,
        val location: String,
        val distance: String,
        val time: String
    )
}