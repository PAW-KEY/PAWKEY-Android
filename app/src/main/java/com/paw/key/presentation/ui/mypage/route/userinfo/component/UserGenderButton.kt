package com.paw.key.presentation.ui.mypage.route.userinfo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable

@Composable
fun UserGenderButton(
    user: String,
    isSelect: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = if (isSelect) {
                    PawKeyTheme.colors.primary
                } else {
                    PawKeyTheme.colors.background
                }
            )
            .border(
                width = 1.dp,
                color = if (isSelect) {
                    Color.Transparent
                } else {
                    PawKeyTheme.colors.defaultMiddle
                },
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable(onClick)
            .padding(horizontal = 68.dp, vertical = 16.dp)
    ) {
        Text(
            text = user,
            color = if (isSelect) {
                PawKeyTheme.colors.background
            } else {
                PawKeyTheme.colors.defaultMiddle
            },
            style = if (isSelect) {
                PawKeyTheme.typography.bodyActive
            } else {
                PawKeyTheme.typography.bodyDefault
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserGenderButtonPreview() {
    PawKeyTheme {
        UserGenderButton(
            user = "남자",
            isSelect = true,
            onClick = {}
        )
    }
}
