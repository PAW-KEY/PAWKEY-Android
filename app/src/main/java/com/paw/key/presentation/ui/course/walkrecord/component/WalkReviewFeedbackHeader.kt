package com.paw.key.presentation.ui.course.walkrecord.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun WalkReviewFeedbackHeader (
    petName : String,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "${petName}와의 산책 어땠나요?",
            style = PawKeyTheme.typography.head18Sb,
            color = PawKeyTheme.colors.black
        )

        Text(
            text = "카테고리 별로 1개 이상의 키워드를 선물해주세요.",
            style = PawKeyTheme.typography.body14R,
            color = PawKeyTheme.colors.gray300,
            modifier = Modifier
                .padding(top = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WalkReviewFeedbackHeaderPreview() {
    PawKeyTheme {
        WalkReviewFeedbackHeader(
            petName = "뽀삐"
        )
    }
}