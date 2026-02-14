package com.paw.key.presentation.ui.dbti.result.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.paw.key.core.designsystem.theme.PawKeyTheme

// TraitAnalysis 데이터 클래스 정의
data class TraitAnalysis(
    val leftLabel: String,
    val rightLabel: String,
    val dominantSide: String,
    val score: Int
)

@Composable
fun ResultBox(
    type: String,
    name: String,
    imageUrl: String?,
    keywords: List<String>,
    description: String,
    analysis: List<TraitAnalysis>,  // TraitLevel 대신 TraitAnalysis
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.width(328.dp),
        color = PawKeyTheme.colors.background,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // DBTI Result Title Section
            Column(
                modifier = Modifier.width(210.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "내 강아지의 성향은",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = PawKeyTheme.colors.defaultDark,
                    textAlign = TextAlign.Center,
                    style = PawKeyTheme.typography.body14M,
                    modifier = Modifier.fillMaxWidth()
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = type,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PawKeyTheme.colors.contents,
                        letterSpacing = (-0.48).sp,
                        textAlign = TextAlign.Center,
                        style = PawKeyTheme.typography.header1
                    )
                    Text(
                        text = name,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = PawKeyTheme.colors.primary,
                        letterSpacing = (-0.56).sp,
                        textAlign = TextAlign.Center,
                        style = PawKeyTheme.typography.header1
                    )
                }
            }

            // DBTI Group Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Image
                if (imageUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.size(150.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .background(PawKeyTheme.colors.defaultButton)
                    )
                }

                // Keywords (Character Chips)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    keywords.forEach { keyword ->
                        CharacterChip(text = "# $keyword")
                    }
                }

                // Description
                Text(
                    text = description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = PawKeyTheme.colors.contents,
                    textAlign = TextAlign.Center,
                    style = PawKeyTheme.typography.body14M,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp)
                )
            }

            // Analysis (Trait Bars)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                analysis.forEach { trait ->
                    TraitBar(
                        leftLabel = trait.leftLabel,
                        rightLabel = trait.rightLabel,
                        dominantSide = trait.dominantSide,
                        score = trait.score
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultBoxPreview() {
    PawKeyTheme {
        ResultBox(
            type = "ISF",
            name = "온순한 방구석멍",
            imageUrl = null,
            keywords = listOf("차분", "다소 활발", "탐험"),
            description = "내향적이고 온순하며 자유로운 흐름 속에서 소소한 행복을 찾습니다.",
            analysis = listOf(
                TraitAnalysis(
                    leftLabel = "탐험가",
                    rightLabel = "휴식가",
                    dominantSide = "right",
                    score = 2
                ),
                TraitAnalysis(
                    leftLabel = "인싸싸",
                    rightLabel = "독고다",
                    dominantSide = "left",
                    score = 3
                ),
                TraitAnalysis(
                    leftLabel = "루틴러",
                    rightLabel = "자유러",
                    dominantSide = "right",
                    score = 2
                )
            )
        )
    }
}