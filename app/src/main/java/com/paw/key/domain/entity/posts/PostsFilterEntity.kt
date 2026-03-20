package com.paw.key.domain.entity.posts

data class PostsCategoryEntity(
    val categoryList: List<FilterItemEntity>
)

data class PostsFilterEntity(
    val durationList: List<FilterItemEntity>,
    val categoryList: List<FilterItemEntity>
)

data class FilterItemEntity(
    val id: Int,
    val name: String,
    val selectionType: String,
    val options: List<FilterOptionEntity>
)

data class FilterOptionEntity(
    val id: Int,
    val text: String
)

data class FilterSelectedItemEntity(
    val selectedOptions: List<FilterSelectedIOptionEntity>
) {

}

data class FilterSelectedIOptionEntity(
    val durationId: Int? = null,
    val categoryId: Int? = null,
    val optionsIds: List<Int?>
)
