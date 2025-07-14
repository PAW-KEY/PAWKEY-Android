package com.paw.key.presentation.ui.course.entire

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.course.entire.component.EntireCourseTabRow
import com.paw.key.presentation.ui.course.entire.state.EntireCourseContract.CourseTab
import com.paw.key.presentation.ui.course.entire.tab.map.List.TapListRoute
import com.paw.key.presentation.ui.course.entire.tab.map.TapMapRoute
import com.paw.key.presentation.ui.course.entire.viewmodel.EntireCourseViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun EntireCourseRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    setOnVisibleRecord: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel : EntireCourseViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val pagerState = rememberPagerState(pageCount = { 2 })

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGranted = checkPermissionResults(permissions)

        if (!isGranted) {
            scope.launch {
                snackBarHostState.showSnackbar("위치 권한이 필요합니다.")
            }
        } else {
            viewModel.updateState {
                copy(
                    isLocationPermissionGranted = true,
                    isRecognitionPermissionGranted = true,
                    isLocationServiceEnabled = true
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        val isGranted = hasAllRequiredPermissions(context)

        if (isGranted && state.isLocationPermissionGranted && state.isRecognitionPermissionGranted) {
            viewModel.updateState {
                copy(
                    isLocationPermissionGranted = true,
                    isRecognitionPermissionGranted = true,
                    isLocationServiceEnabled = true
                )
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACTIVITY_RECOGNITION
                )
            )
        }
    }

    LaunchedEffect(state.selectedTabIndex) {
        if (pagerState.currentPage != state.selectedTabIndex) {
            pagerState.animateScrollToPage(state.selectedTabIndex)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                if (state.selectedTabIndex != page) {
                    viewModel.updateState {
                        copy(selectedTabIndex = page)
                    }
                }
            }
    }

    EntireCourseScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        snackBarHostState = snackBarHostState,
        currentPage = state.selectedTabIndex,
        onTabSelected = {
            viewModel.updateState {
                copy(selectedTabIndex = it)
            }
        },
        setOnVisibleRecord = {
            viewModel.updateState {
                copy(
                    isEnabled = !this.isEnabled
                )
            }
            setOnVisibleRecord(it)
        },
        isGranted = state.isLocationPermissionGranted,
        tabs = state.courseTabs,
        modifier = modifier,
    )
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun EntireCourseScreen(
    paddingValues: PaddingValues,
    snackBarHostState: SnackbarHostState,
    tabs : List<CourseTab>,
    isGranted : Boolean,
    currentPage : Int,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    setOnVisibleRecord : (Boolean) -> Unit,
    onTabSelected : (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        EntireCourseTabRow(
            selectedTabIndex = currentPage,
            onTabSelected = {
                onTabSelected(it)
            },
            tabs = tabs,
            modifier = Modifier
                .padding(top = 8.dp),
        )

        when (currentPage) {
            0 -> {
                TapMapRoute(
                    paddingValues = paddingValues,
                    navigateUp = {},
                    navigateNext = {
                        navigateNext()
                    },
                    isGranted = isGranted,
                    snackBarHostState = snackBarHostState,
                )
            }

            1 -> {
                TapListRoute()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
fun hasAllRequiredPermissions(context: Context): Boolean {
    val fine = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val coarse = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val activity = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACTIVITY_RECOGNITION
    ) == PackageManager.PERMISSION_GRANTED

    return fine || coarse || activity
}

@RequiresApi(Build.VERSION_CODES.Q)
fun checkPermissionResults(permissions: Map<String, Boolean>): Boolean {
    return permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true ||
            permissions[Manifest.permission.ACTIVITY_RECOGNITION] == true
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview
@Composable
private fun EntireCourseScreenPreview() {
    PawKeyTheme {
        EntireCourseScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            navigateNext = {},
            snackBarHostState = SnackbarHostState(),
            tabs = listOf(CourseTab.MapTab, CourseTab.ListTab),
            currentPage = 0,
            onTabSelected = {},
            isGranted = true,
            setOnVisibleRecord = {},
            modifier = Modifier,
        )
    }
}
