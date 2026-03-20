package com.paw.key.data.dto.response.home

import com.paw.key.domain.entity.home.HomeWeatherEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeWeatherResponseDto(
    @SerialName("temperature")
    val temperature: Int, // 온도 정보

    @SerialName("rainyMm")
    val rainyMm: Int, // 강수량

    @SerialName("region")
    val region: String // 유저의 설정 지역
) {
    fun toEntity(): HomeWeatherEntity {
        return HomeWeatherEntity(
            temperature = temperature,
            rainyMm = rainyMm,
            region = region
        )
    }
}
