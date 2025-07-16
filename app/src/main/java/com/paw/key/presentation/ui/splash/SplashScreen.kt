package com.paw.key.presentation.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.splash.state.SplashContract
import com.paw.key.presentation.ui.splash.viewmodel.SplashViewModel

@Preview(showBackground = true)
@Composable
private fun PreviewSplashScreen() {
    PawKeyTheme {
        SplashScreen(
            paddingValues = PaddingValues(),
            modifier = Modifier
        )
    }
}

@Composable
fun SplashRoute(
    paddingValues: PaddingValues,
    navigateLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val effectFlow = viewModel.sideeffect

    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                is SplashContract.SplashSideEffect.NavigateToLogin -> navigateLogin()
            }
        }
    }

    SplashScreen(
        paddingValues = paddingValues,
        modifier = modifier
    )
}


@Composable
fun SplashScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = PawKeyTheme.colors.green500),
        contentAlignment = Alignment.Center
    ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_splash_logo),
                contentDescription = stringResource(id = R.string.ic_logo),
                tint = Color.Unspecified,
                modifier = Modifier.size(154.dp)
            )
    }
}

