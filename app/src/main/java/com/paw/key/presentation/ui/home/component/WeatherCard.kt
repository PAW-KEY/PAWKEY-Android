package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Preview
@Composable
private fun PreviewWeatherCard() {
    PawKeyTheme {
        WeatherCard(
            weathertitle = "35°",
            weathersub1 = "35°",
            weathersub2 = "21°",
            rating = "0",
            weatherIcon = R.drawable.ic_home_weather
        )
    }
}

@Composable
fun WeatherCard(
    weathertitle: String,
    weathersub1: String,
    weathersub2: String,
    rating: String,
    weatherIcon: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = PawKeyTheme.colors.white2,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .padding(vertical = 12.dp, horizontal = 12.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(weatherIcon),
            contentDescription = "weather",
            tint = Color.Unspecified,
        )

        Text(
            text = weathertitle,
            color = PawKeyTheme.colors.black,
            style = PawKeyTheme.typography.head22B.copy(
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(start = 4.dp),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 8.dp),
        ) {
            Text(
                text = weathersub1,
                style = PawKeyTheme.typography.body14R,
            )

            Text(
                text = weathersub2,
                style = PawKeyTheme.typography.body14R,
                color = PawKeyTheme.colors.black.copy(alpha = 0.5f),
                modifier = Modifier.padding(start = 6.dp),
            )
        }

        Spacer(modifier = Modifier.weight(1F))

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_rainrating),
            contentDescription = "rainrating",
            tint = Color.Unspecified,
        )

        Text(
            text = rating,
            style = PawKeyTheme.typography.head22B.copy(
                fontSize = 24.sp,
            ),
        )

        Text(
            text = "ml",
            style = PawKeyTheme.typography.body14Sb, modifier = modifier
                .padding(top = 4.dp),
        )
    }
}