package com.paw.key.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable

@Preview
@Composable
private fun PreviewSubChip() {
    SubChip(
        text = "4km",
        onClick = {}
    )
}

@Composable
fun SubChip(
    text: String,
    modifier: Modifier = Modifier,
    onClick : () -> Unit = {},
    isActionChip: Boolean = false, //true -> 회색
) {
    Box(
        modifier = modifier
            .background(
                color = if (isActionChip) PawKeyTheme.colors.white2 else PawKeyTheme.colors.green50,
                shape = RoundedCornerShape(20.dp)
            )
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = if (isActionChip) PawKeyTheme.colors.gray700 else PawKeyTheme.colors.green600,
            style = PawKeyTheme.typography.caption12R
        )
    }
}