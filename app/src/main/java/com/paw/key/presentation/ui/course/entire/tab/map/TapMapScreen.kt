package com.paw.key.presentation.ui.course.entire.tab.map

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.paw.key.R
import com.paw.key.core.designsystem.component.LoadingScreen
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.UiState
import com.paw.key.core.extension.noRippleClickable
import com.paw.key.presentation.ui.course.entire.tab.map.viewmodel.TapMapViewModel

@Composable
fun TapMapRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    isGranted: Boolean,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: TapMapViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val locationCallback = remember(viewModel) {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->

                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadInitialLocation()
    }

    when (state.initialLocationState) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Success -> {
            TapMapScreen(
                paddingValues = paddingValues,
                navigateUp = navigateUp,
                navigateNext = navigateNext,
                snackBarHostState = snackBarHostState,
                regionName = state.currentRegion ?: "영등포구 여의도동",
                onClickTracking = {
                    viewModel.updateState {
                        copy(
                            isTrackingEnabled = !this.isTrackingEnabled
                        )
                    }
                },
                modifier = modifier,
            )
        }

        UiState.Empty -> {}
        is UiState.Failure -> {}
    }
}

@Composable
fun TapMapScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    regionName: String,
    onClickTracking: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .padding(paddingValues),
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        }
    ) { pv ->
        Box(
            modifier = Modifier
                .padding(pv)
        ) {
            Text(
                text = regionName,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 18.dp, start = 18.dp)
                    .clip(RoundedCornerShape(36.dp))
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = PawKeyTheme.colors.gray50,
                        shape = RoundedCornerShape(36.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = PawKeyTheme.colors.green500,
                style = PawKeyTheme.typography.body14Sb,
                textAlign = TextAlign.Center,
            )

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 100.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = "산책 기록하기",
                    color = PawKeyTheme.colors.white1,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            color = PawKeyTheme.colors.green500
                        )
                        .padding(vertical = 16.dp, horizontal = 20.dp)
                        .noRippleClickable {
                            navigateNext()
                        },
                    textAlign = TextAlign.Center,
                    style = PawKeyTheme.typography.body16Sb
                )

                FloatingActionButton(
                    onClick = onClickTracking,
                    shape = CircleShape,
                    containerColor = PawKeyTheme.colors.white1,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(44.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_course_map_tap_location_on),
                        contentDescription = stringResource(R.string.course_tap_location_description),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}