package com.paw.key.presentation.ui.course.walkreview.state

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.paw.key.presentation.ui.course.walkcourse.model.WalkInfoState
import com.paw.key.presentation.ui.course.walkreview.model.WalkReviewFilterModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class WalkReviewState(
    val walkReviewImageList : PersistentList<Uri> = persistentListOf(),
    val walkReviewFilterModel: WalkReviewFilterModel = WalkReviewFilterModel(),
    val walkReviewSelectedFilterData: PersistentList<String> = persistentListOf(),
    val walkReviewTitle : String = "",
    val walkReviewContent: String = "",
    val walkReviewCourseInfo: WalkInfoState = WalkInfoState(),
    val isComplete : Boolean = false,
) {
    fun getSingleFilterSelection(categoryList: List<String>): String {
        return walkReviewSelectedFilterData.firstOrNull { categoryList.contains(it) }.orEmpty()
    }

    fun getUpdatedFilterList(
        selectedItem: String,
        categoryList: List<String>,
        isSingleSelect: Boolean
    ): PersistentList<String> {
        return if (isSingleSelect) {
            walkReviewSelectedFilterData
                .removeAll(categoryList)
                .add(selectedItem)
        } else {
            if (walkReviewSelectedFilterData.contains(selectedItem)) {
                walkReviewSelectedFilterData.remove(selectedItem)
            } else {
                walkReviewSelectedFilterData.add(selectedItem)
            }
        }
    }
}
