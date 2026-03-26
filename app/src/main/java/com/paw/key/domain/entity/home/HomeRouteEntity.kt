package com.paw.key.domain.entity.home

data class HomeRouteEntity(
    val popularRoutes: List<RouteEntity>, // 사용자 거주 지역 내 인기 리스트
    val similarUserRoutes: List<RouteEntity>
)

data class RouteEntity(
    val routeId: Long, // 산책 루트의 고유 ID
    val postId: Long, // 게시물 고유 Id
    val regionName: String, // 루트가 속한 지역명 (구, 동 등)
    val title: String, // 루트에 대한 게시물의 제목
    val date: String, // 게시글의 마지막 기록 날짜
    val duration: Int, // 총 산책 소요 시간
    val isLiked: Boolean, // 현재 사용자가 해당 루트에 좋아요를 눌렀는지 여부
    val imageUrl: String // 루트 대표 이미지의 URL (CDN)
)
