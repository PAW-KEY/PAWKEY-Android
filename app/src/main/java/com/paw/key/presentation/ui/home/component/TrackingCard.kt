package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme


@Preview
@Composable
private fun PreviewTrackingCard() {
    PawKeyTheme {
        TrackingCard(
            onClick = {}
        )
    }
}

@Composable
fun TrackingCard(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .width(235.dp)
            .height(110.dp)
            .clip(RoundedCornerShape(size = 15.dp))
            .background(color = PawKeyTheme.colors.black)
            .clickable(onClick = onClick),
    ) {
        // TODO: img -> component 나오면 수정
    }
}