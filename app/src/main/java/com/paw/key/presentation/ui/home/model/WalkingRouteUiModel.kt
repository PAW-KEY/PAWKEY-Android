package com.paw.key.presentation.ui.home.model

data class WalkingRouteUiModel(
    val id: Int,
    val title: String,
    val distance: String,
    val time: String,
    val date: String,
    val imageUri: String,
    val location: String
) {
    companion object {
        val Fake = listOf(
            WalkingRouteUiModel(
                id = 1,
                title = "한강 반포공원 코스",
                distance = "3.2km",
                time = "45",
                date = "2025/11/06",
                imageUri = "https://picsum.photos/400/300?random=1",
                location = "서울 서초구"
            ),
            WalkingRouteUiModel(
                id = 2,
                title = "북서울 꿈의숲 코스",
                distance = "2.8km",
                time = "38",
                date = "2025/11/06",
                imageUri = "https://picsum.photos/400/300?random=2",
                location = "서울 강북구"
            ),
            WalkingRouteUiModel(
                id = 3,
                title = "성수동 카페거리 산책로",
                distance = "1.6km",
                time = "25",
                date = "2025/11/06",
                imageUri = "https://picsum.photos/400/300?random=3",
                location = "서울 성동구"
            ),
            WalkingRouteUiModel(
                id = 4,
                title = "올림픽공원 호수길",
                distance = "4.5km",
                time = "60",
                date = "2025/11/06",
                imageUri = "https://picsum.photos/400/300?random=4",
                location = "서울 송파구"
            ),
            WalkingRouteUiModel(
                id = 5,
                title = "남산 둘레길",
                distance = "5.2km",
                time = "75",
                date = "2025/11/06",
                imageUri = "https://picsum.photos/400/300?random=5",
                location = "서울 중구"
            )
        )
    }
}
