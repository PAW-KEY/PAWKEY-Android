package com.paw.key.presentation.ui.dbti.test/*
package com.paw.key.presentation.ui.dbti.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.dbti.model.DBTIChoice
import com.paw.key.presentation.ui.dbti.model.DBTIQuestion
import com.paw.key.presentation.ui.dbti.model.DBTISideEffect
import com.paw.key.presentation.ui.dbti.model.DBTITestState
import com.paw.key.presentation.ui.dbti.test.component.QuestionOptionCard
import com.paw.key.presentation.ui.dbti.viewmodel.DBTIViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TestRoute(
    navigateUp: () -> Unit,
    navigateToResult: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DBTIViewModel = hiltViewModel(),
) {
    val testState by viewModel.testState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is DBTISideEffect.NavigateToResult -> navigateToResult()
                is DBTISideEffect.NavigateBack -> navigateUp()
                else -> {}
            }
        }
    }

    TestScreen(
        state = testState,
        onAnswerSelected = { questionId, choiceId ->
            viewModel.selectAnswer(questionId, choiceId)
        },
        onBackClick = {
            if (testState.currentQuestionIndex > 0) {
                viewModel.previousQuestion()
            } else {
                navigateUp()
            }
        },
        modifier = modifier
    )
}

@Composable
fun TestScreen(
    state: DBTITestState,
    onAnswerSelected: (Int, Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PawKeyTheme.colors.primaryGra1)
    ) {
        TopBar(
            title = "DBTI 검사",
            onBackClick = onBackClick,
            isBackVisible = true
        )

        if (state.isLoading || state.questions.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = PawKeyTheme.colors.primary
                )
            }
        } else {
            val currentQuestion = state.questions[state.currentQuestionIndex]
            val questionNumber = state.currentQuestionIndex + 1
            val totalQuestions = state.questions.size

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // 진행 상황
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Q$questionNumber",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PawKeyTheme.colors.primary,
                        style = PawKeyTheme.typography.body16Sb
                    )

                    Text(
                        text = "$questionNumber/$totalQuestions",
                        fontSize = 14.sp,
                        color = PawKeyTheme.colors.defaultDark,
                        style = PawKeyTheme.typography.body14R
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 진행 바
                LinearProgressIndicator(
                    progress = { questionNumber.toFloat() / totalQuestions.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = PawKeyTheme.colors.primary,
                    trackColor = PawKeyTheme.colors.defaultButton,
                )

                Spacer(modifier = Modifier.height(40.dp))

                // 질문
                Text(
                    text = currentQuestion.questionText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PawKeyTheme.colors.contents,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp,
                    style = PawKeyTheme.typography.title2
                )

                Spacer(modifier = Modifier.height(48.dp))

                // 선택지들
                currentQuestion.choices.forEachIndexed { index, choice ->
                    QuestionOptionCard(
                        choice = choice,
                        onClick = {
                            onAnswerSelected(currentQuestion.questionId, choice.choiceId)
                        }
                    )

                    if (index < currentQuestion.choices.size - 1) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TestScreenPreview() {
    PawKeyTheme {
        TestScreen(
            state = DBTITestState(
                questions = listOf(
                    DBTIQuestion(
                        questionId = 1,
                        questionText = "산책 나가기 전 우리 아이는...",
                        choices = listOf(
                            DBTIChoice(
                                choiceId = 1,
                                choiceText = "다른 강아지를 만나면 신나서 다가가요!",
                                iconType = "FRIENDLY"
                            ),
                            DBTIChoice(
                                choiceId = 2,
                                choiceText = "다른 강아지를 만나면 숨어버려요",
                                iconType = "SHY"
                            )
                        )
                    )
                ),
                isQuestionsLoaded = true
            ),
            onAnswerSelected = { _, _ -> },
            onBackClick = {}
        )
    }
}*/
