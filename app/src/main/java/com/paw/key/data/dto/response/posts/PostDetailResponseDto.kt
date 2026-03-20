package com.paw.key.data.dto.response.posts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDetailResponseDto(
    @SerialName("postId")
    val postId: Long,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,

    @SerialName("isPublic")
    val isPublic: Boolean,

    @SerialName("isMine")
    val isMine: Boolean,

    @SerialName("authorInfo")
    val authorInfo: AuthorInfoDto,

    @SerialName("routeDisplay")
    val routeDisplay: RouteDisplayDto,

    @SerialName("categoryTagTexts")
    val categoryTagTexts: List<String>,

    @SerialName("walkImages")
    val walkImages: List<WalkImageDto>
)

@Serializable
data class AuthorInfoDto(
    @SerialName("authorId")
    val authorId: Long,

    @SerialName("petId")
    val petId: Long,

    @SerialName("petName")
    val petName: String,

    @SerialName("petProfileImage")
    val petProfileImage: String
)

@Serializable
data class RouteDisplayDto(
    @SerialName("routeId")
    val routeId: Long,

    @SerialName("locationText")
    val locationText: String,

    @SerialName("dateTimeText")
    val dateTimeText: String,

    @SerialName("metaTagTexts")
    val metaTagTexts: List<String>,

    @SerialName("routeImageUrl")
    val routeImageUrl: String
)

@Serializable
data class WalkImageDto(
    @SerialName("imageId")
    val imageId: Long,

    @SerialName("imageUrl")
    val imageUrl: String
)