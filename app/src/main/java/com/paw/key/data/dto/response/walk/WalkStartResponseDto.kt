package com.paw.key.data.dto.response.walk

import com.paw.key.domain.entity.walk.WalkStartEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalkStartResponseDto(
    @SerialName("routeId")
    val routeId: String,
    @SerialName("issuedAt")
    val issuedAt: Int,
) {
    fun toEntity() = WalkStartEntity(
        routeId = routeId,
        issuedAt = issuedAt,
    )
}