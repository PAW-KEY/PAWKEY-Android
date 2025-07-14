package com.paw.key.presentation.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import com.paw.key.presentation.ui.signup.component.SignUpHeader
import com.paw.key.presentation.ui.signup.component.SignUpUserSelectButton
import com.paw.key.presentation.ui.signup.viewmodel.SignUpViewModel

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpLevelScreen() {
    PawKeyTheme {
        SignUpLevelScreen(
            navigateNext = {},
            selectedEnergyLevel = "",
            selectedSocialLevel = "",
        )
    }
}

@Composable
fun SignUpLevelRoute(
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    SignUpLevelScreen(
        enabled = viewModel.isNextButtonEnabled(),
        selectedEnergyLevel = state.selectedEnergyLevel,
        selectedSocialLevel = state.selectedSocialLevel,
        navigateNext = navigateNext,
        modifier = modifier,
        viewModel = viewModel,
    )
}

@Composable
fun SignUpLevelScreen(
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    selectedEnergyLevel: String = "",
    selectedSocialLevel: String = "",
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SignUpHeader(
            title = stringResource(id = R.string.ic_onboarding_signup),
            subtitle = stringResource(id = R.string.ic_onboarding_signup_subtitle_step4)
        )

        Spacer(modifier = Modifier.height(42.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            LevelSection(
                title = stringResource(id = R.string.ic_onboarding_signup_energy_level),
                options = listOf(
                    listOf("매우 차분해요", "조금 느릿해요"),
                    listOf("활동적이에요", "아주 활발해요")
                ),
                selectedOption = selectedEnergyLevel,
                onOptionClick = { option ->
                    viewModel.selectEnergyLevel(option)
                }
            )

            Spacer(modifier = Modifier.height(36.dp))

            LevelSection(
                title = stringResource(id = R.string.ic_onboarding_signup_social_level),
                options = listOf(
                    listOf("잘 어울려요", "천천히 친해져요"),
                    listOf("낯을 가려요", "상관없어요")
                ),
                selectedOption = selectedSocialLevel,
                onOptionClick = { option ->
                    viewModel.selectSocialLevel(option)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            PawkeyButton(
                text = stringResource(id = R.string.ic_onboarding_signup_button),
                enabled = enabled,
                onClick = navigateNext
            )

            Spacer(modifier = Modifier.height(46.dp))
        }
    }
}

@Composable
private fun LevelSection(
    title: String,
    options: List<List<String>>,
    selectedOption: String,
    onOptionClick: (String) -> Unit,
) {
    Column {
        Text(
            text = title,
            style = PawKeyTheme.typography.body14Sb,
            color = PawKeyTheme.colors.black
        )

        Spacer(modifier = Modifier.height(12.dp))

        options.forEach { rowOptions ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                rowOptions.forEach { option ->
                    SignUpUserSelectButton(
                        user = option,
                        isSelect = selectedOption == option,
                        onClick = { onOptionClick(option) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}