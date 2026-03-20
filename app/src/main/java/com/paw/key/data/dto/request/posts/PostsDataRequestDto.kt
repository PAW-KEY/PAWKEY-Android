package com.paw.key.data.dto.request.posts

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

    @SerialName("selectedOptionsForCategories")
    val selectedOptionsForCategories: List<CategoryOptionDto>,

    @SerialName("routeId")
    val routeId: Long,

    @SerialName("routeImageId")
    val routeImageId: Long,

    @SerialName("walkImageIds")
    val walkImageIds: List<Long>
)

@Serializable
data class CategoryOptionDto(
    @SerialName("categoryId")
    val categoryId: Long,

    @SerialName("selectedOptionIds")
    val selectedOptionIds: List<Long>
)