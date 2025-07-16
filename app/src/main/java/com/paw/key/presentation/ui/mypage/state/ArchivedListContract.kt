package com.paw.key.presentation.ui.mypage.state

import androidx.compose.runtime.Immutable
import com.paw.key.domain.model.entity.archivedlist.ArchivedListEntity
import com.paw.key.domain.model.entity.savedlist.SavedListEntity

@Immutable
data class ArchivedListState(
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

sealed class ArchivedListSideEffect {
    data class ShowSnackBar(val message: String) : ArchivedListSideEffect()
    data object NavigateUp : ArchivedListSideEffect()
    data object NavigateNext : ArchivedListSideEffect()
}