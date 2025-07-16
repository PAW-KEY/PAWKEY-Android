package com.paw.key.presentation.ui.mypage.state

import androidx.compose.runtime.Immutable
import com.paw.key.domain.model.entity.archivedlist.ArchivedListEntity
import com.paw.key.domain.model.entity.savedlist.SavedListEntity

@Immutable
data class SavedListState(
    val courseList: List<ArchivedListEntity> = emptyList()
)

//@Immutable
//data class CourseCardData(
//    //제목
//    val description: String,
//    val petName: String,
//    val createdAt: String,
//    val isShared: Boolean,
//    val isLiked: Boolean,
//    val imageUrl: String,
//)

sealed class SavedListSideEffect {
    data class ShowSnackBar(val message: String) : SavedListSideEffect()
    data object NavigateUp : SavedListSideEffect()
    data object NavigateNext : SavedListSideEffect()
}