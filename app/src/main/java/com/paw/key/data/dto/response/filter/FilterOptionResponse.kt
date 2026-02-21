package com.paw.key.data.dto.response.filter

import com.paw.key.domain.entity.filter.Category
import com.paw.key.domain.entity.filter.CategoryOption
import com.paw.key.domain.entity.filter.FilterEntity
import com.paw.key.domain.entity.filter.SelectOption
import com.paw.key.domain.entity.filter.SelectOptionItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilterOptionResponse(
    @SerialName("selectList")
    val selectList: List<SelectDto>,
    @SerialName("categoryList")
    val categoryList: List<CategoryDto>
) {
    fun toEntity(): FilterEntity {
        return FilterEntity(
            selectList = selectList.map { it.toEntity() },
            categoryList = categoryList.map { it.toEntity() }
        )
    }
}

@Serializable
data class SelectDto(
    @SerialName("selectId")
    val selectId: Int? = null,
    @SerialName("selectName")
    val selectName: String? = null,
    @SerialName("options")
    val options: List<OptionDto>? = null
) {
    fun toEntity(): SelectOption {
        return SelectOption(
            selectId = selectId ?: 0,
            selectName = selectName ?: "",
            options = options?.map { it.toEntity() } ?: emptyList(),
        )
    }
}

@Serializable
data class OptionDto(
    @SerialName("selectOptionId")
    val selectOptionId: Int? = null,
    @SerialName("selectText")
    val selectText: String? = null
) {
    fun toEntity(): SelectOptionItem {
        return SelectOptionItem(
            selectOptionId = selectOptionId ?: 0,
            selectText = selectText ?: ""
        )
    }
}

@Serializable
data class CategoryDto(
    @SerialName("categoryId")
    val categoryId: Int? = null,
    @SerialName("categoryDescription")
    val categoryDescription: String? = null,
    @SerialName("categoryName")
    val categoryName: String? = null,
    @SerialName("options")
    val categoryOptions: List<CategoryOptionDto>? = null
 ) {
    fun toEntity(): Category {
        return Category(
            categoryId = categoryId ?: 0,
            categoryName = categoryName ?: "",
            categoryDescription = categoryDescription ?: "",
            categoryOptions = categoryOptions?.map { it.toEntity() } ?: emptyList()
        )
    }
}

@Serializable
data class CategoryOptionDto(
    @SerialName("categoryOptionId")
    val categoryOptionId: Int? = null,
    @SerialName("optionText")
    val categoryOptionText: String? = null
) {
    fun toEntity(): CategoryOption {
        return CategoryOption(
            categoryOptionId = categoryOptionId ?: 0,
            categoryOptionText = categoryOptionText ?: ""
        )
    }
}