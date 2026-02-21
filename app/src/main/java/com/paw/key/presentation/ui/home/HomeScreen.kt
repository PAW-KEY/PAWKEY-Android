package com.paw.key.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.routeitem.RouteItem
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.model.WalkingRouteUiModel
import com.paw.key.core.util.UiState
import com.paw.key.presentation.ui.home.component.HomeStartWalkingRow
import com.paw.key.presentation.ui.home.component.HomeTopBar
import com.paw.key.presentation.ui.home.component.HomeWalkingInfoHolder
import com.paw.key.presentation.ui.home.state.HomeState
import com.paw.key.presentation.ui.home.viewmodel.HomeViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    navigateToCourse: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        paddingValues = paddingValues,
        navigateToCourse = navigateToCourse,
        state = state
    )
}

@Composable
private fun HomeScreen(
    paddingValues: PaddingValues,
    navigateToCourse: () -> Unit,
    state: HomeState,
) {
    val itemWidth = (LocalConfiguration.current.screenWidthDp.dp - 40.dp) / 2.5f

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = PawKeyTheme.colors.background)
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .padding(paddingValues)
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
            onClick = navigateToCourse
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
                if (uiState.data.isEmpty()) {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_home_empty),
                            contentDescription = null
                        )

                        Text(
                            text = "곧 추천 루트가 채워질 예정이에요\n" +
                                    "추후에 인기루트를 확인하실 수 있어요!",
                            color = PawKeyTheme.colors.defaultDark,
                            style = PawKeyTheme.typography.subTitle,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(
                            items = uiState.data,
                            key = { _, item -> item.id }
                        ) { _, item ->
                            RouteItem(
                                routeTitle = item.title,
                                routeTime = item.time,
                                routeDate = item.date,
                                routeImage = item.imageUri,
                                location = item.location,
                                onClick = {},
                                onClickHeart = {},
                                modifier = Modifier.width(itemWidth)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(23.dp))

                Text(
                    text = "비슷한 이용자 루트 추천",
                    style = PawKeyTheme.typography.header3,
                    color = PawKeyTheme.colors.contents,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (state.walkingRecommendedData.isEmpty()) {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_home_empty),
                            contentDescription = null
                        )

                        Text(
                            text = "곧 추천 루트가 채워질 예정이에요\n" +
                                    "추후에 인기루트를 확인하실 수 있어요!",
                            color = PawKeyTheme.colors.defaultDark,
                            style = PawKeyTheme.typography.subTitle,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(
                            items = state.walkingRecommendedData,
                            key = { _, item -> item.id }
                        ) { _, item ->
                            RouteItem(
                                routeTitle = item.title,
                                routeTime = item.time,
                                routeDate = item.date,
                                routeImage = item.imageUri,
                                location = item.location,
                                onClick = {},
                                onClickHeart = {},
                                modifier = Modifier.width(itemWidth)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
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
            navigateToCourse = {},
            state = HomeState(
                walkingPopularData = UiState.Success(WalkingRouteUiModel.Fake.toImmutableList()),
                walkingRecommendedData = persistentListOf()
            )
        )
    }
}
