package com.paw.key.presentation.ui.course.walkreview.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.component.FeedbackItem
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.course.walkreview.state.WalkReviewContract

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WalkReviewFeedbackForm(
    icon: Int,
    title: String,
    selectedFeedbackItem: String?, // 선택된 옵션 텍스트
    feedbackList: List<String>,
    onClickFeedback: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(top = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // TODO: 아이콘 변경 예정
        /* Icon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = null
        ) */

        Text(
            text = title,
            style = PawKeyTheme.typography.body16M,
            color = PawKeyTheme.colors.black,
            modifier = Modifier
        )
    }

    FlowRow(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        feedbackList.forEach { itemText ->
            val isSelected = selectedFeedbackItem == itemText
            val textColor = if (isSelected) PawKeyTheme.colors.green500 else PawKeyTheme.colors.gray400
            val borderColor = if (isSelected) PawKeyTheme.colors.green500 else PawKeyTheme.colors.gray50

            FeedbackItem(
                item = itemText,
                textColor = textColor,
                borderColor = borderColor,
                onClickFeedback = onClickFeedback
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun WalkReviewFeedbackFormPreview() {
    PawKeyTheme {
        WalkReviewFeedbackForm(
            feedbackList = listOf(
                "킥보드나 자전거가 거의 없어요",
                "차량이 거의 다니지 않아요",
                "야간 조명이 잘 되어 있어요",
                "보도와 차도가 구분되어 있어요",
                "보도가 넓어서 산책하기 편했어요"
            ),
            icon = com.paw.key.R.drawable.ic_walk_review_location,
            title = "위치",
            selectedFeedbackItem = null,
            onClickFeedback = {}
        )
    }
}