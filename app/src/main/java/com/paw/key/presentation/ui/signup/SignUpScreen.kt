package com.paw.key.presentation.ui.signup

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.paw.key.core.designsystem.component.DokiButton
import com.paw.key.core.designsystem.component.LoadingScreen
import com.paw.key.core.util.UiState
import com.paw.key.presentation.ui.signup.component.SignUpHeader
import com.paw.key.presentation.ui.signup.component.SignUpSubHeader
import com.paw.key.presentation.ui.signup.model.SignUpLocationInfo
import com.paw.key.presentation.ui.signup.model.SignUpMapInfo
import com.paw.key.presentation.ui.signup.model.SignUpPetInfo
import com.paw.key.presentation.ui.signup.model.SignUpUserInfo
import com.paw.key.presentation.ui.signup.state.Gender
import com.paw.key.presentation.ui.signup.state.SignUpSideEffect
import com.paw.key.presentation.ui.signup.state.SignUpStateType
import com.paw.key.presentation.ui.signup.viewmodel.SignUpViewModel

@Composable
fun SignUpRoute(
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    BackHandler(enabled = true) {
        viewModel.onBackPressed()
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { it ->
                when (it) {
                    is SignUpSideEffect.NavigateUp -> {
                        navigateUp()
                    }
                    is SignUpSideEffect.ShowSnackBar -> {

                    }
                    is SignUpSideEffect.NavigateNext -> {
                        viewModel.updateStep()
                    }
                    is SignUpSideEffect.NavigateHome -> {
                        navigateToHome()
                    }
            }
        }
    }

    SignUpScreen(
        currentStep = state.currentStep,
        type = state.signUpState,
        isNextEnabled = state.isNextEnabled,

        onNextClick = viewModel::onNextClick,
        onBackClick = viewModel::onBackPressed,

        userInfo = state.userInfo,
        onNickNameChanged = { viewModel.updateNickname(it) },
        onBirthDateChanged = { viewModel.updateBirthDate(it) },
        onGenderChanged = viewModel::updateGender,

        petInfo = state.petInfo,
        onPetNameChanged = { viewModel.updatePetName(it) },
        onPetBirthDateChanged = { viewModel.updatePetBirthDate(it) },
        onPetGenderChanged = viewModel::updatePetGender,
        onPetNeuteredChanged = viewModel::updatePetNeutered,
        onPetBreedChanged = { viewModel.updatePetBreed(it) },
        deniedPermission = viewModel::deniedPermission,
        onSelectedImage = viewModel::updatePetImage,

        locationInfo = state.locationInfo,
        onSelectedLocation = { gu, dong ->
            viewModel.onRegionSelected(gu, dong)
        },

        mapInfo = state.mapInfo,
    )
}

@Composable
fun SignUpScreen(
    userInfo : SignUpUserInfo,
    onNickNameChanged: (String) -> Unit,
    onBirthDateChanged: (String) -> Unit,
    onGenderChanged: (Gender) -> Unit,

    petInfo : SignUpPetInfo,
    deniedPermission: () -> Unit,
    onPetNameChanged : (String) -> Unit,
    onPetBirthDateChanged : (String) -> Unit,
    onPetGenderChanged : (Gender) -> Unit,
    onPetNeuteredChanged : (Boolean) -> Unit,
    onPetBreedChanged : (String) -> Unit,
    onSelectedImage: (Uri?) -> Unit,

    locationInfo : SignUpLocationInfo,
    onSelectedLocation : (gu : String, dong : String) -> Unit,

    mapInfo: SignUpMapInfo,

    isNextEnabled: Boolean,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    currentStep : Float,
    type: SignUpStateType,
) {
    val title = when(type) {
        SignUpStateType.USER_INFO -> {
            "내 정보 입력"
        }
        SignUpStateType.PET_INFO -> {
            "반려견 정보 입력"
        }
        SignUpStateType.LOCATION_INFO -> {
            "산책 지역 입력"
        }
        SignUpStateType.REGION_MANAGEMENT -> {
            "산책 지역 입력"
        }
    }

    val buttonText = when(type) {
        SignUpStateType.LOCATION_INFO -> {
            "완료"
        }
        else -> {
            "다음"
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        SignUpHeader(
            title = title,
            onBackClick = onBackClick,
            progress = currentStep
        )

        when (type) {
            SignUpStateType.REGION_MANAGEMENT -> {
                when (val state = mapInfo.uiState) {
                    is UiState.Loading -> {
                        LoadingScreen()
                    }

                    is UiState.Success -> {
                        SignUpMapInfoScreen(
                            type = mapInfo.drawType,
                            regionCoordinates = state.data,
                            entireCoordinates = mapInfo.entireCoordinates,
                            regionName = mapInfo.regionName,
                            onClickButton = onNextClick,
                            modifier = Modifier
                        )
                    }

                    else -> {}
                }
            }

            else -> {
                SignUpSubHeader()

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    when (type) {
                        SignUpStateType.USER_INFO -> {
                            SignUpUserInfoScreen(
                                nickName = userInfo.nickName,
                                birthDate = userInfo.birthDate,
                                gender = userInfo.gender,
                                onNickNameChanged = onNickNameChanged,
                                onBirthDateChanged = onBirthDateChanged,
                                onGenderChanged = onGenderChanged,
                                modifier = Modifier
                                    .padding(top = 40.dp)
                            )
                        }

                        SignUpStateType.PET_INFO -> {
                            SignUpPetInfoScreen(
                                petName = petInfo.petName,
                                petBirthDate = petInfo.petBirthDate,
                                petGender = petInfo.petGender,
                                petNeutered = petInfo.petNeutered,
                                petBreed = petInfo.petBreed,
                                selectedImageUri = petInfo.petImage,
                                onPetNameChanged = onPetNameChanged,
                                onPetBirthDateChanged = onPetBirthDateChanged,
                                onPetGenderChanged = onPetGenderChanged,
                                onPetNeuteredChanged = onPetNeuteredChanged,
                                onPetBreedChanged = onPetBreedChanged,
                                deniedPermission = deniedPermission,
                                onSelectedImage = {
                                    onSelectedImage(it)
                                },
                                modifier = Modifier
                            )
                        }

                        SignUpStateType.LOCATION_INFO -> {
                            SignUpLocationInfoScreen(
                                gu = locationInfo.selectedGu,
                                dong = locationInfo.selectedDong,
                                onSelectedLocation = { gu, dong ->
                                    onSelectedLocation(gu, dong)
                                },
                                modifier = Modifier
                            )
                        }

                        else -> {}
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    DokiButton(
                        text = buttonText,
                        onClick = onNextClick,
                        enabled = isNextEnabled,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 32.dp
                            )
                    )
                }
            }
        }
    }
}