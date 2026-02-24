package com.paw.key.presentation.ui.dbti.test.state

import com.paw.key.presentation.ui.dbti.test.model.TestOptionModel

sealed interface TestUiState {
    data object Loading : TestUiState

    data class Success(
        val questionNumber: Int,
        val totalQuestions: Int,
        val categoryName: String,
        val questionText: String,
        val options: List<TestOptionModel>,
        val selectedOptionId: Int?
    ) : TestUiState

    data class Error(val message: String) : TestUiState
}