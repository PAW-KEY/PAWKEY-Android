package com.paw.key.presentation.ui.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable

@Preview(showBackground = true)
@Composable
private fun PreviewLocationButton() {
    PawKeyTheme {
        LocationButton(
            location = "강남구",
            isEnable = true,
            onClick = {}
        )
    }
}

@Composable
fun LocationButton(
    location: String,
    isEnable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(42.dp)
            .width(77.dp)
            .noRippleClickable { onClick() }
            .background(color = PawKeyTheme.colors.white1)
            .border(
                width = 2.dp,
                color = if (isEnable) {
                    PawKeyTheme.colors.green500
                } else {
                    PawKeyTheme.colors.gray200
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = location,
            color = if (isEnable) {
                PawKeyTheme.colors.green500
            } else {
                PawKeyTheme.colors.gray200
            },
            style = if (isEnable) {
                PawKeyTheme.typography.body14Sb
            } else {
                PawKeyTheme.typography.body14R
            }
        )
    }
}