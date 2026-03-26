package com.paw.key.presentation.ui.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
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
import com.paw.key.R
import com.paw.key.core.designsystem.component.DogkyFilterBadge
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.home.model.HomeWeatherModel

@Preview(showBackground = true)
@Composable
private fun PreviewHomeTopBar() {
    PawKeyTheme {
        HomeTopBar(
            homeWeatherModel = HomeWeatherModel(),
            onLocationClick = {},
        )

    }
}

@Composable
fun HomeTopBar(
    homeWeatherModel: HomeWeatherModel,
    onLocationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_logo),
            contentDescription = "logo",
            modifier = Modifier.align(Alignment.Start)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WeatherHolder(
                weatherIcon = R.drawable.ic_home_temperature,
                content = "${homeWeatherModel.temperature}℃",
            )

            Spacer(modifier = Modifier.width(8.dp))

            WeatherHolder(
                weatherIcon = R.drawable.ic_home_drop,
                content = "${homeWeatherModel.rainyMm}mm",
            )
            
            Spacer(modifier = Modifier.weight(1f))

            DogkyFilterBadge(
                location = homeWeatherModel.region,
                onLocationClick = onLocationClick,
            )
        }
    }
}

@Composable
fun WeatherHolder(
    @DrawableRes weatherIcon: Int,
    content: String,
    modifier: Modifier = Modifier
) {
   Row (
       modifier = modifier,
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.spacedBy(2.dp)
   ) {
       Icon(
           imageVector = ImageVector.vectorResource(weatherIcon),
           contentDescription = "temperature",
           tint = Color.Unspecified
       )

       Text(
           text = content,
           style = PawKeyTheme.typography.subButtonActive,
           color = PawKeyTheme.colors.contents
       )
   }
}
