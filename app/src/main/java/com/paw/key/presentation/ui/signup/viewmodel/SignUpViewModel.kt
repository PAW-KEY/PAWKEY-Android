package com.paw.key.presentation.ui.signup.viewmodel

import DistrictDto
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.paw.key.data.dto.request.onboarding.OnboardingInfoRequest
import com.paw.key.data.dto.request.onboarding.PetInfoDto
import com.paw.key.data.dto.request.onboarding.PetTraitDto
import com.paw.key.domain.repository.OnboardingInfoRepository
import com.paw.key.domain.repository.OnboardingRegionRepository
import com.paw.key.domain.repository.OnboardingRepository
import com.paw.key.presentation.ui.signup.state.SignUpContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: OnboardingRepository,
    private val regionRepository: OnboardingRegionRepository,
    private val infoRepository: OnboardingInfoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpContract.SignUpState())
    val state: StateFlow<SignUpContract.SignUpState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SignUpContract.SignUpSideEffect>()
    val sideEffect: MutableSharedFlow<SignUpContract.SignUpSideEffect> = _sideEffect

    private val _regionList = MutableStateFlow<List<DistrictDto>>(emptyList())
    val regionList: StateFlow<List<DistrictDto>> = _regionList.asStateFlow()

    private var loginEmail: String = ""
    private var loginPassword: String = ""

    init {
        fetchPetTraits()
        fetchRegion()
    }

    fun selectGender(gender: SignUpContract.Gender) {
        _state.update { it.copy(selectedGender = gender) }
    }

    fun onDogImageSelected(uri: Uri) {
        _state.update { it.copy(dogImage = uri) }
    }

    fun selectDistrict(districts: List<String>) {
        _state.update { it.copy(selectedDistrict = districts) }
    }

    fun selectLocation(location: String) {
        _state.update { it.copy(selectedLocation = location) }
    }

    fun onLocationChanged(location: String) {
        _state.update { it.copy(selectedLocation = location) }
    }

    fun toggleLocationMenu() {
        _state.update { it.copy(isLocationMenuVisible = !_state.value.isLocationMenuVisible) }
    }

    fun onNameChanged(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun onAgeChanged(age: String) {
        _state.update { it.copy(age = age) }
    }

    fun hideLocationMenu() {
        _state.update { it.copy(isLocationMenuVisible = false) }
    }

    fun onGuSelected(gu: String) {
        _state.update { it.copy(selectedGu = gu, selectedDong = "") }
    }

    fun onDongSelected(dong: String) {
        _state.update { it.copy(selectedDong = dong) }
    }

    fun onDogNameChanged(dogName: String) {
        _state.update { it.copy(dogName = dogName) }
    }

    fun selectDogGender(dogGender: SignUpContract.DogGender) {
        _state.update { it.copy(dogGender = dogGender) }
    }

    fun toggleNeutering() {
        _state.update { it.copy(isNeutered = !_state.value.isNeutered) }
    }

    fun onDogBreedChanged(dogBreed: String) {
        _state.update { it.copy(dogBreed = dogBreed) }
    }

    fun selectAgeKnown(ageKnown: SignUpContract.AgeKnown) {
        _state.update { it.copy(ageKnown = ageKnown) }
    }

    fun onDogAgeChanged(dogAge: String) {
        _state.update { it.copy(dogAge = dogAge) }
    }

    fun selectEnergyLevel(energyLevel: String) {
        Log.d("SignUpViewModel", "Energy level selected: $energyLevel")
        _state.update { it.copy(selectedEnergyLevel = energyLevel) }
    }

    fun selectSocialLevel(socialLevel: String) {
        Log.d("SignUpViewModel", "Social level selected: $socialLevel")
        _state.update { it.copy(selectedSocialLevel = socialLevel) }
    }

    fun isNextButtonEnabled(): Boolean {
        return isSignUpEnabled()
    }
    fun isLevelScreenEnabled(): Boolean {
        val state = _state.value
        return state.selectedEnergyLevel.isNotEmpty() &&
                state.selectedSocialLevel.isNotEmpty()
    }

    fun isSignUpEnabled(): Boolean {
        val state = _state.value
        return state.selectedEnergyLevel.isNotEmpty() &&
                state.selectedSocialLevel.isNotEmpty() &&
                state.name.isNotEmpty() &&
                state.age.isNotEmpty() &&
                state.dogName.isNotEmpty() &&
                state.dogBreed.isNotEmpty() &&
                state.dogImage != null &&
                loginEmail.isNotEmpty() &&
                loginPassword.isNotEmpty()
    }

    fun setLoginCredentials(email: String, password: String) {
        loginEmail = email
        loginPassword = password
        Log.d("SignUpViewModel", "Login credentials set: $email")
    }

    private fun fetchPetTraits() {
        viewModelScope.launch {
            try {
                val result = repository.getOnboardingPets(userId = 2)
                result.onSuccess { response ->
                    Log.d("SignUpViewModel", "Pet traits loaded: ${response.data.petTraitCategoryList.size}")
                    _state.update { it.copy(petTraitCategoryList = response.data.petTraitCategoryList) }
                }.onFailure { error ->
                    Log.e("SignUpViewModel", "성향 정보 불러오기 실패: ${error.message}")
                }
            } catch (e: Exception) {
                Log.e("SignUpViewModel", "fetchPetTraits Exception: ${e.message}")
            }
        }
    }

    fun fetchRegion() {
        viewModelScope.launch {
            try {
                val result = regionRepository.getOnboardingRegion(userId = 2)
                result.onSuccess { response ->
                    Log.d("SignUpViewModel", "Region loaded: ${response.data.districtDtos.size}")
                    _regionList.value = response.data.districtDtos
                }.onFailure {
                    Log.e("SignUpViewModel", "구/동 가져오기 실패: ${it.message}")
                }
            } catch (e: Exception) {
                Log.e("SignUpViewModel", "fetchRegion Exception: ${e.message}")
            }
        }
    }

    fun signUp(context: Context) {
        viewModelScope.launch {
            try {
                Log.d("SignUpViewModel", "Starting signUp process...")
                val state = _state.value


                if (state.selectedEnergyLevel.isEmpty() || state.selectedSocialLevel.isEmpty()) {
                    Log.e("SignUpViewModel", "Energy or social level not selected")
                    _sideEffect.emit(SignUpContract.SignUpSideEffect.ShowSnackBar("에너지 레벨과 사회성 레벨을 모두 선택해주세요."))
                    return@launch
                }


                if (!isSignUpEnabled()) {
                    Log.e("SignUpViewModel", "Required signup info missing")
                    _sideEffect.emit(SignUpContract.SignUpSideEffect.ShowSnackBar("회원가입 정보가 부족합니다."))
                    return@launch
                }

                val energyTraitId = state.petTraitCategoryList
                    .find { it.petTraitCategoryName == "에너지레벨" }
                    ?.petTraitCategoryOptions
                    ?.find { it.petTraitCategoryOptionText == state.selectedEnergyLevel }
                    ?.petTraitCategoryOptionId

                val socialTraitId = state.petTraitCategoryList
                    .find { it.petTraitCategoryName == "사회성레벨" }
                    ?.petTraitCategoryOptions
                    ?.find { it.petTraitCategoryOptionText == state.selectedSocialLevel }
                    ?.petTraitCategoryOptionId

                if (energyTraitId == null || socialTraitId == null) {
                    Log.e("SignUpViewModel", "Trait ID not found - Energy: $energyTraitId, Social: $socialTraitId")
                    _sideEffect.emit(SignUpContract.SignUpSideEffect.ShowSnackBar("성향 정보를 다시 선택해주세요."))
                    return@launch
                }

                Log.d("SignUpViewModel", "Energy trait ID: $energyTraitId, Social trait ID: $socialTraitId")

                val request = OnboardingInfoRequest(
                    loginId = loginEmail,
                    password = loginPassword,
                    name = state.name,
                    gender = when (state.selectedGender) {
                        SignUpContract.Gender.MALE -> "MALE"
                        SignUpContract.Gender.FEMALE -> "FEMALE"
                        SignUpContract.Gender.UNKNOWN -> "UNKNOWN"
                    },
                    age = state.age.toIntOrNull() ?: 0,
                    regionId = 1,
                    pet = PetInfoDto(
                        name = state.dogName,
                        gender = when (state.dogGender) {
                            SignUpContract.DogGender.MALE -> "MALE"
                            SignUpContract.DogGender.FEMALE -> "FEMALE"
                            SignUpContract.DogGender.UNKNOWN -> "UNKNOWN"
                        },
                        age = state.dogAge.toIntOrNull() ?: 0,
                        isAgeKnown = state.ageKnown == SignUpContract.AgeKnown.KNOWN,
                        isNeutered = state.isNeutered,
                        breed = state.dogBreed,
                        petTraits = listOf(
                            PetTraitDto(traitCategoryId = 1, traitOptionId = energyTraitId),
                            PetTraitDto(traitCategoryId = 2, traitOptionId = socialTraitId)
                        )
                    )
                )

                val gson = Gson()
                val json = gson.toJson(request)
                val requestBody = json.toRequestBody("application/json".toMediaType())

                val imagePart = state.dogImage?.let { uri ->
                    try {
                        val inputStream = context.contentResolver.openInputStream(uri)
                        val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
                        inputStream?.use { input ->
                            tempFile.outputStream().use { output -> input.copyTo(output) }
                        }
                        val fileRequestBody = tempFile.asRequestBody("image/*".toMediaType())
                        MultipartBody.Part.createFormData("petProfile", tempFile.name, fileRequestBody)
                    } catch (e: Exception) {
                        Log.e("SignUpViewModel", "Image processing failed: ${e.message}")
                        null
                    }
                }

                if (imagePart == null) {
                    _sideEffect.emit(SignUpContract.SignUpSideEffect.ShowSnackBar("이미지를 선택해주세요."))
                    return@launch
                }

                Log.d("SignUpViewModel", "Sending request to server...")
                val result = infoRepository.postOnboardingInfo(
                    userId = 2,
                    requestBody = requestBody,
                    petImage = imagePart
                )

                result.onSuccess {
                    Log.d("SignUpViewModel", "SignUp successful")
                    _sideEffect.emit(SignUpContract.SignUpSideEffect.NavigateNext)
                }.onFailure { error ->
                    Log.e("SignUpViewModel", "SignUp failed: ${error.message}")
                    _sideEffect.emit(SignUpContract.SignUpSideEffect.ShowSnackBar("회원가입 실패: ${error.message}"))
                }
            } catch (e: Exception) {
                Log.e("SignUpViewModel", "SignUp Exception: ${e.message}")
                _sideEffect.emit(SignUpContract.SignUpSideEffect.ShowSnackBar("알 수 없는 오류가 발생했습니다."))
            }
        }
    }
}