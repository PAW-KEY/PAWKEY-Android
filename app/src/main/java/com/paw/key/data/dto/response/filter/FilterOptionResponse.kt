package com.paw.key.data.dto.response.filter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilterOptionResponseDto(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: FilterOptionResponse
)

@Serializable
data class FilterOptionResponse(
    @SerialName("categoryList")
    val categoryList: List<CategoryDto>
)

@Serializable
data class CategoryDto(
    @SerialName("categoryId")
    val categoryId: Int? = null,
    @SerialName("categoryName")
    val categoryName: String? = null,
    @SerialName("categoryOptions")
    val categoryOptions: List<CategoryOptionDto>? = null
)

@Serializable
data class CategoryOptionDto(
    @SerialName("categoryOptionId")
    val categoryOptionId: Int? = null,
    @SerialName("categoryOptionText")
    val categoryOptionText: String? = null
)