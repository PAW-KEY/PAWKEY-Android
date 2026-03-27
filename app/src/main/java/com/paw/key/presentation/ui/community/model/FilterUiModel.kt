package com.paw.key.presentation.ui.community.model

import androidx.compose.runtime.Immutable
import com.paw.key.domain.entity.posts.FilterItemEntity
import com.paw.key.domain.entity.posts.FilterSelectedIOptionEntity
import com.paw.key.domain.entity.posts.PostsFilterEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Immutable
data class FilterCategoryUiModel(
    val id: Int,
    val name: String,
    val selectionType: SelectionType,
    val options: ImmutableList<FilterOptionUiModel>
)

data class FilterOptionUiModel(
    val id: Int,
    val text: String
)

enum class SelectionType {
    SINGLE, MULTI
}

fun PostsFilterEntity.toUiModel() = PostsFilterUiModel(
    durationList = durationList.map { it.toUiModel() }.toImmutableList(),
    categoryList = categoryList.map { it.toUiModel() }.toImmutableList()
)

fun FilterItemEntity.toUiModel() = FilterCategoryUiModel(
    id = id,
    name = name,
    selectionType = if (selectionType == "SINGLE") SelectionType.SINGLE else SelectionType.MULTI,
    options = options.map { FilterOptionUiModel(id = it.id, text = it.text) }.toImmutableList()
)

data class PostsFilterUiModel(
    val durationList: ImmutableList<FilterCategoryUiModel> = persistentListOf(),
    val categoryList: ImmutableList<FilterCategoryUiModel> = persistentListOf()
) {
    val allCategories get() = durationList + categoryList
}

data class FilterSelectedUiModel(
    val durationId: Int? = null,
    val categoryId: Int? = null,
    val optionsIds: ImmutableList<Int?> = persistentListOf()
) {
    fun toEntity() = FilterSelectedIOptionEntity(
        durationId = durationId,
        categoryId = categoryId,
        optionsIds = optionsIds
    )
}
