package com.paw.key.presentation.ui.mypage.route.courseinfo.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun MyReviewCard(
    cardTitle: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PawKeyTheme.colors.white1,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = PawKeyTheme.colors.primary,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = cardTitle,
            color = PawKeyTheme.colors.black,
            style = PawKeyTheme.typography.mainButtonActive
        )

        Spacer(modifier = Modifier.height(4.dp))

        ReviewContent(
            iconRes = R.drawable.ic_mypage_location_mark,
            content = "강남구 역삼동"
        )
        ReviewContent(
            iconRes = R.drawable.ic_mypage_time_mark,
            content = "만족"
        )

        Spacer(modifier = Modifier.height(18.dp))

        ReviewChip(
            chipTitle = "차량 적음"
        )

        Spacer(modifier = Modifier.height(5.dp))

        ReviewAdditionArea(
            additionEa = "+9"
        )
    }

}

@Composable
private fun ReviewAdditionArea(
    additionEa: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalDivider(
            thickness = 1.5.dp,
            color = PawKeyTheme.colors.defaultButton
        )

        Text(
            text = additionEa,
            color = PawKeyTheme.colors.defaultMiddle,
            style = PawKeyTheme.typography.buttonSmall,
            modifier = Modifier
                .background(
                    color = PawKeyTheme.colors.defaultButton,
                    shape = RoundedCornerShape(999.dp)
                )
                .padding(vertical = 4.dp, horizontal = 16.dp)
        )

    }

}

@Composable
private fun ReviewChip(
    chipTitle: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = chipTitle,
        color = PawKeyTheme.colors.primary,
        style = PawKeyTheme.typography.subButtonActive,
        modifier = modifier
            .background(
                color = PawKeyTheme.colors.primary.copy(alpha = 0.05f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    )

}

@Composable
private fun ReviewContent(
    @DrawableRes iconRes: Int,
    content: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Text(
            text = content,
            color = PawKeyTheme.colors.defaultDark,
            style = PawKeyTheme.typography.bodyActive
        )
    }

}

@Preview
@Composable
private fun PreviewMyReviewCard() {
    PawKeyTheme {
        MyReviewCard(
            cardTitle = "단지와의 룰루랄라"
        )
    }
}