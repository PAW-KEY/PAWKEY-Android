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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.signup.component.SignUpHeader
import com.paw.key.presentation.ui.signup.component.SignUpUserSelectButton
import com.paw.key.presentation.ui.signup.state.SignUpContract
import com.paw.key.presentation.ui.signup.viewmodel.SignUpViewModel

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpLevelScreen() {
    PawKeyTheme {
        SignUpLevelScreen(
            onSignUpClick = {},
            selectedEnergyLevel = "",
            selectedSocialLevel = "",
            energyOptions = listOf("매우 차분해요", "조금 느긋해요"),
            socialOptions = listOf("잘 어울려요", "천천히 친해져요"),
            energyTitle = "에너지 레벨",
            socialTitle = "사회성 레벨",
            viewModel = hiltViewModel()
        )
    }
}

@Composable
fun SignUpLevelRoute(
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel? = null
) {
    val actualViewModel = viewModel ?: hiltViewModel<SignUpViewModel>()
    val state by actualViewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(actualViewModel.sideEffect) {
        actualViewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SignUpContract.SignUpSideEffect.NavigateNext -> {
                    navigateNext()
                }
                is SignUpContract.SignUpSideEffect.ShowSnackBar -> {
                    println("SnackBar: ${sideEffect.message}")
                }
                is SignUpContract.SignUpSideEffect.NavigateUp -> {

                }
            }
        }
    }

    val energyCategory = state.petTraitCategoryList.find { it.petTraitCategoryName == "에너지레벨" }
    val socialCategory = state.petTraitCategoryList.find { it.petTraitCategoryName == "사회성레벨" }

    val energyOptions = energyCategory?.petTraitCategoryOptions?.map { it.petTraitCategoryOptionText } ?: emptyList()
    val socialOptions = socialCategory?.petTraitCategoryOptions?.map { it.petTraitCategoryOptionText } ?: emptyList()

    val isButtonEnabled = state.selectedEnergyLevel.isNotEmpty() &&
            state.selectedSocialLevel.isNotEmpty()

    SignUpLevelScreen(
        enabled = isButtonEnabled,
        selectedEnergyLevel = state.selectedEnergyLevel,
        selectedSocialLevel = state.selectedSocialLevel,
        onSignUpClick = {
            println("SignUp button clicked")
            actualViewModel.debugSignUpState()
            actualViewModel.signUp(context)
        },
        energyOptions = energyOptions,
        socialOptions = socialOptions,
        energyTitle = "에너지 레벨",
        socialTitle = "사회성 레벨",
        modifier = modifier,
        viewModel = actualViewModel
    )
}

@Composable
fun SignUpLevelScreen(
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    selectedEnergyLevel: String = "",
    selectedSocialLevel: String = "",
    energyOptions: List<String>,
    socialOptions: List<String>,
    energyTitle: String = "에너지 레벨",
    socialTitle: String = "사회성 레벨",
    viewModel: SignUpViewModel
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
                title = energyTitle,
                options = energyOptions.chunked(2),
                selectedOption = selectedEnergyLevel,
                onOptionClick = { option ->
                    println("Energy level selected: $option")
                    viewModel.selectEnergyLevel(option)
                }
            )

            Spacer(modifier = Modifier.height(36.dp))

            LevelSection(
                title = socialTitle,
                options = socialOptions.chunked(2),
                selectedOption = selectedSocialLevel,
                onOptionClick = { option ->
                    println("Social level selected: $option")
                    viewModel.selectSocialLevel(option)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            PawkeyButton(
                text = stringResource(id = R.string.ic_onboarding_signup_button),
                enabled = enabled,
                onClick = {
                    println("Button clicked, enabled: $enabled")
                    onSignUpClick()
                }
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
                        onClick = {
                            println("Option clicked: $option")
                            onOptionClick(option)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}