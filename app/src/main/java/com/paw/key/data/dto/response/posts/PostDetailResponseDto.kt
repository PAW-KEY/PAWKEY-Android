package com.paw.key.data.dto.response.posts

import com.paw.key.domain.entity.posts.AuthorInfoEntity
import com.paw.key.domain.entity.posts.PostsDetailEntity
import com.paw.key.domain.entity.posts.RouteDisplayEntity
import com.paw.key.domain.entity.posts.WalkImageEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDetailResponseDto(
    @SerialName("postId")
    val postId: Int,

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
) {
    fun toEntity() = PostsDetailEntity(
        postId = postId,
        title = title,
        description = description,
        isPublic = isPublic,
        isMine = isMine,
        authorInfo = authorInfo.toEntity(),
        routeDisplay = routeDisplay.toEntity(),
        categoryTagTexts = categoryTagTexts,
        walkImages = walkImages.map { it.toEntity() }
    )
}

@Serializable
data class AuthorInfoDto(
    @SerialName("authorId")
    val authorId: Int,

    @SerialName("petId")
    val petId: Int,

    @SerialName("petName")
    val petName: String,

    @SerialName("petProfileImage")
    val petProfileImage: String
) {
    fun toEntity() = AuthorInfoEntity(
        authorId = authorId,
        petId = petId,
        petName = petName,
        petProfileImage = petProfileImage
    )
}

@Serializable
data class RouteDisplayDto(
    @SerialName("routeId")
    val routeId: Int,

    @SerialName("locationText")
    val locationText: String,

    @SerialName("dateTimeText")
    val dateTimeText: String,

    @SerialName("metaTagTexts")
    val metaTagTexts: List<String>,

    @SerialName("routeImageUrl")
    val routeImageUrl: String
) {
    fun toEntity() = RouteDisplayEntity(
        routeId = routeId,
        locationText = locationText,
        dateTimeText = dateTimeText,
        metaTagTexts = metaTagTexts,
        routeImageUrl = routeImageUrl
    )
}

@Serializable
data class WalkImageDto(
    @SerialName("imageId")
    val imageId: Int,

    @SerialName("imageUrl")
    val imageUrl: String
) {
    fun toEntity() = WalkImageEntity(
        imageId = imageId,
        imageUrl = imageUrl
    )
}
