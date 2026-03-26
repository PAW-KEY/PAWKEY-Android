package com.paw.key.data.dto.response.walkpreparation

import com.paw.key.domain.entity.walkpreparation.WalkPreparationMessageEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalkPreparationMessageResponseDto(
    @SerialName("mainMessage")
    val mainMessage: String,
    @SerialName("subMessage")
    val subMessage: String
) {
    fun toEntity() = WalkPreparationMessageEntity(
        mainMessage = mainMessage,
        subMessage = subMessage
    )
}
