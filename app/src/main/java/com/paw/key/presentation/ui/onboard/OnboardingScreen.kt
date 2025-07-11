package com.paw.key.presentation.ui.onboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun OnboardingRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    navigateSignUp: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {


    OnboardingScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        navigateSignUp = navigateSignUp,
        snackBarHostState = snackBarHostState,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewOnboardingScreen() {
    PawKeyTheme {
        OnboardingScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            navigateNext = {},
            navigateSignUp = {},
            snackBarHostState = SnackbarHostState(),
            modifier = Modifier
        )
    }
}

@Composable
fun OnboardingScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    navigateSignUp: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 48.dp)
            .background(color = PawKeyTheme.colors.white1)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "안녕하세요\n우리 강아지를 위한 산책,\n${"PAWKEY"}와 함께해요! ",
                color = PawKeyTheme.colors.black,
                style = PawKeyTheme.typography.head22B
            )

            Spacer(modifier = Modifier.weight(1F))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PawkeyButton(
                    text = "로그인",
                    enabled = true,
                    onClick = { navigateSignUp() },
                )

                PawkeyButton(
                    text = "회원가입",
                    enabled = false,
                    onClick = { navigateSignUp() },
                )
            }
        }
    }
}