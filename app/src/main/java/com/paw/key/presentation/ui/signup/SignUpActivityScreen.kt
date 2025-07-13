package com.paw.key.presentation.ui.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.signup.component.FormField
import com.paw.key.presentation.ui.signup.component.LocationButton
import com.paw.key.presentation.ui.signup.component.LocationList
import com.paw.key.presentation.ui.signup.component.SignUpHeader
import com.paw.key.presentation.ui.signup.viewmodel.SignUpViewModel

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpActivityScreen() {
    PawKeyTheme {
        SignUpActivityScreen(
            step = 0.5F,
            navigateSignUpDog = {},
        )
    }
}

@Composable
fun SignUpActivityRoute(
    navugateSignUpDog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SignUpActivityScreen(
        step = 0.5F,
        navigateSignUpDog = navugateSignUpDog,
        modifier = modifier,
    )
}

@Composable
fun SignUpActivityScreen(
    step: Float,
    navigateSignUpDog: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        SignUpHeader(
            title = stringResource(R.string.ic_onboarding_signup),
            subtitle = stringResource(id = R.string.ic_onboarding_signup_subtitle_step2),
            progress = step,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(27.dp))

            FormField(
                label = stringResource(id = R.string.ic_onboarding_signup_main_location),
                content = {
                    LocationButton(
                        isEnable = state.isLocationMenuVisible,
                        location = "강남구",
                        onClick = { viewModel.toggleLocationMenu() }
                    )
                }
            )

            Spacer(modifier = Modifier.height(46.dp))

            FormField(
                label = stringResource(id = R.string.ic_onboarding_signup_sub_location),
                content = {
                    if (state.isLocationMenuVisible) {
                        LocationList(
                            selected = state.selectedLocation,
                            locations = listOf("개포동", "논현동", "뭔동", "동동동", "스꾸삐", "4글자유"),
                            onLocationSelected = { location ->
                                viewModel.selectLocation(location)
                            }
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            val isFormValid = state.selectedLocation.isNotEmpty()

            PawkeyButton(
                text = stringResource(id = R.string.ic_onboarding_signup_button),
                enabled = isFormValid,
                onClick = {
                    if (isFormValid) {
                        navigateSignUpDog()
                    }
                }
            )

            Spacer(modifier = Modifier.height(46.dp))
        }
    }

}
