package com.paw.key.presentation.ui.community

import com.paw.key.core.model.WalkingRouteUiModel
import com.paw.key.presentation.ui.community.model.SortedType
import com.paw.key.presentation.ui.course.walkreview.model.WalkReviewFilterModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class CommunityState(
    val filterList: ImmutableList<String> = persistentListOf(),
    val communityRouteList: ImmutableList<WalkingRouteUiModel> = persistentListOf(),
    val selectedSortedType: SortedType = SortedType.LATEST,

    val communityFilterModel: WalkReviewFilterModel = WalkReviewFilterModel(),
    val communitySelectedFilterData: PersistentList<String> = persistentListOf(),
) {
    fun getSingleFilterSelection(categoryList: List<String>): String {
        return communitySelectedFilterData.firstOrNull { categoryList.contains(it) }.orEmpty()
    }

    fun getUpdatedFilterList(
        selectedItem: String,
        categoryList: List<String>,
        isSingleSelect: Boolean
    ): PersistentList<String> {
        return if (isSingleSelect) {
            communitySelectedFilterData
                .removeAll(categoryList)
                .add(selectedItem)
        } else {
            if (communitySelectedFilterData.contains(selectedItem)) {
                communitySelectedFilterData.remove(selectedItem)
            } else {
                communitySelectedFilterData.add(selectedItem)
            }
        }
    }
}