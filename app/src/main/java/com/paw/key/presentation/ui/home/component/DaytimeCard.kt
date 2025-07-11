package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Preview(showBackground = true)
@Composable
private fun PreviewDaytimeCard() {
    PawKeyTheme {
        DaytimeCard(daytime = "05:06", daystate = "일출")
    }
}

@Composable
fun DaytimeCard(
    daytime: String,
    daystate: String,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
            .width(81.dp)
            .height(110.dp)
            .background(
                color = PawKeyTheme.colors.white1,
                shape = RoundedCornerShape(15.dp),)
            .clip(RoundedCornerShape(15.dp)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(31.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(bottom = 14.dp)
                .zIndex(1F),
        ) {
            Text(
                text = daytime,
                color = PawKeyTheme.colors.black,
                style = PawKeyTheme.typography.head20B1,
            )

            Text(
                text = daystate,
                color = PawKeyTheme.colors.black,
                style = PawKeyTheme.typography.body16Sb,
            )
        }
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.img_home_sunset),
            contentDescription = "daytime",
            tint = Color.Unspecified,
        )

    }
}