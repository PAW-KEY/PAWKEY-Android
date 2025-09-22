package com.paw.key.presentation.ui.signup

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable
import com.paw.key.presentation.ui.signup.component.FormField
import com.paw.key.presentation.ui.signup.component.SignUpTextField
import com.paw.key.presentation.ui.signup.component.SignUpUserSelectButton
import com.paw.key.presentation.ui.signup.state.SignUpContract
import com.paw.key.presentation.ui.signup.viewmodel.SignUpViewModel

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpDogScreen() {
    PawKeyTheme {
        // Preview content
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun SignUpDogRoute(
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel? = null
) {
    val actualViewModel = viewModel ?: hiltViewModel<SignUpViewModel>()
    SignUpDogScreen(
        progress = 0.75F,
        navigateNext = navigateNext,
        modifier = modifier,
        viewModel = actualViewModel
    )
}

private fun isAgeValid(ageKnown: SignUpContract.AgeKnown, dogAge: String): Boolean {
    return when (ageKnown) {
        SignUpContract.AgeKnown.KNOWN -> dogAge.isNotEmpty()
        SignUpContract.AgeKnown.UNKNOWN -> true
        SignUpContract.AgeKnown.NONE -> false
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SignUpDogScreen(
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier,
    progress: Float = 1F,
    viewModel: SignUpViewModel
) {
    val state by viewModel.state.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val dogNameFocusRequester = remember { FocusRequester() }
    val dogBreedFocusRequester = remember { FocusRequester() }
    val dogAgeFocusRequester = remember { FocusRequester() }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "progress_animation"
    )

    val imagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val pickSingleMediaLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            viewModel.onDogImageSelected(uri)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.onDogImageSelected(uri)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        }
    }

    val onClickImage = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pickSingleMediaLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            permissionLauncher.launch(imagePermission)
        }
    }

    val isFormValid = remember(state.dogName, state.dogGender, state.dogBreed, state.ageKnown, state.dogAge) {
        state.dogName.isNotEmpty() &&
                state.dogGender != SignUpContract.DogGender.UNKNOWN &&
                state.dogBreed.isNotEmpty() &&
                isAgeValid(state.ageKnown, state.dogAge)
    }

    val hideKeyboardAndClearFocus = {
        keyboardController?.hide()
        focusManager.clearFocus()
    }

    val proceedToNext = {
        if (isFormValid) {
            hideKeyboardAndClearFocus()
            navigateNext()
        }
    }

    val requestFocusSafely = { focusRequester: FocusRequester ->
        try {
            focusRequester.requestFocus()
        } catch (e: Exception) {

        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 헤더
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.ic_onboarding_signup),
                    color = PawKeyTheme.colors.black,
                    style = PawKeyTheme.typography.body16Sb,
                    modifier = Modifier.padding(top = 16.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = PawKeyTheme.colors.green500,
                    trackColor = PawKeyTheme.colors.gray100,
                    strokeCap = StrokeCap.Square,
                    gapSize = 0.dp,
                    drawStopIndicator = {}
                )
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Text(
                        text = stringResource(id = R.string.ic_onboarding_signup_subtitle_step3),
                        color = PawKeyTheme.colors.black,
                        style = PawKeyTheme.typography.head22Sb,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }

                item {
                    DogProfileImage(
                        dogImage = state.dogImage,
                        onClickImage = onClickImage
                    )
                }

                item {
                    DogNameField(
                        value = state.dogName,
                        onValueChange = viewModel::onDogNameChanged,
                        focusRequester = dogNameFocusRequester,
                        onNext = { requestFocusSafely(dogBreedFocusRequester) }
                    )
                }

                item {
                    Column {
                        DogGenderSection(
                            selectedGender = state.dogGender,
                            onGenderSelected = { gender ->
                                viewModel.selectDogGender(gender)
                                requestFocusSafely(dogBreedFocusRequester)
                            }
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
                        onValueChange = viewModel::onDogBreedChanged,
                        focusRequester = dogBreedFocusRequester,
                        onNext = { focusManager.clearFocus() }
                    )
                }

                item {
                    DogAgeSection(
                        ageKnown = state.ageKnown,
                        dogAge = state.dogAge,
                        onAgeKnownSelected = { ageKnown ->
                            viewModel.selectAgeKnown(ageKnown)
                            if (ageKnown == SignUpContract.AgeKnown.KNOWN) {
                                requestFocusSafely(dogAgeFocusRequester)
                            }
                        },
                        onDogAgeChanged = viewModel::onDogAgeChanged,
                        focusRequester = dogAgeFocusRequester,
                        onDone = proceedToNext
                    )
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }

        PawkeyButton(
            text = "다음으로",
            enabled = isFormValid,
            onClick = proceedToNext,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 46.dp)
        )
    }
}

@Composable
private fun DogProfileImage(
    dogImage: Uri?,
    onClickImage: () -> Unit
) {
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
                    .border(
                        width = 2.dp,
                        color = if (dogImage != null) PawKeyTheme.colors.green500 else PawKeyTheme.colors.white2,
                        shape = CircleShape
                    )
                    .background(PawKeyTheme.colors.white2)
                    .noRippleClickable { onClickImage() }
            ) {
                if (dogImage != null) {
                    AsyncImage(
                        model = dogImage,
                        contentDescription = "강아지 프로필 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(92.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_onboarding_img_plus),
                        contentDescription = "앨범",
                        tint = PawKeyTheme.colors.gray100
                    )
                }
            }
        }
    }
}

@Composable
private fun DogNameField(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    onNext: () -> Unit
) {
    FormField(
        label = stringResource(id = R.string.ic_onboarding_signup_dog_name),
        content = {
            SignUpTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = "강아지 이름을 입력해주세요",
                modifier = Modifier.focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = { onNext() }
                )
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
            style = PawKeyTheme.typography.body14Sb
        )
    }
}

@Composable
private fun DogBreedField(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    onNext: () -> Unit
) {
    FormField(
        label = stringResource(id = R.string.ic_onboarding_signup_dog_breed),
        content = {
            SignUpTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = "견종을 입력해주세요",
                modifier = Modifier.focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = { onNext() }
                )
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
    focusRequester: FocusRequester,
    onDone: () -> Unit
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
                        placeholder = "나이를 입력해주세요",
                        modifier = Modifier.focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { onDone() }
                        )
                    )
                }
            }
        }
    )
}