package com.paw.key.data.dto.request.posts

import com.paw.key.domain.entity.posts.CategoryOptionEntity
import com.paw.key.domain.entity.posts.PostsInfoEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsDataRequestDto(
    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,

    @SerialName("isPublic")
    val isPublic: Boolean,

    @SerialName("routeId")
    val routeId: Int,

    @SerialName("routeImageId")
    val routeImageId: Int,

    @SerialName("walkImageIds")
    val walkImageIds: List<Int>,

    @SerialName("selectedOptionsForCategories")
    val selectedOptionsForCategories: List<CategoryOptionDto>,

    @SerialName("imageUrls")
    val imageUrls: List<Int>
)

fun PostsInfoEntity.toDto() = PostsDataRequestDto(
    title = title,
    description = description,
    isPublic = isPublic,
    routeId = routeId,
    routeImageId = routeImageId,
    walkImageIds = walkImageIds,
    selectedOptionsForCategories = selectedOptionsForCategories.map { it.toDto() },
    imageUrls = imageUrls
)

@Serializable
data class CategoryOptionDto(
    @SerialName("categoryId")
    val categoryId: Int,

    @SerialName("selectedOptionIds")
    val selectedOptionIds: List<Int>
)

fun CategoryOptionEntity.toDto() = CategoryOptionDto(
    categoryId = categoryId,
    selectedOptionIds = selectedOptionIds
)

