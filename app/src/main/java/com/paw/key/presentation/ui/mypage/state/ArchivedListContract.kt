package com.paw.key.presentation.ui.mypage.state

import androidx.compose.runtime.Immutable

@Immutable
data class ArchivedListState(
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

sealed class ArchivedListSideEffect {
    data class ShowSnackBar(val message: String) : ArchivedListSideEffect()
    data object NavigateUp : ArchivedListSideEffect()
    data object NavigateNext : ArchivedListSideEffect()
}