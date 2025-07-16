package com.paw.key.presentation.ui.signup.viewmodel

import DistrictDto
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.data.dto.request.onboarding.OnboardingInfoRequest
import com.paw.key.data.dto.request.onboarding.PetInfoDto
import com.paw.key.data.dto.request.onboarding.PetTraitDto
import com.paw.key.domain.repository.onboarding.OnboardingInfoRepository
import com.paw.key.domain.repository.onboarding.OnboardingRegionRepository
import com.paw.key.domain.repository.onboarding.OnboardingRepository
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
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: OnboardingRepository,
    private val regionRepository: OnboardingRegionRepository,
    private val infoRepository: OnboardingInfoRepository,
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

    fun onGuSelected(guName: String, guId: Int) {
        _state.update { currentState ->
            currentState.copy(
                selectedGu = guName,
                selectedGuId = guId,
                // 구를 새로 선택하면 기존 동 선택 초기화
                selectedDong = "",
                selectedDongId = 0,
                // 구 선택 후 메뉴 닫기
                isLocationMenuVisible = false
            )
        }
    }

    fun onDongSelected(dongName: String, dongId: Int) {
        _state.update { it.copy(selectedDong = dongName, selectedDongId = dongId) }
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
        Log.d(
            "SignUpViewModel", """
            isSignUpEnabled check:
            - selectedEnergyLevel: '${state.selectedEnergyLevel}' (isEmpty: ${state.selectedEnergyLevel.isEmpty()})
            - selectedSocialLevel: '${state.selectedSocialLevel}' (isEmpty: ${state.selectedSocialLevel.isEmpty()})
            - name: '${state.name}' (isEmpty: ${state.name.isEmpty()})
            - age: '${state.age}' (isEmpty: ${state.age.isEmpty()})
            - selectedGu: '${state.selectedGu}' (isEmpty: ${state.selectedGu.isEmpty()})
            - selectedDong: '${state.selectedDong}' (isEmpty: ${state.selectedDong.isEmpty()})
            - dogName: '${state.dogName}' (isEmpty: ${state.dogName.isEmpty()})
            - dogBreed: '${state.dogBreed}' (isEmpty: ${state.dogBreed.isEmpty()})
            - dogImage: ${state.dogImage != null}
            - loginEmail: '$loginEmail' (isEmpty: ${loginEmail.isEmpty()})
            - loginPassword: '$loginPassword' (isEmpty: ${loginPassword.isEmpty()})
        """.trimIndent()
        )

        return state.selectedEnergyLevel.isNotEmpty() &&
                state.selectedSocialLevel.isNotEmpty() &&
                state.name.isNotEmpty() &&
                state.age.isNotEmpty() &&
                state.selectedGu.isNotEmpty() &&
                state.selectedDong.isNotEmpty() &&
                state.dogName.isNotEmpty() &&
                state.dogBreed.isNotEmpty() &&
                state.dogImage != null &&
                loginEmail.isNotEmpty() &&
                loginPassword.isNotEmpty()
    }

    fun setLoginCredentials(email: String, password: String) {
        loginEmail = email
        loginPassword = password
        Log.d(
            "SignUpViewModel",
            "Login credentials set - Email: '$email', Password length: ${password.length}"
        )
    }

    fun debugSignUpState() {
        val state = _state.value
        Log.d(
            "DEBUG_SIGNUP", """
        === SignUp State Debug ===
        Energy: '${state.selectedEnergyLevel}' (empty: ${state.selectedEnergyLevel.isEmpty()})
        Social: '${state.selectedSocialLevel}' (empty: ${state.selectedSocialLevel.isEmpty()})
        Name: '${state.name}' (empty: ${state.name.isEmpty()})
        Age: '${state.age}' (empty: ${state.age.isEmpty()})
        Gu: '${state.selectedGu}' (empty: ${state.selectedGu.isEmpty()})
        GuId: ${state.selectedGuId}
        Dong: '${state.selectedDong}' (empty: ${state.selectedDong.isEmpty()})
        DongId: ${state.selectedDongId}
        Dog Name: '${state.dogName}' (empty: ${state.dogName.isEmpty()})
        Dog Breed: '${state.dogBreed}' (empty: ${state.dogBreed.isEmpty()})
        Dog Image: ${state.dogImage != null}
        Login Email: '$loginEmail' (empty: ${loginEmail.isEmpty()})
        Login Password: '$loginPassword' (length: ${loginPassword.length})
        
        각 단계별 체크:
        - 성향 정보: ${state.selectedEnergyLevel.isNotEmpty() && state.selectedSocialLevel.isNotEmpty()}
        - 개인 정보: ${state.name.isNotEmpty() && state.age.isNotEmpty()}
        - 지역 정보: ${state.selectedGu.isNotEmpty() && state.selectedDong.isNotEmpty()}
        - 반려견 정보: ${state.dogName.isNotEmpty() && state.dogBreed.isNotEmpty() && state.dogImage != null}
        - 로그인 정보: ${loginEmail.isNotEmpty() && loginPassword.isNotEmpty()}
        
        최종 가능 여부: ${isSignUpEnabled()}
        =========================
    """.trimIndent()
        )
    }

    private fun fetchPetTraits() {
        viewModelScope.launch {
            try {
                val result = repository.getOnboardingPets(userId = 2)
                result.onSuccess { response ->
                    Log.d(
                        "SignUpViewModel",
                        "Pet traits loaded: ${response.data.petTraitCategoryList.size}"
                    )
                    _state.update { it.copy(petTraitCategoryList = response.data.petTraitCategoryList) }
                }.onFailure { error ->
                    Log.e("SignUpViewModel", "성향 정보 불러오기 실패: ${error.message}")
                }
            } catch (e: Exception) {
                Log.e("SignUpViewModel", "fetchPetTraits Exception: ${e.message}")
            }
        }
    }

    private fun getSelectedRegionId(): Int {
        val state = _state.value

        // selectedDongId가 있으면 그것을 우선 사용
        if (state.selectedDongId != 0) {
            Log.d("SignUpViewModel", "Using selectedDongId: ${state.selectedDongId}")
            return state.selectedDongId
        }

        // 없으면 기존 방식으로 찾기
        val selectedDong = _regionList.value
            .find { it.gu.name == state.selectedGu }
            ?.dongs
            ?.find { it.name == state.selectedDong }

        Log.d(
            "SignUpViewModel",
            "Selected region - Gu: ${state.selectedGu}, Dong: ${state.selectedDong}, DongId: ${selectedDong?.id}"
        )
        return selectedDong?.id ?: run {
            Log.e(
                "SignUpViewModel",
                "동 ID를 찾을 수 없습니다. Gu: ${state.selectedGu}, Dong: ${state.selectedDong}"
            )
            1
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

                // 에너지 레벨과 사회성 레벨 체크
                if (state.selectedEnergyLevel.isEmpty() || state.selectedSocialLevel.isEmpty()) {
                    Log.e("SignUpViewModel", "Energy or social level not selected")
                    _sideEffect.emit(SignUpContract.SignUpSideEffect.ShowSnackBar("에너지 레벨과 사회성 레벨을 모두 선택해주세요."))
                    return@launch
                }

                // 지역 정보 체크
                if (state.selectedGu.isEmpty() || state.selectedDong.isEmpty()) {
                    Log.e("SignUpViewModel", "Region not selected")
                    _sideEffect.emit(SignUpContract.SignUpSideEffect.ShowSnackBar("지역을 선택해주세요."))
                    return@launch
                }

                val regionId = getSelectedRegionId()
                if (regionId == 1) { // 기본값이면 실제로 선택되지 않았을 가능성
                    Log.e("SignUpViewModel", "Invalid region ID")
                    _sideEffect.emit(SignUpContract.SignUpSideEffect.ShowSnackBar("올바른 지역을 선택해주세요."))
                    return@launch
                }

                // 전체 회원가입 정보 체크
                if (!isSignUpEnabled()) {
                    Log.e("SignUpViewModel", "Required signup info missing")
                    _sideEffect.emit(SignUpContract.SignUpSideEffect.ShowSnackBar("회원가입 정보가 부족합니다."))
                    return@launch
                }

                // 나이 유효성 체크
                val userAge = state.age.toIntOrNull()
                if (userAge == null || userAge <= 0) {
                    Log.e("SignUpViewModel", "Invalid user age: ${state.age}")
                    _sideEffect.emit(SignUpContract.SignUpSideEffect.ShowSnackBar("올바른 나이를 입력해주세요."))
                    return@launch
                }

                // 강아지 나이 유효성 체크 (나이를 안다고 했을 때만)
                val dogAge = if (state.ageKnown == SignUpContract.AgeKnown.KNOWN) {
                    state.dogAge.toIntOrNull()?.takeIf { it >= 0 } ?: run {
                        Log.e("SignUpViewModel", "Invalid dog age: ${state.dogAge}")
                        _sideEffect.emit(SignUpContract.SignUpSideEffect.ShowSnackBar("올바른 강아지 나이를 입력해주세요."))
                        return@launch
                    }
                } else {
                    0
                }

                // trait ID 찾기
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
                    Log.e(
                        "SignUpViewModel",
                        "Trait ID not found - Energy: $energyTraitId, Social: $socialTraitId"
                    )
                    _sideEffect.emit(SignUpContract.SignUpSideEffect.ShowSnackBar("성향 정보를 다시 선택해주세요."))
                    return@launch
                }

                Log.d(
                    "SignUpViewModel",
                    "Energy trait ID: $energyTraitId, Social trait ID: $socialTraitId"
                )

                // 요청 객체 생성
                val request = OnboardingInfoRequest(
                    loginId = loginEmail,
                    password = loginPassword,
                    name = state.name,
                    gender = when (state.selectedGender) {
                        SignUpContract.Gender.MALE -> "M"
                        SignUpContract.Gender.FEMALE -> "F"
                        SignUpContract.Gender.UNKNOWN -> "M"
                    },
                    age = userAge,
                    regionId = getSelectedRegionId(),
                    pet = PetInfoDto(
                        name = state.dogName,
                        gender = when (state.dogGender) {
                            SignUpContract.DogGender.MALE -> "M"
                            SignUpContract.DogGender.FEMALE -> "F"
                            SignUpContract.DogGender.UNKNOWN -> "M" // 예외.. 를 위해 일단 달아놨슴다
                        },
                        age = dogAge,
                        isAgeKnown = state.ageKnown == SignUpContract.AgeKnown.KNOWN,
                        isNeutered = state.isNeutered,
                        breed = state.dogBreed,
                        petTraits = listOf(
                            PetTraitDto(
                                traitCategoryId = 1,
                                traitOptionId = energyTraitId
                            ),
                            PetTraitDto(
                                traitCategoryId = 2,
                                traitOptionId = socialTraitId
                            )
                        )
                    )
                )

                // 이미지 처리
                val imagePart = state.dogImage?.let { uri ->
                    try {
                        val inputStream = context.contentResolver.openInputStream(uri)
                        val tempFile = File.createTempFile("pet_profile", ".jpg", context.cacheDir)
                        inputStream?.use { input ->
                            tempFile.outputStream().use { output -> input.copyTo(output) }
                        }
                        val fileRequestBody = tempFile.asRequestBody("image/jpeg".toMediaType())
                        MultipartBody.Part.createFormData(
                            "pet_profile",
                            "pet_image.jpg",
                            fileRequestBody
                        )
                    } catch (e: Exception) {
                        Log.e("SignUpViewModel", "Image processing failed: ${e.message}")
                        null
                    }
                }

                if (imagePart == null) {
                    _sideEffect.emit(SignUpContract.SignUpSideEffect.ShowSnackBar("이미지를 선택해주세요."))
                    return@launch
                }

                // 서버 요청
                val result = infoRepository.postOnboardingInfo(
                    userId = 2,
                    image = imagePart,
                    onboardingInfoRequest = request
                )

                result.onSuccess { response ->
                    Log.d("SignUpViewModel", "SignUp successful - Response: $response")

                    // 회원가입 성공 시 사용자 정보를 DataStore에 저장
                    try {
                        PreferenceDataStore.saveUserInfo(
                            userId = response.data.userId,
                            userName = response.data.userName,
                            petId = response.data.petId,
                            petName = response.data.petName
                        )

                        // 로그인 정보도 함께 저장
                        PreferenceDataStore.saveLoginInfo(
                            email = loginEmail,
                            password = loginPassword
                        )

                        Log.d(
                            "SignUpViewModel",
                            "User info and login info saved to DataStore successfully"
                        )
                        Log.d(
                            "SignUpViewModel",
                            "Saved - UserId: ${response.data.userId}, UserName: ${response.data.userName}, PetId: ${response.data.petId}, PetName: ${response.data.petName}"
                        )

                    } catch (e: Exception) {
                        Log.e(
                            "SignUpViewModel",
                            "Failed to save user info to DataStore: ${e.message}"
                        )
                    }

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