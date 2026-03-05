package com.paw.key.data.dto.request.walk

import com.paw.key.domain.entity.walk.WalkFinish
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalkFinishRequestDto(
    @SerialName("distance")
    val distance: Int,
    @SerialName("duration")
    val duration: Int,
    @SerialName("stepCount")
    val stepCount: Int,
    @SerialName("endedAt")
    val endedAt: String
)

fun WalkFinish.toDto() = WalkFinishRequestDto(
    distance = distance,
    duration = duration,
    stepCount = stepCount,
    endedAt = endedAt
)
