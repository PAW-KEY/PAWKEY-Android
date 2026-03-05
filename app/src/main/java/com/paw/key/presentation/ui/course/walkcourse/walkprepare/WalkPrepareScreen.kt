package com.paw.key.presentation.ui.course.walkcourse.walkprepare

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.core.designsystem.component.DokiBorderButton
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.collectSideEffect
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.component.WalkPrepareBody
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.component.WalkPrepareWeatherInfo
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.state.WalkPrepareSideEffect
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.state.WalkPrepareState

@Composable
fun WalkPrepareRoute(
    paddingValues: PaddingValues,
    navigateWalkCourse: (routeId: String) -> Unit = {},
    viewModel: WalkPrepareViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectSideEffect {
        when(it) {
            is WalkPrepareSideEffect.NavigateToWalkCourse -> {
                navigateWalkCourse(it.routeId)
            }
            is WalkPrepareSideEffect.ShowToastMessage -> {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.updateWalkPreparation()
        }
    }

    WalkPrepareScreen(
        paddingValues = paddingValues,
        state = state,
        startWalkCourse = viewModel::startWalk,
        addWalkItem = viewModel::addWalkItem,
        deleteWalkItem = viewModel::deleteWalkItem,
        clearLastAddedItemId = viewModel::clearLastAddedItemId
    )
}

@Composable
private fun WalkPrepareScreen(
    paddingValues: PaddingValues,
    state: WalkPrepareState,
    startWalkCourse: () -> Unit = {},
    addWalkItem : () -> Unit = {},
    deleteWalkItem : (Int) -> Unit = {},
    clearLastAddedItemId: () -> Unit = {}
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = PawKeyTheme.colors.defaultButton
            )
            .padding(paddingValues)
    ) {
        TopBar(
            title = "산책",
            isBackVisible = false
        )

        Spacer(modifier = Modifier.height(20.dp))

        WalkPrepareWeatherInfo(
            walkPreparationMessage = state.walkPreparationMessage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        WalkPrepareBody(
            itemList = state.walkPrepareItemList,
            lastAddedItemId = state.lastAddedItemId,
            onFocusHandled = clearLastAddedItemId,
            modifier = Modifier
                .padding(horizontal = 16.dp),
            addWalkItem = addWalkItem,
            deleteWalkItem = deleteWalkItem
        )

        Spacer(modifier = Modifier.height(16.dp))

        DokiBorderButton(
            text = "산책 기록하기",
            enabled = true,
            onClick = startWalkCourse,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
    }
}

@Preview
@Composable
private fun WalkPrepareScreenPreview() {
    PawKeyTheme {
        WalkPrepareScreen(
            paddingValues = PaddingValues(),
            state = WalkPrepareState()
        )
    }
}
