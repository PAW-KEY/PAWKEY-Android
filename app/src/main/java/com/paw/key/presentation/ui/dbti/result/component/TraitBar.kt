package com.paw.key.presentation.ui.dbti.result.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun TraitBar(
    leftLabel: String,
    rightLabel: String,
    dominantSide: String,  // "left" 또는 "right"
    score: Int,            // 1, 2, 3
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = leftLabel,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = PawKeyTheme.colors.contents,
            textAlign = TextAlign.Center,
            style = PawKeyTheme.typography.body14Sb,
            modifier = Modifier.width(40.dp)
        )

        TraitBlocks(
            dominantSide = dominantSide,
            score = score
        )

        Text(
            text = rightLabel,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = PawKeyTheme.colors.defaultMiddle,
            textAlign = TextAlign.Center,
            style = PawKeyTheme.typography.body14R,
            modifier = Modifier.width(40.dp)
        )
    }
}

@Composable
private fun TraitBlocks(
    dominantSide: String,
    score: Int,
    modifier: Modifier = Modifier,
) {
    val activeColor = PawKeyTheme.colors.primaryGra5
    val inactiveColor = PawKeyTheme.colors.defaultButton

    // dominantSide와 score에 따라 블록 색상 결정
    val (first, second, third) = when {
        dominantSide == "left" && score == 3 -> Triple(activeColor, activeColor, activeColor)
        dominantSide == "left" && score == 2 -> Triple(activeColor, activeColor, inactiveColor)
        dominantSide == "left" && score == 1 -> Triple(activeColor, inactiveColor, inactiveColor)
        dominantSide == "right" && score == 3 -> Triple(activeColor, activeColor, activeColor)
        dominantSide == "right" && score == 2 -> Triple(inactiveColor, activeColor, activeColor)
        dominantSide == "right" && score == 1 -> Triple(inactiveColor, inactiveColor, activeColor)
        else -> Triple(inactiveColor, inactiveColor, inactiveColor) // 기본값
    }

    Row(
        modifier = modifier.width(175.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(10.dp)
                .background(first, RoundedCornerShape(4.dp))
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(10.dp)
                .background(second, RoundedCornerShape(4.dp))
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(10.dp)
                .background(third, RoundedCornerShape(4.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TraitBarPreview() {
    PawKeyTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TraitBar(
                leftLabel = "탐험가",
                rightLabel = "휴식가",
                dominantSide = "right",
                score = 2
            )
            TraitBar(
                leftLabel = "인싸싸",
                rightLabel = "독고다",
                dominantSide = "left",
                score = 3
            )
            TraitBar(
                leftLabel = "루틴러",
                rightLabel = "자유러",
                dominantSide = "right",
                score = 2
            )
        }
    }
}