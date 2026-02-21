package com.paw.key.presentation.ui.detail.component

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun DetailTopReview(
    reviewCount: Int,
    modifier: Modifier = Modifier,
    isShared: Boolean = false, // 공유 여부
) {
    val emptyReviewText = if (isShared) "아직은 후기가 없어요." else "현재는 비공개 상태에요.\n" +
            "공개로 전환해 산책 루트를 공유해보세요."

    Column(
        modifier = modifier
    ) {
        Row (
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "이런 점이 좋았어요",
                style = PawKeyTheme.typography.mainButtonActive,
                color = PawKeyTheme.colors.contents
            )

            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_edit),
                tint = Color.Unspecified,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "$reviewCount",
                style = PawKeyTheme.typography.bodySmall,
                color = PawKeyTheme.colors.defaultMiddle
            )
        }

        // Todo: 서버 기준으로 변경
        if (reviewCount == 0) {
            Text(
                text = emptyReviewText,
                style = PawKeyTheme.typography.subTitle,
                color = PawKeyTheme.colors.defaultMiddle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PercentageItem(
                    text = "~가 좋아요",
                    widthPercent = 1f,
                    backgroundColor = PawKeyTheme.colors.primaryGra5
                )

                PercentageItem(
                    text = "후기 업선",
                    widthPercent = 0.75f,
                    backgroundColor = PawKeyTheme.colors.primaryGra2
                )

                PercentageItem(
                    text = "후기 업선",
                    widthPercent = 0.55f,
                    backgroundColor = PawKeyTheme.colors.primaryGra2
                )
            }
        }
    }
}

@Composable
private fun PercentageItem(
    text: String,
    widthPercent: Float,
    @ColorRes backgroundColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(widthPercent)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(vertical = 11.dp, horizontal = 14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = PawKeyTheme.typography.subButtonActive,
            color = PawKeyTheme.colors.contents,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
    }
}

@Preview
@Composable
private fun DetailTopReviewPreview() {
    PawKeyTheme {
        DetailTopReview(
            reviewCount = 30
        )
    }
}