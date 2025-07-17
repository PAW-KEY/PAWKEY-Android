package com.paw.key.data.dto.response.list

import com.paw.key.domain.model.entity.list.ListEntity
import com.paw.key.domain.model.entity.list.PostEntity
import com.paw.key.domain.model.entity.list.WriterEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsListResponseDto (
    @SerialName("posts")
    val posts : List<PostDto>
)

@Serializable
data class PostDto (
    @SerialName("postId")
    val postId : Int,
    @SerialName("createdAt")
    val createdAt : String,
    @SerialName("isLike")
    val isLike : Boolean,
    @SerialName("title")
    val title : String,
    @SerialName("isMine")
    val isMine : Boolean,
    @SerialName("isPublic")
    val isPublic : Boolean,
    @SerialName("representativeImageUrl")
    val representativeImageUrl : String,
    @SerialName("routeId")
    val routeId : Int,
    @SerialName("writer")
    val writer : WriterDto,
    @SerialName("descriptionTags")
    val descriptionTags : List<String>
)

@Serializable
data class WriterDto (
    @SerialName("userId")
    val userId : Int,
    @SerialName("petName")
    val petName : String,
    @SerialName("petProfileImageUrl")
    val petProfileImageUrl : String,
)

fun PostsListResponseDto.toEntity(): ListEntity {
    return ListEntity(
        posts = posts.map { it.toEntity() }
    )
}

fun PostDto.toEntity(): PostEntity {
    return PostEntity(
        postId = postId,
        createdAt = createdAt,
        isLike = isLike,
        title = title,
        representativeImageUrl = representativeImageUrl,
        routeId = routeId,
        isMine = isMine,
        writer = writer.toEntity(),
        isPublic = isPublic,
        descriptionTags = descriptionTags
    )
}

fun WriterDto.toEntity(): WriterEntity {
    return WriterEntity(
        userId = userId,
        petName = petName,
        petProfileImageUrl = petProfileImageUrl
    )
}