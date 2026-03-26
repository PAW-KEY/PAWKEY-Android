package com.paw.key.data.dto.response.walkpreparation

import com.paw.key.domain.entity.walkpreparation.WalkPreparationEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalkPreparationResponseDto(
    @SerialName("preparation")
    val preparationList: List<String>
) {
    fun toEntity() = WalkPreparationEntity(
        preparationList = preparationList
    )
}
