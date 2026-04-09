package com.paw.key.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable

@Composable
fun DokiBorderButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDialog: Boolean = false,
) {
    val textColor = when {
        enabled -> PawKeyTheme.colors.primary
        else -> PawKeyTheme.colors.defaultMiddle
    }

    val textStyle = when {
        isDialog -> PawKeyTheme.typography.subTitle
        enabled -> PawKeyTheme.typography.mainButtonActive
        else -> PawKeyTheme.typography.mainButtonDefault
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = textColor,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = PawKeyTheme.colors.background,
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable {
                if (enabled) onClick()
            }
            .padding(vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun DokiBorderButtonPreview() {
    PawKeyTheme {
        DokiBorderButton(
            text = "산책 기록하기",
            enabled = true,
            onClick = {}
        )
    }
}
