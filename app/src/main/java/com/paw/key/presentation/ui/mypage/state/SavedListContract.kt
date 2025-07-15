package com.paw.key.presentation.ui.mypage.state

import androidx.compose.runtime.Immutable

@Immutable
data class SavedListState(
    val courseList: List<CourseCardData> = emptyList()
)

@Immutable
data class CourseCardData(
    val description: String,
    val petName: String,
    val createdAt: String,
    val isShared: Boolean,
    val isLiked: Boolean,
    val imageUrl: String,
    val onClickItem: () -> Unit = {}, // optional로 추가
)

sealed class SavedListSideEffect {
    data class ShowSnackBar(val message: String) : SavedListSideEffect()
    data object NavigateUp : SavedListSideEffect()
    data object NavigateNext : SavedListSideEffect()
}