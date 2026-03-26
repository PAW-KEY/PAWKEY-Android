package com.paw.key.data.dto.request.walkpreparation

import com.paw.key.domain.entity.walkpreparation.WalkPreparationEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalkPreparationRequestDto(
    @SerialName("preparation")
    val preparationList: List<String>
) {
    fun toEntity() = WalkPreparationEntity(
        preparationList = preparationList
    )
}

fun WalkPreparationEntity.toDto() = WalkPreparationRequestDto(
    preparationList = preparationList
)