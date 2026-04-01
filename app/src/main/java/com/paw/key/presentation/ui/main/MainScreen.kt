package com.paw.key.presentation.ui.main

import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.paw.key.presentation.ui.main.component.MainBottomBar
import kotlinx.collections.immutable.toImmutableList

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    val toast = remember {
        Toast.makeText(context, "한 번 더 누르면 종료합니다.", Toast.LENGTH_SHORT)
    }

    var backPressedTime by remember { mutableLongStateOf(0L) }
    
    if (navigator.currentTab != null) {
        BackHandler {
            val currentTime = System.currentTimeMillis()
            if (currentTime - backPressedTime <= 2000L) {
                (context as? Activity)?.finishAffinity()
            } else {
                backPressedTime = currentTime
                toast.show()
            }
        }
    }

    MainScreenContent(
        navigator = navigator,
        snackBarHostState = snackBarHostState,
    )
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
private fun MainScreenContent(
    navigator: MainNavigator,
    snackBarHostState: SnackbarHostState,
) {
    Scaffold (
       bottomBar = {
           MainBottomBar(
               isVisible = navigator.showBottomBar(),
               tabs = MainTab.entries.toImmutableList(),
               currentTab = navigator.currentTab,
               onTabSelected = navigator::navigate,
           )
       },
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
    ) { innerPadding ->
        PawKeyNavHost(
            navigator = navigator,
            paddingValues = innerPadding,
            snackbarHostState = snackBarHostState
        )
    }
}

/*FootprintAnimationScreen(
            footprints = footprints.toPersistentList(),
            onAnimationFinished = { finishedFootprint ->
                removeFootprint(finishedFootprint)
            },
            modifier = Modifier
                .fillMaxSize()
        )*/