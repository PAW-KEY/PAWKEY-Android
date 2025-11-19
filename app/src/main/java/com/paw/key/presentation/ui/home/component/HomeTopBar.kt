package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.paw.key.R
import com.paw.key.core.designsystem.component.RegionBadge
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Preview(showBackground = true)
@Composable
private fun PreviewHomeTopBar() {
    PawKeyTheme {
        HomeTopBar(
            location = "강남구 역삼동",
            onLocationClick = {},
        )

    }
}

@Composable
fun HomeTopBar(
    location: String,
    onLocationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_logo),
            contentDescription = "logo",
        )

        RegionBadge(
            location = location,
            onLocationClick = onLocationClick,
        )
    }
}
