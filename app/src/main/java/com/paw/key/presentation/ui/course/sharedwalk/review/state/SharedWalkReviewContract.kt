package com.paw.key.presentation.ui.course.sharedwalk.review.state

import androidx.compose.runtime.Immutable
import com.paw.key.presentation.ui.course.walkreview.WalkReviewCategoryUiModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class SharedWalkReviewState(
    val location : String = "",
    val date : String = "",
    val time : String = "",

    val petName : String = "포비",

    val isDialogVisible : Boolean = false,

    val categoryList: List<WalkReviewCategoryUiModel> = emptyList(),

    val tags : PersistentList<String> = persistentListOf(),
){
    val isValidForm get() = categoryList.all { category ->
        category.options.any { it.isSelected }
    }
}

sealed class SharedWalkReviewSideEffect {
    data class ShowSnackBar(val message: String) : SharedWalkReviewSideEffect()
    data object NavigateUp: SharedWalkReviewSideEffect()
    data object NavigateNext: SharedWalkReviewSideEffect()
}