package com.paw.key.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable

@Preview
@Composable
private fun PreviewSubChip() {
    SubChip(
        text = "+ 9",
        onClick = {}
    )
}

@Composable
fun SubChip(
    text: String,
    modifier: Modifier = Modifier,
    onClick : () -> Unit = {},
    isDividerChip: Boolean = false, //true -> detail의 16dp
    isActionChip: Boolean = false, //true -> 회색
) {
    Box(
        modifier = modifier
            .background(
                color = if (isActionChip) PawKeyTheme.colors.primaryGra1 else PawKeyTheme.colors.defaultButton,
                shape = if (isActionChip) RoundedCornerShape(8.dp) else RoundedCornerShape(36.dp)
            )
            .noRippleClickable(onClick = onClick)
            .then(
                if (isDividerChip) Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                else Modifier.padding(8.dp)
            )
    ) {
        Text(
            text = text,
            color = if (isActionChip) PawKeyTheme.colors.primary else PawKeyTheme.colors.defaultMiddle,
            style = if (isActionChip) PawKeyTheme.typography.subButtonActive else PawKeyTheme.typography.buttonSmall
        )
    }
}