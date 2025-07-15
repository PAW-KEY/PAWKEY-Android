package com.paw.key.presentation.ui.region

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapView
import com.paw.key.core.designsystem.component.CustomSnackBar
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.UiState
import com.paw.key.presentation.ui.region.component.regionalMapView
import com.paw.key.presentation.ui.region.state.RegionContract
import com.paw.key.presentation.ui.region.viewmodel.RegionViewModel

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
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Log.e("regregionId", regionId.toString())
        viewModel.getRegionGeometry(
            X_USER_ID = 2,
            regionId = regionId,
        )
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is RegionContract.RegionSideEffect.ShowSnackBar -> snackBarHostState.showSnackbar(
                        sideEffect.message
                    )

                    RegionContract.RegionSideEffect.NavigateNext -> navigateNext()
                    RegionContract.RegionSideEffect.NavigateUp -> navigateUp()
                }
            }
    }

    when (state.uiState) {
        is UiState.Success -> {
            val mapView = regionalMapView(
                lifeCycle = lifecycleOwner.lifecycle,
                context = context,
                currentUserLocation = state.centerLocation,
                polyPoints = (state.uiState as UiState.Success<List<List<LatLng>>>).data
            )

            RegionalManagementScreen(
                paddingValues = paddingValues,
                snackBarHostState = snackBarHostState,
                mapView = mapView,
                selectedRegion = state.selectedRegion,
                preRegionName = state.preRegionName,
                regionName = state.regionName,
                onClickButton = {
                    viewModel.onChangeRegion()
                    navigateNext()
                },
                modifier = modifier
            )
        }

        is UiState.Loading -> {

        }

        else -> {}
    }
}

@Composable
fun RegionalManagementScreen(
    paddingValues: PaddingValues,
    snackBarHostState: SnackbarHostState,
    mapView: MapView,
    selectedRegion : String?,
    preRegionName : String?,
    regionName : String?,
    onClickButton : () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .padding(paddingValues),
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = {
                    CustomSnackBar(
                        data = it,
                        modifier = Modifier
                            .padding(bottom = LocalConfiguration.current.screenHeightDp.dp * 0.4f + 16.dp)
                    )
                }
            )
        }
    ) { pv ->
        Box(
            modifier = Modifier
                .padding(pv)
        ) {
            AndroidView(
                factory = { mapView },
                modifier = Modifier
                    .align(Alignment.Center)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp * 0.4f)
                    .align(Alignment.BottomCenter)
                    .clip(
                        RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp
                        )
                    )
                    .background(
                        color = PawKeyTheme.colors.white1
                    )
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
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
                            text = selectedRegion ?: "강남구 역삼동",
                            style = PawKeyTheme.typography.head20B2,
                            color = PawKeyTheme.colors.green500
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (selectedRegion == preRegionName) {
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
                                //onClickButton()
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
}

