package com.paw.key.data.dto.request.dbti

import kotlinx.serialization.Serializable

@Serializable
data class DbtiResultRequest(
    val optionIds: List<Int>
)