package com.paw.key.presentation.ui.region

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PolygonOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.paw.key.core.designsystem.component.CustomSnackBar
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.UiState
import com.paw.key.presentation.ui.region.state.DrawType
import com.paw.key.presentation.ui.region.state.RegionSideEffect
import com.paw.key.presentation.ui.region.viewmodel.RegionViewModel
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun RegionalManagementRoute(
    paddingValues: PaddingValues,
    snackBarHostState: SnackbarHostState,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    regionId: Int,
    modifier: Modifier = Modifier,
    viewModel: RegionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraPositionState = rememberCameraPositionState()

    val bottomPanelHeightPx = remember {
        mutableIntStateOf(0)
    }

    val density = LocalDensity.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is RegionSideEffect.ShowSnackBar -> {
                        snackBarHostState.showSnackbar(
                            sideEffect.message
                        )
                        navigateNext()
                    }

                    RegionSideEffect.NavigateNext -> navigateNext()
                    RegionSideEffect.NavigateUp -> navigateUp()
                }
            }
    }

    when (val uiState = state.uiState) {
        is UiState.Success -> {
            LaunchedEffect(state.entireCoordinates.size) {
                if (state.entireCoordinates.size >= 2 && bottomPanelHeightPx.intValue > 0) {
                    val bounds = LatLngBounds.from(state.entireCoordinates)

                    val bottomPadding =
                        bottomPanelHeightPx.intValue + with(density) { 100.dp.roundToPx() }

                    cameraPositionState.move(
                        CameraUpdate.fitBounds(bounds, 100, 100, 100, bottomPadding)
                    )
                }
            }

            RegionalManagementScreen(
                paddingValues = paddingValues,
                snackBarHostState = snackBarHostState,
                cameraPositionState = cameraPositionState,
                type = state.drawType,
                regionCoordinates = uiState.data,
                selectedRegion = state.selectedRegion,
                preRegionName = state.preRegionName,
                regionName = state.regionName,
                onClickButton = {
                    viewModel.patchRegion()
                },
                onSizeChanged = {
                    bottomPanelHeightPx.intValue = it
                },
                modifier = Modifier
            )
        }

        is UiState.Loading -> {

        }

        else -> {}
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun RegionalManagementScreen(
    paddingValues: PaddingValues,
    snackBarHostState: SnackbarHostState,
    cameraPositionState: CameraPositionState,
    type: DrawType,
    regionCoordinates: ImmutableList<ImmutableList<LatLng>>,
    selectedRegion: String?,
    preRegionName: String?,
    regionName: String?,
    onClickButton: () -> Unit,
    onSizeChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.padding(
                    bottom = LocalConfiguration.current.screenHeightDp.dp * 0.4f
                )
            ) { data ->
                CustomSnackBar(
                    data = data
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NaverMap(
                modifier = Modifier
                    .align(Alignment.Center),
                cameraPositionState = cameraPositionState
            ) {
                when (type) {
                    DrawType.SINGLE -> {
                        val singlePolygonCoords = regionCoordinates.first()
                        if (singlePolygonCoords.isNotEmpty()) {
                            PolygonOverlay(
                                coords = singlePolygonCoords,
                                color = PawKeyTheme.colors.green500.copy(alpha = 0.3f),
                                outlineWidth = 1.dp,
                                outlineColor = PawKeyTheme.colors.green500
                            )
                        }
                    }

                    DrawType.MULTIPLE -> {
                        regionCoordinates.forEach {
                            PolygonOverlay(
                                coords = it,
                                color = PawKeyTheme.colors.green500.copy(alpha = 0.3f),
                                outlineWidth = 1.dp,
                                outlineColor = PawKeyTheme.colors.green500
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(
                        color = PawKeyTheme.colors.white1,
                        shape = RoundedCornerShape(
                            topStart = 16.dp, topEnd = 16.dp
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .onSizeChanged { size ->
                        onSizeChanged(size.height)
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "선택한 위치",
                        style = PawKeyTheme.typography.head20B2,
                        color = PawKeyTheme.colors.black
                    )

                    Text(
                        text = regionName ?: "강남구 역삼동",
                        style = PawKeyTheme.typography.head20B2,
                        color = PawKeyTheme.colors.green500
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (regionName == preRegionName) {
                    Text(
                        text = "기존에 산책하던 지역은\n" +
                                "기존 지역과 같은 동네에요.",
                        style = PawKeyTheme.typography.body14M,
                        color = PawKeyTheme.colors.gray500,
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    PawkeyButton(
                        text = "지역 변경하기",
                        onClick = {
                            onClickButton()
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = false,
                    )
                } else {
                    Text(
                        text = "기존에 산책하던 지역은 ${preRegionName}이에요.\n선택한 위치로 산책 지역을 변경하시겠어요?",
                        style = PawKeyTheme.typography.body14M,
                        color = PawKeyTheme.colors.gray500,
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    PawkeyButton(
                        text = "지역 변경하기",
                        onClick = {
                            onClickButton()
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = true,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RadiusTestPreview() {
    PawKeyTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
                    .padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f),
                )

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .background(
                            color = PawKeyTheme.colors.black,
                            shape = RoundedCornerShape(
                                topStart = 16.dp, topEnd = 16.dp
                            )
                        )
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "선택한 위치",
                            style = PawKeyTheme.typography.head20B2,
                            color = PawKeyTheme.colors.black
                        )

                        Text(
                            text = "강남구 역삼동",
                            style = PawKeyTheme.typography.head20B2,
                            color = PawKeyTheme.colors.green500
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "기존에 산책하던 지역은이에요.\n선택한 위치로 산책 지역을 변경하시겠어요?",
                        style = PawKeyTheme.typography.body14M,
                        color = PawKeyTheme.colors.gray500,
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    PawkeyButton(
                        text = "지역 변경하기",
                        onClick = {

                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = true,
                    )
                }
            }
        }
    }
}
