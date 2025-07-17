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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.CourseCard
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable
import com.paw.key.presentation.ui.home.component.DaytimeCard
import com.paw.key.presentation.ui.home.component.HomeTopBar
import com.paw.key.presentation.ui.home.component.RowCalendar
import com.paw.key.presentation.ui.home.component.SettingButton
import com.paw.key.presentation.ui.home.component.TrackingCard
import com.paw.key.presentation.ui.home.component.WeatherCard
import com.paw.key.presentation.ui.home.viewmodel.HomeViewModel

@Preview
@Composable
private fun HomeScreenPreview() {
    PawKeyTheme {
        HomeScreen(
            paddingValues = PaddingValues(),
            onClickLike = { _, _ -> },
            navigateUp = {},
            navigateNext = {},
            navigateHomeLocationSetting = {},
            viewModel = hiltViewModel()
        )
    }
}

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    navigateHomeLocationSetting: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    HomeScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        navigateHomeLocationSetting = navigateHomeLocationSetting,
        modifier = modifier,
        viewModel = viewModel,
        onClickLike = { postId, isLiked ->
            viewModel.toggleLike(postId = postId, isLiked = isLiked)
        }
    )
}

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    navigateHomeLocationSetting: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onClickLike: (postId: Int, isLiked: Boolean) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val postsResult = state.postsResult
    val posts = postsResult?.posts

    SideEffect {
        window?.let {
            it.statusBarColor = Color.Black.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = false
        }
    }

    Column(
        modifier = modifier
            .padding(paddingValues)
            .background(PawKeyTheme.colors.white2)
            .fillMaxSize()
    ) {
        HomeTopBar(
            location = state.currentRegion.currentName,
            onLocationClick = { viewModel.toggleLocationMenu() }
        )

        // 로딩 상태 표시
        if (state.uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(PawKeyTheme.colors.white2),
            ) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    WeatherCard(
                        weathertitle = "35°",
                        weathersub1 = "35°",
                        weathersub2 = "21°",
                        rating = "0",
                        weatherIcon = R.drawable.ic_home_weather,
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DaytimeCard(daytime = "05:06", daystate = "일출")
                        Spacer(modifier = Modifier.weight(1F))
                        TrackingCard(onClick = { navigateNext() })
                    }
                }

                item { RowCalendar(date = "7월") }

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.ic_home_current_word),
                        color = PawKeyTheme.colors.black,
                        style = PawKeyTheme.typography.head18Sb,
                    )
                }

                // 에러 상태 표시
                state.uiState.error?.let { error ->
                    item {
                        Text(
                            text = "오류: $error",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                item {
                    CourseCard(
                        postId = -1,
                        title = "제목을 입력해주세요",
                        petName = "반려견 이름",
                        createdAt = "2025/07/19",
                        representativeImageUrl = "https://pawkey-bucket.s3.ap-northeast-2.amazonaws.com/route/69a9c758-csnapshot.jpg",
                        petProfileImageUrl = "",
                        descriptionTags = listOf("2.2km"),
                        isLiked = true,
                        onClickLike = { isLiked ->
                            //onClickLike(post.postId, isLiked)
                        },
                        onClickItem = { navigateNext() }
                    )
                }

                item { Spacer(modifier = Modifier.height(48.dp)) }
            }
        }
    }

    // 위치 메뉴 오버레이
    if (state.isLocationMenuVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { viewModel.toggleLocationMenu() }
        ) {
            SettingButton(
                modifier = Modifier
                    .padding(top = 43.dp, end = 16.dp)
                    .noRippleClickable {
                        viewModel.toggleLocationMenu()
                        navigateHomeLocationSetting()
                    }
                    .align(Alignment.TopEnd),
            )
        }
    }
}