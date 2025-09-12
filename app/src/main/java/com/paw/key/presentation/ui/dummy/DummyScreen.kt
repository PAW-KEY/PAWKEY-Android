package com.paw.key.presentation.ui.dummy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.paw.key.presentation.ui.dummy.viewmodel.DummyViewModel
import kotlinx.collections.immutable.PersistentList
import com.paw.key.presentation.ui.dummy.state.DummyContract.DummySideEffect
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.UiState
import com.paw.key.core.extension.noRippleClickable
import com.paw.key.domain.model.entity.DummyUser
import com.paw.key.presentation.ui.dummy.component.DummyItem


@Composable
fun DummyRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    viewModel: DummyViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.getDummyList()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is DummySideEffect.ShowSnackBar -> snackBarHostState.showSnackbar(sideEffect.message)
                    DummySideEffect.NavigateNext -> navigateNext()
                    DummySideEffect.NavigateUp -> navigateUp()
                }
            }
    }

    DummyScreen(
        paddingValues = paddingValues,
        navigateUp = viewModel::navigateUp,
        navigateNext = viewModel::navigateNext,
        state = state.uiState
    )
}

@Composable
fun DummyScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    state: UiState<PersistentList<DummyUser>>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (state) {
            is UiState.Loading -> {
                item {
                    Text(
                        modifier = modifier
                            .noRippleClickable { navigateUp() },
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.loading_string),
                        fontSize = 30.sp
                    )
                }
            }

            is UiState.Empty -> {
                item {
                    Text(
                        modifier = modifier
                            .noRippleClickable { navigateUp() },
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_string),
                        fontSize = 30.sp
                    )
                }
            }

            is UiState.Failure -> {
                item {
                    Text(
                        modifier = modifier
                            .noRippleClickable { navigateUp() },
                        textAlign = TextAlign.Center,
                        text = state.message,
                    )
                }
            }

            is UiState.Success -> {
                items(state.data){
                    DummyItem(
                        id = it.id,
                        firstName = it.firstName,
                        lastName = it.lastName,
                        profileUrl = it.profile,
                        navigateNext = navigateNext
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DummyScreenPreview() {
    PawKeyTheme {
        DummyScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            navigateNext = {},
            state = UiState.Loading
        )
    }
}