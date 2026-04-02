package com.paw.key.presentation.ui.dbti.test.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.entity.dbti.DbtiQuestionEntity
import com.paw.key.domain.usecase.GetDbtiQuestionsUseCase
import com.paw.key.domain.usecase.SubmitDbtiResultUseCase
import com.paw.key.presentation.ui.dbti.test.model.TestOptionModel
import com.paw.key.presentation.ui.dbti.test.state.TestUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val getDbtiQuestionsUseCase: GetDbtiQuestionsUseCase,
    private val submitDbtiResultUseCase: SubmitDbtiResultUseCase
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
        } else {
            // 마지막 질문 → 결과 제출
            submitResult()
        }
    }

    private fun submitResult() {
        viewModelScope.launch {
            _uiState.value = TestUiState.Loading

            // 순서대로 optionId 리스트 생성
            val optionIds = questions.map { question ->
                selectedAnswers[question.id] ?: 0
            }

            submitDbtiResultUseCase(
                petId = 2L, // TODO: 실제 petId로 변경
                optionIds = optionIds
            )
                .onSuccess { result ->
                    // TODO: ResultScreen으로 이동하면서 결과 전달
                    // navigateToResult(result)
                }
                .onFailure { error ->
                    _uiState.value = TestUiState.Error(
                        error.message ?: "결과 제출에 실패했습니다"
                    )
                }
        }
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