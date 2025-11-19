package com.paw.key.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.core.designsystem.component.LoadingScreen
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.UiState
import com.paw.key.presentation.ui.home.component.HomeRouteItem
import com.paw.key.presentation.ui.home.component.HomeStartWalkingRow
import com.paw.key.presentation.ui.home.component.HomeTopBar
import com.paw.key.presentation.ui.home.component.HomeWalkingInfoHolder
import com.paw.key.presentation.ui.home.model.WalkingRouteUiModel
import com.paw.key.presentation.ui.home.state.HomeState
import com.paw.key.presentation.ui.home.viewmodel.HomeViewModel
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        paddingValues = paddingValues,
        state = state
    )
}

@Composable
private fun HomeScreen(
    paddingValues: PaddingValues,
    state: HomeState,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HomeTopBar(
            location = "강남구 역삼동",
            onLocationClick = {}
        )

        Spacer(modifier = Modifier.height(24.dp))

        HomeWalkingInfoHolder(
            walkingInfo = state.walkingInfo
        )

        Spacer(modifier = Modifier.height(24.dp))

        HomeStartWalkingRow(
            petName = "보리",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "인기있는 산책 루트 추천",
            style = PawKeyTheme.typography.header3,
            color = PawKeyTheme.colors.contents,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (val uiState = state.walkingPopularData) {
            is UiState.Success -> {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(
                        items = uiState.data,
                        key = { _, item -> item.id }
                    ) { _, item ->
                        HomeRouteItem(
                            routeTitle = item.title,
                            routeDistance = item.distance,
                            routeTime = item.time,
                            routeDate = item.date,
                            routeImage = item.imageUri,
                            location = item.location
                        )
                    }
                }
            }

            is UiState.Loading -> {}
            is UiState.Empty -> Text("데이터가 없어요")
            is UiState.Failure -> Text("로드 실패")
        }
    }
}

@Preview
@Composable
private fun HomePreview() {
    PawKeyTheme {
        HomeScreen(
            paddingValues = PaddingValues(),
            state = HomeState(
                walkingPopularData = UiState.Success(WalkingRouteUiModel.Fake.toImmutableList())
            )
        )
    }
}
