package com.paw.key.data.dto.response.walklist

import com.paw.key.domain.model.entity.walklist.AuthorInfoEntity
import com.paw.key.domain.model.entity.walklist.CategoryTagsEntity
import com.paw.key.domain.model.entity.walklist.WalkListDetailEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalkReviewDetailResponseDto(
    @SerialName("postId")
    val postId: Int,
    @SerialName("routeId")
    val routeId: Int,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("isLike")
    val isLike: Boolean,
    @SerialName("authorInfo")
    val authorInfo: AuthorInfoDto,
    @SerialName("categoryTags")
    val categoryTags: CategoryTagsDto,
    @SerialName("regionName")
    val regionName: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("routeMapImageUrl")
    val routeMapImageUrl: String,
    @SerialName("walkingImageUrls")
    val walkingImageUrls: List<String>
) {
    fun toEntity(): WalkListDetailEntity {
        return WalkListDetailEntity(
            postId = postId,
            routeId = routeId,
            title = title,
            content = content,
            isLike = isLike,
            authorInfo = authorInfo.toEntity(),
            categoryTags = categoryTags.toEntity(),
            regionName = regionName,
            createdAt = createdAt,
            routeMapImageUrl = routeMapImageUrl,
            walkingImageUrls = walkingImageUrls
        )
    }
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
    fun toEntity(): AuthorInfoEntity {
        return AuthorInfoEntity(
            authorId = authorId,
            petId = petId,
            petName = petName,
            petProfileImage = petProfileImage
        )
    }
}

@Serializable
data class CategoryTagsDto(
    @SerialName("categoryOptionSummary")
    val categoryOptionSummary: List<String>
) {
    fun toEntity(): CategoryTagsEntity {
        return CategoryTagsEntity(
            categoryOptionSummary = categoryOptionSummary
        )
    }
}
