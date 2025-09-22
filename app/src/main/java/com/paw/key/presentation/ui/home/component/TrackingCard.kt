package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import com.paw.key.R
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
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(
                color = PawKeyTheme.colors.black,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .clip(RoundedCornerShape(size = 15.dp))
            .clickable(onClick = onClick),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = "산책 시작하기",
                color = PawKeyTheme.colors.white1,
                style = PawKeyTheme.typography.head22B
            )

            Spacer(modifier = Modifier.weight(1F))

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_home_startcourse_button),
                contentDescription = "tracking",
                tint = Color.Unspecified,
            )
        }
    }
}