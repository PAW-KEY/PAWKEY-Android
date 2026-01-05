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

@Composable
fun InfoChip(
    text: String,
    modifier: Modifier = Modifier,
    isActionChip: Boolean = false,
) {
    val backGroundColor = if (isActionChip) {
        PawKeyTheme.colors.primary
    } else {
        PawKeyTheme.colors.primaryGra1
    }

    val textColor = if (isActionChip) {
        PawKeyTheme.colors.background
    } else {
        PawKeyTheme.colors.primary
    }

    val fontStyle = if (isActionChip) {
        PawKeyTheme.typography.bodySmall
    } else {
        PawKeyTheme.typography.subButtonDefault
    }

    Box (
        modifier = modifier
            .background(
                color = backGroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = fontStyle
        )
    }
}

@Preview
@Composable
private fun InfoChipPreview() {
    PawKeyTheme {
        InfoChip(
            text = "2.2 km",
            isActionChip = true
        )
    }
}