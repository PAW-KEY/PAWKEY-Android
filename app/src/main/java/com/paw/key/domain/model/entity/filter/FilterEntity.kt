package com.paw.key.domain.model.entity.filter

data class FilterEntity(
    val selectList: List<SelectOption>? = null,
    val categoryList: List<Category>? = null
)

data class SelectOption(
    val selectId: Int = 0,
    val selectName: String = "",
    val options: List<SelectOptionItem>? = null
)

data class SelectOptionItem(
    val selectOptionId: Int = 0,
    val selectText: String = ""
)

data class Category(
    val categoryId: Int = 0,
    val categoryName: String = "",
    val categoryDescription: String? = null,
    val categoryOptions: List<CategoryOption>? = null
)

data class CategoryOption(
    val categoryOptionId: Int = 0,
    val categoryOptionText: String = ""
)