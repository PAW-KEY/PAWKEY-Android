package com.paw.key.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewSubChip() {
    SubChip(
        text = "4km"
    )

}

@Composable
fun SubChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        Modifier
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(6.dp, 4.dp)
    ) {
        Text(
            text = text,
            color = Color.DarkGray
        )
    }

}