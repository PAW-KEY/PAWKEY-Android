package com.paw.key.presentation.ui.home.component

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

@Preview(showBackground = true)
@Composable
private fun PreviewHistoryChip() {
    PawKeyTheme {
        HistoryChip(
            text = "4km"
        )
    }
}

@Composable
fun HistoryChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .background(
                color = PawKeyTheme.colors.white2,
                shape = RoundedCornerShape(4.dp),
            )
            .padding(horizontal = 6.dp, vertical = 4.dp),
    ) {
        Text(
            text = text,
            color = PawKeyTheme.colors.black,
            style = PawKeyTheme.typography.body14M,
        )
    }
}