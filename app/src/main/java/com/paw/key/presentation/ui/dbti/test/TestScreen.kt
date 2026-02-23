package com.paw.key.presentation.ui.dbti.test

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import com.paw.key.core.designsystem.component.DokiButton
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.dbti.component.SelectCard

data class TestOption(
    val id: Int,
    val text: String,
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
                Spacer(modifier = Modifier.weight(67f))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = categoryName,
                        color = PawKeyTheme.colors.primary,
                        style = PawKeyTheme.typography.bodyActive
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = questionText,
                        color = PawKeyTheme.colors.contents,
                        textAlign = TextAlign.Center,
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
                    text = option.text,
                    imageUrl = option.imageUrl,
                    onClick = { selectedOptionId = option.id },
                    modifier = Modifier.weight(1f),
                    isSelected = selectedOptionId == option.id
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
                    text = "잠깐 눈치만 보고\n거리를 유지해요",
                    imageUrl = null
                ),
                TestOption(
                    id = 2,
                    text = "먼저 다가가서\n인사하고 놀자고 해요",
                    imageUrl = null
                )
            ),
            onBackClick = {},
            onNextClick = {}
        )
    }
}