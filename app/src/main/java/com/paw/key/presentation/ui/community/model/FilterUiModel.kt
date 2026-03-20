package com.paw.key.presentation.ui.community.model

import androidx.compose.runtime.Immutable
import com.paw.key.domain.entity.posts.FilterItemEntity
import com.paw.key.domain.entity.posts.FilterSelectedIOptionEntity
import com.paw.key.domain.entity.posts.PostsFilterEntity

@Immutable
data class FilterCategoryUiModel(
    val id: Int,
    val name: String,
    val selectionType: SelectionType,
    val options: List<FilterOptionUiModel>
)

data class FilterOptionUiModel(
    val id: Int,
    val text: String
)

enum class SelectionType {
    SINGLE, MULTI
}

fun PostsFilterEntity.toUiModel() = PostsFilterUiModel(
    durationList = durationList.map { it.toUiModel() },
    categoryList = categoryList.map { it.toUiModel() }
)

fun FilterItemEntity.toUiModel() = FilterCategoryUiModel(
    id = id,
    name = name,
    selectionType = if (selectionType == "SINGLE") SelectionType.SINGLE else SelectionType.MULTI,
    options = options.map { FilterOptionUiModel(id = it.id, text = it.text) }
)

data class PostsFilterUiModel(
    val durationList: List<FilterCategoryUiModel> = emptyList(),
    val categoryList: List<FilterCategoryUiModel> = emptyList()
) {
    val allCategories get() = durationList + categoryList
}

data class FilterSelectedUiModel(
    val durationId: Int? = null,
    val categoryId: Int? = null,
    val optionsIds: List<Int?> = emptyList()
) {
    fun toEntity() = FilterSelectedIOptionEntity(
        durationId = durationId,
        categoryId = categoryId,
        optionsIds = optionsIds
    )
}
