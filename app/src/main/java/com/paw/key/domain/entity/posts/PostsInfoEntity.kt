package com.paw.key.domain.entity.posts

data class PostsInfoEntity(
    val title: String,
    val description: String,
    val isPublic: Boolean,
    val routeId: Int,
    val routeImageId: Int,
    val walkImageIds: List<Int>,
    val selectedOptionsForCategories: List<CategoryOptionEntity>,
    val imageUrls: List<Int>
)

data class CategoryOptionEntity(
    val categoryId: Int,
    val selectedOptionIds: List<Int>
)

data class PostsResultEntity(
    val postId: Int,
    val routeId: Int,
)
