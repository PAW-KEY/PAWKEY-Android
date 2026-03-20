package com.paw.key.presentation.ui.community

import com.paw.key.core.model.WalkingRouteUiModel
import com.paw.key.domain.entity.posts.FilterSelectedItemEntity
import com.paw.key.presentation.ui.community.model.FilterCategoryUiModel
import com.paw.key.presentation.ui.community.model.FilterSelectedUiModel
import com.paw.key.presentation.ui.community.model.PostsFilterUiModel
import com.paw.key.presentation.ui.community.model.SelectionType
import com.paw.key.presentation.ui.community.model.SortedType
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

data class CommunityState(
    val communityRouteList: PersistentList<WalkingRouteUiModel> = persistentListOf(),
    val selectedSortedType: SortedType = SortedType.LATEST,
    val filterUiModel: PostsFilterUiModel = PostsFilterUiModel(), // 게시물 조회 시 사용하는 필터-스크린용
    val selectedOptionIds: PersistentMap<Int, PersistentList<Int>> = persistentMapOf(), // 사용자가 필터 선택 시
    val nextCursor: String? = null,
    val hasNext: Boolean = false
) {
    fun getSelectedOptionIds(category: FilterCategoryUiModel): PersistentList<Int> {
        return selectedOptionIds[category.id] ?: persistentListOf()
    }

    fun getUpdatedOptionIds(
        optionId: Int,
        category: FilterCategoryUiModel
    ): PersistentMap<Int, PersistentList<Int>> {
        return if (category.selectionType == SelectionType.SINGLE) {
            selectedOptionIds.put(category.id, persistentListOf(optionId))
        } else {
            val current = selectedOptionIds[category.id] ?: persistentListOf()
            val updated = if (current.contains(optionId)) {
                current.remove(optionId)
            } else {
                current.add(optionId)
            }
            if (updated.isEmpty()) {
                selectedOptionIds.remove(category.id)
            } else {
                selectedOptionIds.put(category.id, updated)
            }
        }
    }

    fun toFilterEntity(): FilterSelectedItemEntity {
        val options = selectedOptionIds.map { (categoryId, optionIds) ->
            val isDuration = filterUiModel.durationList.any { it.id == categoryId }

            FilterSelectedUiModel(
                durationId = if (isDuration) categoryId else null,
                categoryId = if (!isDuration) categoryId else null,
                optionsIds = optionIds
            ).toEntity()
        }
        return FilterSelectedItemEntity(selectedOptions = options)
    }
}
