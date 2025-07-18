package com.paw.key.presentation.ui.course.walkreview.state

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.paw.key.presentation.ui.course.walkreview.WalkReviewCategoryUiModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

class WalkReviewContract {

    @Immutable
    data class WalkReviewState(
        val images: PersistentList<Uri> = persistentListOf(),
        val tags : PersistentList<String> = persistentListOf(),
        val location: String = "",
        val date: String = "",
        val time: String = "",

        val title: String = "",
        val content: String = "",

        val petName: String = "포비",

        val isPublic: Boolean = false,
        val isMine: Boolean = false,

        val categoryList: List<WalkReviewCategoryUiModel> = emptyList()
    ) {
        val isValidForm: Boolean
            get() = title.isNotBlank() &&
                    content.isNotBlank() &&
                    categoryList.all { category ->
                        category.options.any { it.isSelected }
                    }
    }

    sealed class WalkReviewSideEffect {
        data class ShowSnackBar(val message: String) : WalkReviewSideEffect()
        data class SHowToastMessage(val message: String) : WalkReviewSideEffect()
        data object NavigateUp: WalkReviewSideEffect()
        data class NavigateNext(val routeId : Int, val pageId : Int): WalkReviewSideEffect()
    }
}