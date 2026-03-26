package com.paw.key.data.dto.response.home

import com.paw.key.domain.entity.home.HomeRouteEntity
import com.paw.key.domain.entity.home.RouteEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeRouteResponseDto(
    @SerialName("popularRoutes")
    val popularRoutes: List<RouteDto>, // 사용자 거주 지역 내 인기 리스트

    @SerialName("similarUserRoutes")
    val similarUserRoutes: List<RouteDto> // 비슷한 이용자들이 선호하는 리스트
) {
    fun toEntity(): HomeRouteEntity {
        return HomeRouteEntity(
            popularRoutes = popularRoutes.map { it.toEntity() },
            similarUserRoutes = similarUserRoutes.map { it.toEntity() }
        )
    }
}

@Serializable
data class RouteDto(
    @SerialName("routeId")
    val routeId: Long, // 산책 루트의 고유 ID

    @SerialName("postId")
    val postId: Long, // 게시물 고유 Id

    @SerialName("regionName")
    val regionName: String, // 루트가 속한 지역명 (구, 동 등)

    @SerialName("title")
    val title: String, // 루트에 대한 게시물의 제목

    @SerialName("date")
    val date: String, // 게시글의 마지막 기록 날짜

    @SerialName("duration")
    val duration: Int, // 총 산책 소요 시간

    @SerialName("isLiked")
    val isLiked: Boolean, // 현재 사용자가 해당 루트에 좋아요를 눌렀는지 여부

    @SerialName("imageUrl")
    val imageUrl: String // 루트 대표 이미지의 URL (CDN)
) {
    fun toEntity(): RouteEntity {
        return RouteEntity(
            routeId = routeId,
            postId = postId,
            regionName = regionName,
            title = title,
            date = date,
            duration = duration,
            isLiked = isLiked,
            imageUrl = imageUrl
        )
    }
}
