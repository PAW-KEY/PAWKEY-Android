package com.paw.key.data.dto.request.list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsListRequestDto(
    @SerialName("durationStart")
    val durationStart: Int? = null,
    @SerialName("durationEnd")
    val durationEnd: Int? = null,
    @SerialName("selectedOptions")
    val selectedOptions: List<TraitList>? = null
)

@Serializable
data class TraitList(
    @SerialName("categoryId")
    val categoryId: Int? = null,
    @SerialName("optionsIds")
    val optionIds: List<Int>? = null
)