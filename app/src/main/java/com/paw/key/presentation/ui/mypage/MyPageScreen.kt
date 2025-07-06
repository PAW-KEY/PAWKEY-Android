package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paw.key.R
import com.paw.key.presentation.ui.mypage.navigation.MyPage

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    MyPageScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        snackBarHostState = snackBarHostState,
        modifier = modifier,
    )
}

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.ic_mypage_description),
        modifier = modifier,
    )
}

@Preview
@Composable
private fun MyPageScreenPreview() {
    MyPageScreen(
        paddingValues = PaddingValues(),
        navigateUp = {},
        navigateNext = {},
        snackBarHostState = SnackbarHostState(),
    )
}