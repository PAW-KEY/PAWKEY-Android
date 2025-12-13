package com.paw.key.presentation.ui.mypage.courseinfo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paw.key.R
import com.paw.key.core.designsystem.component.DogkyFilterBadge
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.designsystem.theme.PretendardBold

@Composable
fun CourseRouteItem(
    location: String,
    routeTitle: String,
    routeImage: String,
    routeDistance: String,
    routeTime: String,
    routeDate: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(9f / 16f)
                .background(
                    color = PawKeyTheme.colors.gray25,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                model = routeImage,
                contentDescription = "routeImage",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            DogkyFilterBadge(
                location = location,
                onLocationClick = {},
                horizontalPadding = 6,
                verticalPadding = 5,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            )

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_heart_default),
                    contentDescription = "heart",
                    tint = Color.Unspecified
                )
            }
        }

        CourseRouteItemInfo(
            routeTitle = routeTitle,
            routeDistance = routeDistance,
            routeTime = routeTime,
            routeDate = routeDate
        )
    }
}

@Composable
fun CourseRouteItemInfo(
    routeTitle: String,
    routeDistance: String,
    routeTime: String,
    routeDate: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(
                top = 12.dp,
                start = 4.dp,
                end = 4.dp
            )
    ) {
        Text(
            text = routeTitle,
            style = PawKeyTheme.typography.bodyActive,
            fontFamily = PretendardBold,
            color = PawKeyTheme.colors.contents
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "현재 거리로부터 ${routeDistance}km",
            style = PawKeyTheme.typography.subButtonDefault,
            color = PawKeyTheme.colors.defaultMiddle
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CourseIconText(
                icon = ImageVector.vectorResource(R.drawable.ic_calendar),
                text = routeDate
            )

            CourseIconText(
                icon = ImageVector.vectorResource(R.drawable.ic_alarm),
                text = "${routeTime}min"
            )
        }
    }
}

@Composable
fun CourseIconText(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            style = PawKeyTheme.typography.buttonSmall,
            color = PawKeyTheme.colors.defaultMiddle
        )
    }
}

@Preview
@Composable
private fun CourseRouteItemPreview() {
    PawKeyTheme {
        CourseRouteItem(
            routeImage = "",
            routeDistance = "10",
            routeTime = "10",
            routeTitle = "강남구 역삼동",
            routeDate = "2025/11/06",
            location = "강남구 역삼동"
        )
    }

}
