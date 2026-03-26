package com.paw.key.presentation.ui.dbti.test

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import com.paw.key.core.designsystem.component.DokiButton
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.dbti.component.SelectCard


data class TestOption(
    val id: Int,
    val topText: String,
    val bottomText: String,
    val imageUrl: String? = null
)

@Composable
fun TestScreen(
    categoryName: String,
    questionText: String,
    options: List<TestOption>,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedOptionId by remember { mutableStateOf<Int?>(null) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(
                title = "프로필 설정",
                onBackClick = onBackClick,
                isBackVisible = true
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.weight(67f)) // TopBar ↔ 텍스트

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = categoryName,
                        fontWeight = FontWeight.SemiBold,
                        color = PawKeyTheme.colors.primary,
                        style = PawKeyTheme.typography.bodyActive
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = questionText,
                        color = PawKeyTheme.colors.contents,
                        textAlign = TextAlign.Center,
                        lineHeight = 28.sp,
                        style = PawKeyTheme.typography.header3,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.weight(30f))

                Spacer(modifier = Modifier.weight(219.31f))

                Spacer(modifier = Modifier.weight(1f))

                DokiButton(
                    text = "다음으로",
                    onClick = onNextClick,
                    enabled = selectedOptionId != null,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(34.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            options.forEach { option ->
                SelectCard(
                    imageUrl = option.imageUrl,
                    topText = option.topText,
                    bottomText = option.bottomText,
                    isSelected = selectedOptionId == option.id,
                    onClick = { selectedOptionId = option.id },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TestScreenPreview() {
    PawKeyTheme {
        TestScreen(
            categoryName = "부끄멍 vs 적극멍",
            questionText = "산책 중 다른 강아지를 만나면\n우리 강아지는...",
            options = listOf(
                TestOption(
                    id = 1,
                    topText = "잠깐 눈치만 보고",
                    bottomText = "거리를 유지해요",
                    imageUrl = null
                ),
                TestOption(
                    id = 2,
                    topText = "먼저 다가가서",
                    bottomText = "인사하고 놀자고 해요",
                    imageUrl = null
                )
            ),
            onBackClick = {},
            onNextClick = {}
        )
    }
}