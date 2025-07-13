package com.paw.key.presentation.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable
import com.paw.key.presentation.ui.signup.component.FormField
import com.paw.key.presentation.ui.signup.component.SignUpHeader
import com.paw.key.presentation.ui.signup.component.SignUpTextField
import com.paw.key.presentation.ui.signup.component.SignUpUserSelectButton
import com.paw.key.presentation.ui.signup.state.SignUpContract
import com.paw.key.presentation.ui.signup.viewmodel.SignUpViewModel

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpDogScreen() {
    PawKeyTheme {
        SignUpDogScreen(
            step = 0.75F,
            navigateNext = {},
        )
    }
}

@Composable
fun SignUpDogRoute(
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    SignUpDogScreen(
        step = 0.75F,
        navigateNext = navigateNext,
        modifier = modifier,
        viewModel = viewModel
    )
}

private fun isAgeValid(ageKnown: SignUpContract.AgeKnown, dogAge: String): Boolean {
    return when (ageKnown) {
        SignUpContract.AgeKnown.KNOWN -> dogAge.isNotEmpty()
        SignUpContract.AgeKnown.UNKNOWN -> true
        SignUpContract.AgeKnown.NONE -> false
    }
}

@Composable
fun SignUpDogScreen(
    step: Float,
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            SignUpHeader(
                title = stringResource(R.string.ic_onboarding_signup),
                subtitle = stringResource(id = R.string.ic_onboarding_signup_subtitle_step3),
                progress = step,
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item { DogProfileImage() }

                item {
                    DogNameField(
                        value = state.dogName,
                        onValueChange = viewModel::onDogNameChanged
                    )
                }

                item {
                    Column(
                    ) {
                        DogGenderSection(
                            selectedGender = state.dogGender,
                            onGenderSelected = viewModel::selectDogGender
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        NeuteringCheckbox(
                            isNeutered = state.isNeutered,
                            onToggle = viewModel::toggleNeutering
                        )
                    }
                }

                item {
                    DogBreedField(
                        value = state.dogBreed,
                        onValueChange = viewModel::onDogBreedChanged
                    )
                }

                item {
                    DogAgeSection(
                        ageKnown = state.ageKnown,
                        dogAge = state.dogAge,
                        onAgeKnownSelected = viewModel::selectAgeKnown,
                        onDogAgeChanged = viewModel::onDogAgeChanged
                    )
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }

        val isFormValid = state.dogName.isNotEmpty() &&
                state.dogGender != SignUpContract.DogGender.UNKNOWN &&
                state.dogBreed.isNotEmpty() &&
                isAgeValid(state.ageKnown, state.dogAge)

        PawkeyButton(
            text = "다음으로",
            enabled = isFormValid,
            onClick = navigateNext,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(34.dp))
    }
}

@Composable
private fun DogProfileImage() {
    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .border(1.dp, PawKeyTheme.colors.white2, CircleShape)
                    .background(PawKeyTheme.colors.white2)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_onboarding_img_plus),
                    contentDescription = "앨범",
                    tint = PawKeyTheme.colors.gray100
                )
            }
        }
    }
}

@Composable
private fun DogNameField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    FormField(
        label = stringResource(id = R.string.ic_onboarding_signup_dog_name),
        content = {
            SignUpTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = "강아지 이름을 입력해주세요"
            )
        }
    )
}

@Composable
private fun DogGenderSection(
    selectedGender: SignUpContract.DogGender,
    onGenderSelected: (SignUpContract.DogGender) -> Unit,
) {
    FormField(
        label = stringResource(id = R.string.ic_onboarding_signup_gender),
        content = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                SignUpUserSelectButton(
                    user = "남성",
                    isSelect = selectedGender == SignUpContract.DogGender.MALE,
                    onClick = { onGenderSelected(SignUpContract.DogGender.MALE) },
                    modifier = Modifier.weight(1f)
                )
                SignUpUserSelectButton(
                    user = "여성",
                    isSelect = selectedGender == SignUpContract.DogGender.FEMALE,
                    onClick = { onGenderSelected(SignUpContract.DogGender.FEMALE) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    )
}

@Composable
private fun NeuteringCheckbox(
    isNeutered: Boolean,
    onToggle: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable { onToggle() }
    ) {
        Icon(
            imageVector = if (isNeutered)
                ImageVector.vectorResource(R.drawable.ic_roundcheck_valid)
            else
                ImageVector.vectorResource(R.drawable.ic_roundcheck_invalid),
            contentDescription = "",
            tint = Color.Unspecified
        )
        Text(
            text = "중성화했어요",
            color = if (isNeutered)
                PawKeyTheme.colors.black
            else PawKeyTheme.colors.gray300,
            style = if (isNeutered)
                PawKeyTheme.typography.body14Sb
            else PawKeyTheme.typography.body14R
        )
    }
}

@Composable
private fun DogBreedField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    FormField(
        label = stringResource(id = R.string.ic_onboarding_signup_dog_breed),
        content = {
            SignUpTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = "견종을 입력해주세요"
            )
        }
    )
}

@Composable
private fun DogAgeSection(
    ageKnown: SignUpContract.AgeKnown,
    dogAge: String,
    onAgeKnownSelected: (SignUpContract.AgeKnown) -> Unit,
    onDogAgeChanged: (String) -> Unit,
) {
    FormField(
        label = stringResource(id = R.string.ic_onboarding_signup_age),
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SignUpUserSelectButton(
                        user = "나이를 알아요",
                        isSelect = ageKnown == SignUpContract.AgeKnown.KNOWN,
                        onClick = {
                            onAgeKnownSelected(SignUpContract.AgeKnown.KNOWN)
                            onDogAgeChanged("")
                        },
                        modifier = Modifier.weight(1f)
                    )

                    SignUpUserSelectButton(
                        user = "나이를 몰라요",
                        isSelect = ageKnown == SignUpContract.AgeKnown.UNKNOWN,
                        onClick = {
                            onAgeKnownSelected(SignUpContract.AgeKnown.UNKNOWN)
                            onDogAgeChanged("")
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                if (ageKnown == SignUpContract.AgeKnown.KNOWN) {
                    SignUpTextField(
                        value = dogAge,
                        onValueChange = onDogAgeChanged,
                        placeholder = "나이를 입력해주세요"
                    )
                }
            }
        }
    )
}