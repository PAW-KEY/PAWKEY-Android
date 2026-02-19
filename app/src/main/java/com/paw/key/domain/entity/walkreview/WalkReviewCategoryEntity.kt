package com.paw.key.domain.entity.walkreview

data class WalkReviewCategoryListEntity(
    val categoryList : List<WalkReviewCategoryEntity>
)

data class WalkReviewCategoryEntity(
    val categoryId : Int,
    val categoryDescription : String,
    val categoryName : String,
    val options : List<WalkReviewOptionOptionsResponseEntity>
)

data class WalkReviewOptionOptionsResponseEntity(
    val categoryOptionId : Int,
    val optionText : String
)