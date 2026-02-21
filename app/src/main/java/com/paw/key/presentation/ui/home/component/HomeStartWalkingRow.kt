package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable

@Composable
fun HomeStartWalkingRow(
    petName : String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "${petName}와 함께",
                style = PawKeyTheme.typography.subTitle,
                color = PawKeyTheme.colors.contents
            )

            Text(
                text = "지금 산책을 시작해보세요!",
                style = PawKeyTheme.typography.subButtonDefault,
                color = PawKeyTheme.colors.contents
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(
                    color = PawKeyTheme.colors.primary,
                    shape = RoundedCornerShape(999.dp)
                )
                .noRippleClickable(onClick = onClick),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "산책 시작",
                style = PawKeyTheme.typography.bodyBold,
                color = PawKeyTheme.colors.background,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }
    }
}

@Preview
@Composable
private fun HomeStartWalkingRowPreview() {
    PawKeyTheme {
        HomeStartWalkingRow(
            petName = "보리",
            onClick = {}
        )
    }
}
