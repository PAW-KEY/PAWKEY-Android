package com.paw.key.presentation.ui.community

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paw.key.R

@Composable
fun CommunityRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    CommunityScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        snackBarHostState = snackBarHostState,
        modifier = modifier,
    )
}

@Composable
fun CommunityScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.ic_community_description),
        modifier = modifier,
    )
}

@Preview
@Composable
private fun CommunityScreenPreview() {
    CommunityScreen(
        paddingValues = PaddingValues(),
        navigateUp = {},
        navigateNext = {},
        snackBarHostState = SnackbarHostState(),
    )
}