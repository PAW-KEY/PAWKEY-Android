package com.paw.key.presentation.ui.mypage.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun InfoChip(
    chipText: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = PawKeyTheme.colors.primary,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 6.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = chipText,
            style = PawKeyTheme.typography.buttonSmall,
            color = PawKeyTheme.colors.background
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ReviewInfoChip() {
    PawKeyTheme {
        InfoChip(
            chipText = "동네인기스타 ddd"
        )
    }
}