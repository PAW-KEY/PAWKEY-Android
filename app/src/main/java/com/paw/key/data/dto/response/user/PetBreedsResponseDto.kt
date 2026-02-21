package com.paw.key.data.dto.response.user

import com.paw.key.domain.entity.user.PetBreedsEntity
import com.paw.key.domain.entity.user.PetBreedsItemEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PetBreedsResponseDto(
    @SerialName("breedList")
    val breedList: List<PetBreedsItemDto>
) {
    fun toEntity() = PetBreedsEntity(
        breedList = breedList.map { it.toEntity() }
    )
}

@Serializable
data class PetBreedsItemDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
) {
    fun toEntity() = PetBreedsItemEntity(
        id = id,
        name = name
    )
}
