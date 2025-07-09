package com.paw.key.presentation.ui.home

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.home.component.DaytimeCard
import com.paw.key.presentation.ui.home.component.HistoryCard
import com.paw.key.presentation.ui.home.component.RowCalendar
import com.paw.key.presentation.ui.home.component.SettingButton
import com.paw.key.presentation.ui.home.component.TopBar
import com.paw.key.presentation.ui.home.component.TrackingCard
import com.paw.key.presentation.ui.home.component.WeatherCard
import com.paw.key.presentation.ui.home.viewmodel.HomeViewModel

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    HomeScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        snackBarHostState = snackBarHostState,
        modifier = modifier,
        viewModel = viewModel
    )
}

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
) {
    val isLocationMenuVisible = viewModel.isLocationMenuVisible
    val view = LocalView.current
    val window = (view.context as? Activity)?.window

    SideEffect {
        window?.let {
            it.statusBarColor = Color.Black.toArgb()
            ViewCompat.getWindowInsetsController(view)?.let { controller ->
                controller.isAppearanceLightStatusBars = false
            }
        }
    }

    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(color = PawKeyTheme.colors.white1)
    ) {
        TopBar(location = "강남구 역삼동", onLocationClick = { viewModel.toggleLocationMenu() })

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(13.dp))

            WeatherCard(
                weathertitle = "35°",
                weathersub1 = "35°",
                weathersub2 = "21°",
                rating = "0",
                weatherIcon = R.drawable.ic_home_weather,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                DaytimeCard(
                    daytime = "05:06",
                    daystate = "일출",
                )

                Spacer(modifier = Modifier.width(12.dp))

                TrackingCard(onClick = {navigateNext()})
            }

            Spacer(modifier = Modifier.height(12.dp))

            RowCalendar(date = "7월")

            HistoryCard()
        }

    }
    if (isLocationMenuVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    viewModel.hideLocationMenu()
                }
        )

        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .padding(top = 97.dp, start = 250.dp),
        ) {
            SettingButton()
        }
    }
}

//@Preview
//@Composable
//private fun HomeScreenPreview() {
//    PawKeyTheme {
//        HomeScreen(
//            paddingValues = PaddingValues(),
//            navigateUp = {},
//            navigateNext = {},
//            snackBarHostState = SnackbarHostState(),
//
//            )
//
//    }
//
//}
// viewmodel 을 파라미터로 넣으면 못본다! 알고싶지않았음
