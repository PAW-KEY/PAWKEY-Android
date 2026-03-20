package com.paw.key.core.model

data class WalkingRouteUiModel(
    val routeId: Int,
    val postId: Int,
    val regionName: String,
    val title: String,
    val date: String,
    val duration: Int,
    val isLiked: Boolean,
    val imageUrl: String
) {
    companion object {
        val Fake = listOf(
            WalkingRouteUiModel(
                routeId = 1,
                postId = 1,
                regionName = "강남구 역삼동",
                title = "강남구 역삼동 산책",
                date = "2023-07-01",
                duration = 6,
                isLiked = true,
                imageUrl = ""
            ),
            WalkingRouteUiModel(
                routeId = 2,
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