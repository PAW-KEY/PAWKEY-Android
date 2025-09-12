package com.paw.key.presentation.ui.mypage.component

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
private fun PreviewGrayChip() {
    GrayChip(
        text = "4km",
        onClick = {}
    )
}

@Composable
fun GrayChip(
    text: String,
    modifier: Modifier = Modifier,
    onClick : () -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(
                color = PawKeyTheme.colors.white2,
                shape = RoundedCornerShape(20.dp)
            )
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = PawKeyTheme.colors.gray400,
            style = PawKeyTheme.typography.caption12R
        )
    }
}