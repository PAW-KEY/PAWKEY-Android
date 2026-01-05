package com.paw.key.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable

@Composable
fun DokiButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDialog: Boolean = false,
) {
    val backgroundColor = when {
        enabled -> PawKeyTheme.colors.primary
        else -> PawKeyTheme.colors.defaultButton
    }

    val textColor = when {
        enabled -> PawKeyTheme.colors.background
        else -> PawKeyTheme.colors.defaultDark
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .noRippleClickable(onClick = onClick)
            .padding(
                if (isDialog) {
                    PaddingValues(horizontal = 6.dp, vertical = 17.dp)
                } else {
                    PaddingValues(vertical = 18.dp)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = if (isDialog) PawKeyTheme.typography.subTitle else PawKeyTheme.typography.mainButtonDefault,
            color = textColor
        )
    }
}

@Preview
@Composable
private fun DogkyButtonPreview() {
    PawKeyTheme {
        DokiButton(
            text = "산책 종료하기",
            enabled = false,
            onClick = {}
        )
    }
}