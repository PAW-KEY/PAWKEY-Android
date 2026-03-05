package com.paw.key.presentation.ui.signup.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.core.extension.toBirthDateFormat
import com.paw.key.core.util.UiState
import com.paw.key.core.util.flattenCoordinatesToLatLng
import com.paw.key.core.util.handleError
import com.paw.key.domain.entity.user.PetInfoEntity
import com.paw.key.domain.entity.user.UserInfoEntity
import com.paw.key.domain.repository.RegionRepository
import com.paw.key.domain.repository.localstorage.LocalStorageRepository
import com.paw.key.domain.repository.user.UserRepository
import com.paw.key.domain.usecase.user.PostCreateUserUseCase
import com.paw.key.presentation.ui.region.state.DrawType
import com.paw.key.presentation.ui.signup.model.DongModel
import com.paw.key.presentation.ui.signup.model.GuModel
import com.paw.key.presentation.ui.signup.model.PetInfoItemModel
import com.paw.key.presentation.ui.signup.model.toState
import com.paw.key.presentation.ui.signup.state.Gender
import com.paw.key.presentation.ui.signup.state.SignUpSideEffect
import com.paw.key.presentation.ui.signup.state.SignUpState
import com.paw.key.presentation.ui.signup.state.SignUpStateType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val regionRepository: RegionRepository,
    private val userRepository: UserRepository,
    private val localRepository: LocalStorageRepository,
    private val postCreateUserUseCase: PostCreateUserUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SignUpSideEffect>()
    val sideEffect: MutableSharedFlow<SignUpSideEffect> = _sideEffect

    fun deniedPermission() {
        viewModelScope.launch {
            _sideEffect.emit(SignUpSideEffect.ShowSnackBar("갤러리 접근 권한을 허용해주세요"))
        }
    }

    fun onBackPressed() {
        viewModelScope.launch {
            Timber.e("onBackPressed ${_state.value.currentStep}")
            when (state.value.signUpState) {
                SignUpStateType.USER_INFO -> {
                    _sideEffect.emit(SignUpSideEffect.NavigateUp)
                }

                SignUpStateType.PET_INFO -> {
                    updateState {
                        it.copy(
                            signUpState = SignUpStateType.USER_INFO,
                            currentStep = it.currentStep - 1f
                        )
                    }
                }

                SignUpStateType.LOCATION_INFO -> {
                    updateState {
                        it.copy(
                            signUpState = SignUpStateType.PET_INFO,
                            currentStep = it.currentStep - 1f
                        )
                    }
                }

                SignUpStateType.REGION_MANAGEMENT -> {
                    updateState { currentState ->
                        currentState.copy(
                            signUpState = SignUpStateType.LOCATION_INFO,
                            isRegionComplete = false,
                        )
                    }
                }
            }
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            if (!validateCurrentStep(_state.value)) {
                _state.update { currentState ->
                    currentState.copy(
                        isNextEnabled = false
                    )
                }
                _sideEffect.emit(SignUpSideEffect.ShowSnackBar("입력되지 않은 정보가 있습니다."))
                return@launch
            }

            when (_state.value.signUpState) {
                SignUpStateType.USER_INFO -> {
                    updateState {
                        it.copy(
                            signUpState = SignUpStateType.PET_INFO,
                        )
                    }
                    _sideEffect.emit(SignUpSideEffect.NavigateNext)
                }

                SignUpStateType.PET_INFO -> {
                    updateState {
                        it.copy(
                            signUpState = SignUpStateType.LOCATION_INFO,
                        )
                    }
                    _sideEffect.emit(SignUpSideEffect.NavigateNext)
                }

                SignUpStateType.LOCATION_INFO -> {
                    if (_state.value.isRegionComplete && _state.value.locationInfo.selectedGu.name.isNotBlank() && _state.value.locationInfo.selectedDong.name.isNotBlank()) {
                        postCreateUser()
                    } else {
                        // Todo: 좌표값이 없어서 우선 여기서 종료
                        /*updateState {
                            it.copy(
                                signUpState = SignUpStateType.REGION_MANAGEMENT,
                            )
                        }
                        _sideEffect.emit(SignUpSideEffect.NavigateNext)*/
                        postCreateUser()
                    }
                }

                SignUpStateType.REGION_MANAGEMENT -> {
                    updateState {
                        it.copy(
                            signUpState = SignUpStateType.LOCATION_INFO,
                            isRegionComplete = true
                        )
                    }
                }
            }
        }
    }

    private fun updateState(updateAction: (SignUpState) -> SignUpState) {
        _state.update { currentState ->
            val newState = updateAction(currentState)
            val isButtonEnabled = validateCurrentStep(newState)
            newState.copy(isNextEnabled = isButtonEnabled)
        }
    }

    fun updateStep() {
        viewModelScope.launch {
            _state.update { currentState ->
                if (_state.value.signUpState != SignUpStateType.REGION_MANAGEMENT) {
                    currentState.copy(
                        currentStep = _state.value.currentStep + 1f
                    )
                } else {
                    currentState.copy(
                        currentStep = _state.value.currentStep
                    )
                }
            }
        }
    }

    fun postCreateUser() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            postCreateUserUseCase(
                userInfoEntity = UserInfoEntity(
                    name = _state.value.userInfo.nickName,
                    birth = _state.value.userInfo.birthDate.toBirthDateFormat(),
                    gender = _state.value.userInfo.gender.value,
                    dongId = _state.value.locationInfo.selectedDong.id,
                    pet = PetInfoEntity(
                        name = _state.value.petInfo.petName,
                        birth = _state.value.petInfo.petBirthDate.toBirthDateFormat(),
                        gender = _state.value.petInfo.petGender.value,
                        isNeutered = _state.value.petInfo.petNeutered,
                        breedId = _state.value.petInfo.petBreed.id,
                        imageId = -1
                    )
                ),
                petImageUri = _state.value.petInfo.petImage?.toString()
            ).onSuccess {

                _state.update { it.copy(isLoading = false) }
                _sideEffect.emit(SignUpSideEffect.NavigateHome)
            }.onFailure(Timber::e)

            /*suspendRunCatching {
                val currentState = _state.value
                val petImageUri = currentState.petInfo.petImage

                val finalImageId: Int = if (petImageUri != null) {
                    val presignedResult = imageRepository.presignedImage(
                        presignedEntity = ImagePresignedEntity(
                            domain = ImageDomainType.PET_PROFILE,
                            contentType = "image/webp"
                        )
                    ).getOrThrow()

                    val registerImage = imageRepository.registerImage(
                        uriString = "${presignedResult.imageUrl}#${_state.value.petInfo.petImage}",
                        domainType = ImageDomainType.PET_PROFILE,
                    ).onFailure(Timber::e)

                    if (!registerImage.isSuccess) {
                        throw Exception("이미지 업로드에 실패했습니다.")
                    }

                    registerImage.getOrThrow().imageId
                } else {
                    -1
                }

                userRepository.createUser(
                    userInfoEntity = UserInfoEntity(
                        name = _state.value.userInfo.nickName,
                        birth = _state.value.userInfo.birthDate.toBirthDateFormat(),
                        gender = _state.value.userInfo.gender.value,
                        dongId = _state.value.locationInfo.selectedDong.id,
                        pet = PetInfoEntity(
                            name = _state.value.petInfo.petName,
                            birth = _state.value.petInfo.petBirthDate.toBirthDateFormat(),
                            gender = _state.value.petInfo.petGender.value,
                            isNeutered = _state.value.petInfo.petNeutered,
                            breedId = _state.value.petInfo.petBreed.id,
                            imageId = finalImageId
                        )
                    )
                ).onSuccess {
                    UserDataStore.saveUserId(context, it.userId)
                    UserDataStore.savePetId(context, it.petId)
                }
            }.onSuccess {
                Timber.e("postCreateUser success")
                _sideEffect.emit(SignUpSideEffect.NavigateHome)
            }.onFailure {
                Timber.e(it)
                _sideEffect.emit(SignUpSideEffect.ShowSnackBar(it.message ?: "알 수 없는 오류가 발생했습니다."))
            }
        }*/
        }
    }

    // userinfo
    fun updateNickname(nickname: String) {
        viewModelScope.launch {
            updateState { currentState ->
                currentState.copy(
                    userInfo = currentState.userInfo.copy(nickName = nickname)
                )
            }
        }
    }

    fun updateBirthDate(birthDate: String) {
        val digitsOnly = birthDate.filter { it.isDigit() }

        if (digitsOnly.length <= 8) {
            updateState { currentState ->
                currentState.copy(
                    userInfo = currentState.userInfo.copy(birthDate = digitsOnly)
                )
            }
        }
    }

    fun updateGender(gender: Gender) {
        viewModelScope.launch {
            updateState { currentState ->
                currentState.copy(
                    userInfo = currentState.userInfo.copy(gender = gender)
                )
            }
        }
    }

    // petinfo
    fun getPetInfo() {
        viewModelScope.launch {
            userRepository.getPetBreeds()
                .onSuccess {
                    updateState { currentState ->
                        currentState.copy(
                            petBreedList = it.breedList.map { it.toState() }.toImmutableList()
                        )
                    }
                }
                .onFailure(Timber::e)
        }
    }
    fun updatePetName(name: String) {
        viewModelScope.launch {
            updateState { currentState ->
                currentState.copy(
                    petInfo = currentState.petInfo.copy(petName = name)
                )
            }
        }
    }

    fun updatePetBirthDate(birthDate: String) {
        val digitsOnly = birthDate.filter { it.isDigit() }

        if (digitsOnly.length <= 8) {
            updateState { currentState ->
                currentState.copy(
                    petInfo = currentState.petInfo.copy(petBirthDate = digitsOnly)
                )
            }
        }
    }

    fun updatePetGender(gender: Gender) {
        viewModelScope.launch {
            updateState { currentState ->
                currentState.copy(
                    petInfo = currentState.petInfo.copy(petGender = gender)
                )
            }
        }
    }

    fun updatePetNeutered(neutered: Boolean) {
        viewModelScope.launch {
            updateState { currentState ->
                currentState.copy(
                    petInfo = currentState.petInfo.copy(petNeutered = neutered)
                )
            }
        }
    }

    fun updatePetBreed(breed: PetInfoItemModel) {
        viewModelScope.launch {
            updateState { currentState ->
                currentState.copy(
                    petInfo = currentState.petInfo.copy(petBreed = breed)
                )
            }
        }
    }

    fun updatePetImage(uri: Uri?) {
        viewModelScope.launch {
            updateState { currentState ->
                currentState.copy(
                    petInfo = currentState.petInfo.copy(petImage = uri)
                )
            }
        }
    }

    // location

    fun getRegions() {
        viewModelScope.launch {
            regionRepository.getRegionList()
                .onSuccess { result ->
                    updateState { currentState ->
                        currentState.copy(
                            locationInfo = currentState.locationInfo.copy(
                                regionList = result.map { it.toState() }.toImmutableList()
                            )
                        )
                    }
                }.onFailure(Timber::e)
        }
    }

    fun updateLocation(gu: GuModel, dong: DongModel) {
        viewModelScope.launch {
            updateState { currentState ->
                // 원래 선택한 구와 동이 같으면 완료로 아니라면 다시 지도뷰
                if (gu != currentState.locationInfo.selectedGu || dong != currentState.locationInfo.selectedDong) {
                    currentState.copy(
                        locationInfo = currentState.locationInfo.copy(
                            selectedGu = gu,
                            selectedDong = dong,
                        ),
                        isRegionComplete = false,
                        signUpState = SignUpStateType.LOCATION_INFO
                    )
                } else {
                    currentState.copy(
                        locationInfo = currentState.locationInfo.copy(
                            selectedGu = gu,
                            selectedDong = dong,
                        ),
                        signUpState = SignUpStateType.LOCATION_INFO
                    )
                }
            }
            getRegionGeometryList()
        }
    }

    fun getRegionGeometryList() {
        viewModelScope.launch {
            val dongId = _state.value.locationInfo.selectedDong.id
            getRegionGeometry(
                userId = localRepository.getUserId(),
                regionId = dongId,
            )
            onNextClick()
        }
    }

    fun getRegionGeometry(userId: Int, regionId: Int?) = viewModelScope.launch {
        regionRepository.getRegionGeometry(userId, regionId!!)
            .onSuccess { data ->
                val coordinates = data.geometry.coordinates
                val flattenedLatLng = flattenCoordinatesToLatLng(coordinates)

                if (flattenedLatLng.isEmpty() || flattenedLatLng.first().isEmpty()) {
                    _state.update { currentState ->
                        currentState.copy(
                            mapInfo = currentState.mapInfo.copy(
                                uiState = UiState.Failure("좌표 데이터가 올바르지 않습니다")
                            )
                        )
                    }
                    return@launch
                }

                val allPoints = flattenedLatLng.flatten().toPersistentList()

                if (flattenedLatLng.size == 1) {
                    // 폴리곤이 하나일 경우
                    _state.update { currentState ->
                        currentState.copy(
                            mapInfo = currentState.mapInfo.copy(
                                uiState = UiState.Success(flattenedLatLng),
                                entireCoordinates = allPoints,
                                drawType = DrawType.SINGLE,
                                regionName = data.regionName
                            )
                        )
                    }
                } else {
                    // 폴리곤이 여러 개일 경우
                    _state.update { currentState ->
                        currentState.copy(
                            mapInfo = currentState.mapInfo.copy(
                                uiState = UiState.Success(flattenedLatLng),
                                entireCoordinates = allPoints,
                                drawType = DrawType.MULTIPLE,
                                regionName = data.regionName
                            )
                        )
                    }
                }
            }
            .onFailure { throwable ->
                val errorMessage = handleError(throwable)
                _state.update { currentState ->
                    currentState.copy(
                        mapInfo = currentState.mapInfo.copy(
                            uiState = UiState.Failure(errorMessage)
                        )
                    )
                }
            }
    }

    private fun validateCurrentStep(state: SignUpState): Boolean {
        return when (state.signUpState) {
            SignUpStateType.USER_INFO -> {
                // UserInfo 확인
                state.userInfo.nickName.isNotBlank() && state.userInfo.nickName.length <= 8 &&
                        state.userInfo.birthDate.length == 8 && state.userInfo.birthDate.isValidDate() &&
                        state.userInfo.gender != Gender.UNKNOWN
            }

            SignUpStateType.PET_INFO -> {
                // PetInfo 확인
                state.petInfo.petName.isNotBlank() && state.petInfo.petName.length <= 8 &&
                        state.petInfo.petBirthDate.length == 8 && state.petInfo.petBirthDate.isValidDate() &&
                        state.petInfo.petGender != Gender.UNKNOWN &&
                        state.petInfo.petBreed.name.isNotBlank() &&
                        state.petInfo.petImage != null
            }

            SignUpStateType.LOCATION_INFO, SignUpStateType.REGION_MANAGEMENT -> {
                state.locationInfo.selectedGu.id != 0 &&
                        state.locationInfo.selectedGu.name.isNotBlank() &&
                        state.locationInfo.selectedDong.id != 0 &&
                        state.locationInfo.selectedDong.name.isNotBlank()
            }
        }
    }
}

private fun String.isValidDate(): Boolean {
    if (this.length != 8) return false
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        LocalDate.parse(this, formatter)
        true
    } catch (e: Exception) {
        false
    }
}
