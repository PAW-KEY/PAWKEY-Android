package com.paw.key.data.dto.response.home

import kotlinx.serialization.Serializable

@Serializable
data class HomeRegionResponse<T>(
    val code: String,
    val message: String,
    val data: T? = null
)