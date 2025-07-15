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
import com.paw.key.presentation.ui.signup.component.LocationItem
import com.paw.key.presentation.ui.signup.component.LocationItemList
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
            viewModel = hiltViewModel()
        )
    }
}

@Composable
fun SignUpActivityRoute(
    navigateSignUpDog: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel? = null
) {

    SignUpActivityScreen(
        step = 0.5F,
        navigateSignUpDog = navigateSignUpDog,
        modifier = modifier,
        viewModel = viewModel ?: hiltViewModel()
    )
}

@Composable
fun SignUpActivityScreen(
    step: Float,
    navigateSignUpDog: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val regionList by viewModel.regionList.collectAsStateWithLifecycle()

    val selectedGu = state.selectedGu
    val selectedDong = state.selectedDong

    val guOptions = regionList.map { it.gu.name }

    val dongOptions = if (selectedGu.isNotEmpty()) {
        regionList.find { it.gu.name == selectedGu }?.dongs?.map {
            LocationItem(id = it.id, name = it.name)
        } ?: emptyList()
    } else {
        emptyList()
    }

    Column(
        modifier = modifier.fillMaxSize()
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

            // 지역구 섹션 - 처음부터 모든 구 칩들을 보여줌
            FormField(
                label = stringResource(id = R.string.ic_onboarding_signup_main_location),
                content = {
                    LocationList(
                        selected = selectedGu,
                        locations = guOptions,
                        onLocationSelected = { guName ->
                            val selectedGuItem = regionList.find { it.gu.name == guName }
                            selectedGuItem?.let {
                                viewModel.onGuSelected(it.gu.name, it.gu.id)
                            }
                        }
                    )
                }
            )

            Spacer(modifier = Modifier.height(46.dp))

            if (selectedGu.isNotEmpty()) {
                FormField(
                    label = stringResource(id = R.string.ic_onboarding_signup_sub_location),
                    content = {
                        LocationItemList(
                            selected = selectedDong,
                            locations = dongOptions,
                            onLocationSelected = { locationItem ->
                                viewModel.onDongSelected(locationItem.name, locationItem.id)
                            }
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val isFormValid = selectedGu.isNotEmpty() && selectedDong.isNotEmpty()

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