package com.paw.key.data.dto.response.mypage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutePostListResponseDto(
    @SerialName("posts") val posts: List<RoutePostDto>,
)

@Serializable
data class RoutePostDto(
    @SerialName("postId") val postId: Int,
    @SerialName("regionName") val regionName: String,
    @SerialName("title") val title: String,
    @SerialName("date") val date: String,
    @SerialName("durationMinutes") val durationMinutes: Int,
    @SerialName("isLiked") val isLiked: Boolean,
    @SerialName("imageUrl") val imageUrl: String,
)

@Serializable
data class ReviewPostListResponseDto(
    @SerialName("posts") val posts: List<ReviewPostDto>,
)

@Serializable
data class ReviewPostDto(
    @SerialName("postId") val postId: Int,
    @SerialName("title") val title: String,
    @SerialName("regionName") val regionName: String,
    @SerialName("date") val date: String,
    @SerialName("categoryOptionSummary") val categoryOptionSummary: List<String>,
)