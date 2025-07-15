package com.paw.key.presentation.ui.mypage.state

import androidx.compose.runtime.Immutable

@Immutable
data class SavedListState(
    val courseList: List<CourseCardData> = emptyList()
)

@Immutable
data class CourseCardData(
    //제목
    val description: String,
    val petName: String,
    val createdAt: String,
    val isShared: Boolean,
    val isLiked: Boolean,
    val imageUrl: String,
)

sealed class SavedListSideEffect {
    data class ShowSnackBar(val message: String) : SavedListSideEffect()
    data object NavigateUp : SavedListSideEffect()
    data object NavigateNext : SavedListSideEffect()
}