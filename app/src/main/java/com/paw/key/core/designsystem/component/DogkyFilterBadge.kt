package com.paw.key.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun DogkyFilterBadge(
    location: String,
    onLocationClick: () -> Unit,
    modifier: Modifier = Modifier,
    horizontalPadding: Int = 10,
    verticalPadding: Int = 9,
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(4.dp)
            )
            .background(
                color = PawKeyTheme.colors.opacity5Primary,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = PawKeyTheme.colors.primary,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(
                onClick = onLocationClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = location,
            style = PawKeyTheme.typography.buttonSmall,
            color = PawKeyTheme.colors.primary,
            modifier = Modifier.padding(horizontal = horizontalPadding.dp, vertical = verticalPadding.dp)
        )
    }
}

@Preview
@Composable
private fun RegionBadgePreview() {
    PawKeyTheme {
        DogkyFilterBadge(
            location = "w적음",
            onLocationClick = {}
        )
    }
}
