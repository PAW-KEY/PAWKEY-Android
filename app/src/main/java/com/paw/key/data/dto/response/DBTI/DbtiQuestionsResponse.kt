package com.paw.key.data.dto.response.dbti

import kotlinx.serialization.Serializable

@Serializable
data class DbtiQuestionsResponse(
    val code: String,
    val message: String,
    val data: QuestionsData
)

@Serializable
data class QuestionsData(
    val questions: List<QuestionDto>
)

@Serializable
data class QuestionDto(
    val id: Int,
    val category: CategoryDto,
    val content: String,
    val options: List<OptionDto>
)

@Serializable
data class CategoryDto(
    val code: String,
    val name: String
)

@Serializable
data class OptionDto(
    val id: Int,
    val content: String,
    val imageUrl: String?,
    val value: String
)