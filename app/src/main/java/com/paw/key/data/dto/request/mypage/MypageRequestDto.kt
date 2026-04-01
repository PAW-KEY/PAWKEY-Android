package com.paw.key.data.dto.request.mypage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequestDto(
    @SerialName("name") val name: String,
    @SerialName("birth") val birth: String,
    @SerialName("gender") val gender: String,
)

@Serializable
data class UpdatePetRequestDto(
    @SerialName("name") val name: String,
    @SerialName("birth") val birth: String,
    @SerialName("gender") val gender: String,
    @SerialName("isNeutered") val isNeutered: Boolean,
    @SerialName("breedId") val breedId: Int,
    @SerialName("imageId") val imageId: Int,
)