package com.paw.key.data.dto.response.petprofile

import com.paw.key.domain.model.entity.petprofile.PetProfileEntity
import com.paw.key.domain.model.entity.petprofile.TraitEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PetProfileResponseDto(
    @SerialName("petId")
    val petId: Long,

    @SerialName("name")
    val name: String,

    @SerialName("gender")
    val gender: String,

    @SerialName("isNeutered")
    val isNeutered: Boolean,

    @SerialName("age")
    val age: Int,

    @SerialName("isAgeKnown")
    val isAgeKnown: Boolean,

    @SerialName("breed")
    val breed: String,

    @SerialName("imageUrl")
    val imageUrl: String,

    @SerialName("traits")
    val traits: List<TraitDto>,

    @SerialName("walkCount")
    val walkCount: Int
) {
    fun toEntity() = PetProfileEntity(
        petId = petId,
        name = name,
        gender = gender,
        isNeutered = isNeutered,
        age = age,
        isAgeKnown = isAgeKnown,
        breed = breed,
        imageUrl = imageUrl,
        walkCount = walkCount,
        traits = traits.map { it.toEntity() }
    )
}

@Serializable
data class TraitDto(
    @SerialName("category")
    val category: String,

    @SerialName("option")
    val option: String
)
{
    fun toEntity() = TraitEntity(
        category = category,
        option = option
    )
}
