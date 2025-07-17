package com.paw.key.presentation.ui.onboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.onboard.component.OnboardPager
import com.paw.key.presentation.ui.onboard.component.OnboardingPosting

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

    val jobList = listOf(
        OnboardingPosting(
            title = "우리의 강아지를 위한 산책,\nPAWKEY와 함께해요!",  // 원본 텍스트로 변경
            subtitle = "",
            backImg = R.drawable.onboard1
        ),
        OnboardingPosting(
            title = "새로운 산책의 시작",
            subtitle = "최적화된 산책 환경에서 더 즐겁고 의미있는 산책을\n우리 강아지와 함께 시작해보세요.",
            backImg = R.drawable.onboard2
        ),
        OnboardingPosting(
            title = "매일매일 새로운 루트로!\n우리 강아지와 나만의 산책.",
            subtitle = "",
            backImg = R.drawable.onboard3
        ),
        OnboardingPosting(
            title = "산책을 기록하고 공유하고\n수정해보세요.",
            subtitle = "",
            backImg = R.drawable.onboard4
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = PawKeyTheme.colors.green500)
    ) {
        OnboardPager(
            jobList = jobList
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 64.dp)
                .fillMaxWidth()
        ) {
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = true,
                onClick = { },
                isBackGround = true,
                isBorder = false
            )

            PawkeyButton(
                text = "기존 계정으로 로그인",
                enabled = true,
                isBorder = false,
                onClick = { navigateSignUp() },
            )
        }
    }
}

