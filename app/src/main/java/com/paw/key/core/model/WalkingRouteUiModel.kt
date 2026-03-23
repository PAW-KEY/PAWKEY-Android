package com.paw.key.core.model

import com.paw.key.domain.entity.posts.PostEntity

data class WalkingRouteUiModel(
    val postId: Int,
    val regionName: String,
    val title: String,
    val date: String,
    val duration: Int,
    val isLiked: Boolean,
    val imageUrl: String?
) {
    companion object {
        val Fake = listOf(
            WalkingRouteUiModel(
                postId = 1,
                regionName = "강남구 역삼동",
                title = "강남구 역삼동 산책",
                date = "2023-07-01",
                duration = 6,
                isLiked = true,
                imageUrl = ""
            ),
            WalkingRouteUiModel(
                postId = 2,
                regionName = "강남구 역삼동",
                title = "강남구 역삼동 산책",
                date = "2023-07-01",
                duration = 6,
                isLiked = true,
                imageUrl = ""
            ),
        )
    }
}

fun PostEntity.toUiModel() = WalkingRouteUiModel(
    postId = postId,
    regionName = regionName,
    title = title,
    date = date.split("T").first().replace("-", "/"),
    duration = durationMinutes,
    isLiked = isLiked,
    imageUrl = imageUrl,
)
