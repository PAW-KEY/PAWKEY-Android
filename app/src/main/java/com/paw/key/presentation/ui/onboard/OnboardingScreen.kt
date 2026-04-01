package com.paw.key.presentation.ui.onboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.component.DokiButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable
import com.paw.key.presentation.ui.onboard.component.OnboardPager
import com.paw.key.presentation.ui.onboard.component.OnboardingPosting

@Preview(showBackground = true)
@Composable
private fun PreviewOnboardingScreen() {
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
            .background(color = PawKeyTheme.colors.white1),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo),
            contentDescription = stringResource(id = R.string.ic_onboarding_top_icon),
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.height(40.dp))

        OnboardPager(
            jobList = listOf(
                OnboardingPosting(
                    title = stringResource(id = R.string.ic_onboadring_pager_title1),
                    subtitle = stringResource(id = R.string.ic_onboarding_pager_subtext1),
                    backImg = R.drawable.doki_welcome,
                    isLarge = true
                ),
                OnboardingPosting(
                    title = stringResource(id = R.string.ic_onboadring_pager_title2),
                    subtitle = stringResource(id = R.string.ic_onboarding_pager_subtext2),
                    backImg = R.drawable.img_onboarding_2
                ),
                OnboardingPosting(
                    title = stringResource(id = R.string.ic_onboadring_pager_title3),
                    subtitle = stringResource(id = R.string.ic_onboarding_pager_subtext3),
                    backImg = R.drawable.img_onboarding_3
                ),
                OnboardingPosting(
                    title = stringResource(id = R.string.ic_onboadring_pager_title4),
                    subtitle = stringResource(id = R.string.ic_onboarding_pager_subtext4),
                    backImg = R.drawable.img_onboarding_4
                ),
            ),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(26.dp))

        Text (
            text = "건너뛰기",
            style = PawKeyTheme.typography.subButtonActive,
            color = PawKeyTheme.colors.defaultDark,
            modifier = Modifier
                .noRippleClickable(onClick = navigateSignUp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        DokiButton(
            text = "시작하기",
            enabled = true,
            onClick = navigateNext,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(34.dp))
    }
}

