package com.paw.key.data.dto.response.walkreview

import com.paw.key.domain.model.entity.walkreview.WalkReviewCategoryEntity
import com.paw.key.domain.model.entity.walkreview.WalkReviewCategoryListEntity
import com.paw.key.domain.model.entity.walkreview.WalkReviewOptionOptionsResponseEntity
import kotlinx.serialization.Serializable

@Serializable
data class WalkReviewCategoryResponseDto(
    val categoryList : List<CategoryResponseDto>
) {
    fun toEntity() = WalkReviewCategoryListEntity(
        categoryList = categoryList.map { it.toEntity() }
    )
}

@Serializable
data class CategoryResponseDto(
    val categoryId : Int,
    val categoryDescription : String,
    val categoryName : String,
    val categoryOptions : List<OptionsResponseDto>
) {
    fun toEntity() = WalkReviewCategoryEntity(
        categoryId = categoryId,
        categoryDescription = categoryDescription,
        categoryName = categoryName,
        options = categoryOptions.map { it.toEntity() }
    )

}

@Serializable
data class OptionsResponseDto(
    val categoryOptionId : Int,
    val categoryOptionText : String
) {
    fun toEntity() = WalkReviewOptionOptionsResponseEntity(
        categoryOptionId = categoryOptionId,
        optionText = categoryOptionText
    )
}