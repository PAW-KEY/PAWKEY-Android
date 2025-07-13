package com.paw.key.presentation.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.presentation.animation.FootprintAnimationScreen
import com.paw.key.presentation.ui.main.component.MainBottomBar
import com.paw.key.presentation.ui.main.state.MainContract
import com.paw.key.presentation.ui.main.viewmodel.MainViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun MainRoute(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainScreen(
        footprints = state.footprint,
        addFootprint = viewModel::addFootprint,
        removeFootprint = viewModel::removeFootprint
    )
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun MainScreen(
    footprints: List<MainContract.Footprint>,
    addFootprint: (MainContract.Footprint) -> Unit,
    removeFootprint: (MainContract.Footprint) -> Unit,
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val snackBarHostState = remember { SnackbarHostState() }

    MainScreenContent(
        navigator = navigator,
        snackBarHostState = snackBarHostState,
        footprints = footprints,
        addFootprint = { footprint ->
            addFootprint(footprint)
        },
        removeFootprint = { footprint ->
            removeFootprint(footprint)
        }
    )
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
private fun MainScreenContent(
    navigator: MainNavigator,
    snackBarHostState: SnackbarHostState,
    footprints: List<MainContract.Footprint>,
    addFootprint: (MainContract.Footprint) -> Unit,
    removeFootprint: (MainContract.Footprint) -> Unit,
) {
    Box(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
            .systemBarsPadding()
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    addFootprint(MainContract.Footprint(position = offset))
                }
            }
    ) {
        PawKeyNavHost(
            navigator = navigator,
            paddingValues = PaddingValues(),
            snackbarHostState = snackBarHostState
        )

        MainBottomBar(
            isVisible = navigator.showBottomBar(),
            tabs = MainTab.entries.toImmutableList(),
            currentTab = navigator.currentTab,
            onTabSelected = navigator::navigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        FootprintAnimationScreen(
            footprints = footprints.toPersistentList(),
            onAnimationFinished = { finishedFootprint ->
                removeFootprint(finishedFootprint)
            },
            modifier = Modifier
                .fillMaxSize()
        )
    }
}