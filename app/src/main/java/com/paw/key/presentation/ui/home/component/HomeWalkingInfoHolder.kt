package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.home.model.WalkingInfo

@Composable
fun HomeWalkingInfoHolder(
    walkingInfo: WalkingInfo,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .border(
                width = 1.dp,
                color = PawKeyTheme.colors.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(
                RoundedCornerShape(8.dp)
            )
            .background(
                color = PawKeyTheme.colors.opacity5Primary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomeWalkingInfoHolderItem(
            text = "누적 거리",
            value = "${walkingInfo.cumulativeDistance} KM",
            modifier = Modifier.weight(1f)
        )

        VerticalDivider(
            thickness = 2.dp,
            color = PawKeyTheme.colors.primary,
            modifier = Modifier
                .fillMaxHeight()
        )

        HomeWalkingInfoHolderItem(
            text = "총 산책 시간",
            value = walkingInfo.walkingTime,
            modifier = Modifier.weight(1f)
        )

        VerticalDivider(
            thickness = 2.dp,
            color = PawKeyTheme.colors.primary,
            modifier = Modifier
                .fillMaxHeight()
        )

        HomeWalkingInfoHolderItem(
            text = "산책 횟수",
            value = "${walkingInfo.walkingCount} 회",
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
fun HomeWalkingInfoHolderItem(
    text: String,
    value : String,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = text,
            style = PawKeyTheme.typography.bodyActive, // Todo: bold로
            color = PawKeyTheme.colors.primary
        )

        Text(
            text = value,
            style = PawKeyTheme.typography.bodyActive,
            color = PawKeyTheme.colors.primary
        )
    }
}

@Preview
@Composable
private fun HomeWalkingInfoHolderPreview() {
    PawKeyTheme {
        HomeWalkingInfoHolder(
            walkingInfo = WalkingInfo(
                cumulativeDistance = 10.0,
                walkingCount = 1,
                walkingTime = "00:00:00",
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
