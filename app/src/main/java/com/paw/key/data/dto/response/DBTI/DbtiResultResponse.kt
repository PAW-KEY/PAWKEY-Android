package com.paw.key.data.dto.response.dbti

import kotlinx.serialization.Serializable

@Serializable
data class DbtiResultResponse(
    val code: String,
    val message: String,
    val data: DbtiResultData
)

@Serializable
data class DbtiResultData(
    val type: String,
    val name: String,
    val image: String?,
    val keyword: List<String>,
    val description: String,
    val analysis: List<AnalysisDto>
)

@Serializable
data class AnalysisDto(
    val axis: String,
    val leftLabel: String,
    val rightLabel: String,
    val dominantSide: String,
    val score: Int
)