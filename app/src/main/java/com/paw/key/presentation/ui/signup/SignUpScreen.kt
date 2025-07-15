package com.paw.key.presentation.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.signup.component.FormField
import com.paw.key.presentation.ui.signup.component.SignUpHeader
import com.paw.key.presentation.ui.signup.component.SignUpTextField
import com.paw.key.presentation.ui.signup.component.SignUpUserSelectButton
import com.paw.key.presentation.ui.signup.state.SignUpContract
import com.paw.key.presentation.ui.signup.viewmodel.SignUpViewModel

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpScreen() {
    PawKeyTheme {
        SignUpScreen(
            step = 0.25F,
            navigateSignUpActivity = {},
            viewModel = hiltViewModel<SignUpViewModel>()
        )
    }
}

@Composable
fun SignUpRoute(
    navigateSignUpActivity: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel? = null
    ) {
    val actualViewModel = viewModel ?: hiltViewModel<SignUpViewModel>()

    SignUpScreen(
        step = 0.25F,
        navigateSignUpActivity = navigateSignUpActivity,
        modifier = modifier,
        viewModel = actualViewModel
    )
}

@Composable
fun SignUpScreen(
    step: Float,
    navigateSignUpActivity: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        SignUpHeader(
            title = stringResource(R.string.ic_onboarding_signup),
            subtitle = stringResource(id = R.string.ic_onboarding_signup_subtitle_step1),
            progress = step,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(42.dp))

            FormField(
                label = stringResource(id = R.string.ic_onboarding_signup_name),
                content = {
                    SignUpTextField(
                        value = state.name,
                        onValueChange = viewModel::onNameChanged,
                        placeholder = "이름을 입력해주세요"
                    )
                }
            )

            Spacer(modifier = Modifier.height(33.dp))

            FormField(
                label = stringResource(id = R.string.ic_onboarding_signup_gender),
                content = {
                    GenderSelector(
                        selectedGender = state.selectedGender,
                        onGenderSelected = viewModel::selectGender
                    )
                }
            )

            Spacer(modifier = Modifier.height(33.dp))

            FormField(
                label = stringResource(id = R.string.ic_onboarding_signup_age),
                content = {
                    SignUpTextField(
                        value = state.age,
                        onValueChange = viewModel::onAgeChanged,
                        placeholder = "나이를 입력해주세요"
                    )
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            val isFormValid = state.name.isNotBlank() &&
                    state.age.isNotBlank() &&
                    state.selectedGender != SignUpContract.Gender.UNKNOWN

            PawkeyButton(
                text = stringResource(id = R.string.ic_onboarding_signup_button),
                enabled = isFormValid,
                onClick = {
                    if (isFormValid) {
                        navigateSignUpActivity()
                    }
                }
            )

            Spacer(modifier = Modifier.height(46.dp))
        }
    }

}

@Composable
private fun GenderSelector(
    selectedGender: SignUpContract.Gender,
    onGenderSelected: (SignUpContract.Gender) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        SignUpUserSelectButton(
            user = "남성",
            isSelect = selectedGender == SignUpContract.Gender.MALE,
            onClick = { onGenderSelected(SignUpContract.Gender.MALE) },
            modifier = Modifier.weight(1f)
        )

        SignUpUserSelectButton(
            user = "여성",
            isSelect = selectedGender == SignUpContract.Gender.FEMALE,
            onClick = { onGenderSelected(SignUpContract.Gender.FEMALE) },
            modifier = Modifier.weight(1f)
        )
    }
}