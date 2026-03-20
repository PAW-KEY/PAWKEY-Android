package com.paw.key.data.dto.request.posts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsFilterRequestDto(
    @SerialName("selectedOptions")
    val selectedOptions: List<FilterOptionDto>
)

@Serializable
data class FilterOptionDto(
    @SerialName("categoryId")
    val categoryId: Long,

    @SerialName("durationId")
    val durationId: Long,

    @SerialName("optionsIds")
    val optionsIds: Long
)
