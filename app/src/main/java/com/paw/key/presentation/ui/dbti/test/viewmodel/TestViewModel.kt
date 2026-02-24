package com.paw.key.presentation.ui.dbti.test.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.entity.dbti.DbtiQuestionEntity
import com.paw.key.domain.usecase.GetDbtiQuestionsUseCase
import com.paw.key.presentation.ui.dbti.test.model.TestOptionModel
import com.paw.key.presentation.ui.dbti.test.state.TestUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val getDbtiQuestionsUseCase: GetDbtiQuestionsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<TestUiState>(TestUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var questions: List<DbtiQuestionEntity> = emptyList()
    private var currentIndex = 0
    private val selectedAnswers = mutableMapOf<Int, Int>() // questionId to optionId

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            _uiState.value = TestUiState.Loading

            getDbtiQuestionsUseCase()
                .onSuccess { questionList ->
                    questions = questionList
                    if (questionList.isNotEmpty()) {
                        updateCurrentQuestion()
                    } else {
                        _uiState.value = TestUiState.Error("질문을 불러올 수 없습니다.")
                    }
                }
                .onFailure { error ->
                    _uiState.value = TestUiState.Error(
                        error.message ?: "알 수 없는 오류가 발생했습니다"
                    )
                }
        }
    }

    fun selectOption(optionId: Int) {
        val currentQuestion = questions.getOrNull(currentIndex) ?: return
        selectedAnswers[currentQuestion.id] = optionId
        updateCurrentQuestion()
    }

    fun nextQuestion() {
        if (currentIndex < questions.size - 1) {
            currentIndex++
            updateCurrentQuestion()
        }
        // TODO: 마지막 질문이면 결과 제출 API 호출
    }

    fun previousQuestion() {
        if (currentIndex > 0) {
            currentIndex--
            updateCurrentQuestion()
        }
    }

    private fun updateCurrentQuestion() {
        val currentQuestion = questions.getOrNull(currentIndex) ?: return

        _uiState.value = TestUiState.Success(
            questionNumber = currentIndex + 1,
            totalQuestions = questions.size,
            categoryName = currentQuestion.categoryName,
            questionText = currentQuestion.content,
            options = currentQuestion.options.map { option ->
                TestOptionModel(
                    id = option.id,
                    text = option.content,
                    imageUrl = option.imageUrl
                )
            },
            selectedOptionId = selectedAnswers[currentQuestion.id]
        )
    }

    fun retry() {
        loadQuestions()
    }
}