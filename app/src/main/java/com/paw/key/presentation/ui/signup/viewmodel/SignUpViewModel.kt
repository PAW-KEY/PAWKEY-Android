package com.paw.key.presentation.ui.signup.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.signup.state.SignUpContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpContract.SignUpState())
    val state: StateFlow<SignUpContract.SignUpState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SignUpContract.SignUpSideEffect>()
    val sideEffect: MutableSharedFlow<SignUpContract.SignUpSideEffect>
        get() = _sideEffect

    fun selectGender(gender: SignUpContract.Gender) {
        _state.value = _state.value.copy(
            selectedGender = gender
        )
    }

    fun selectDistrict(districts: List<String>) {
        _state.value = _state.value.copy(
            selectedDistrict = districts
        )
    }

    fun selectLocation(location: String) {
        _state.value = _state.value.copy(
            selectedLocation = location
        )
    }

    fun onLocationChanged(location: String) {
        _state.value = _state.value.copy(
            selectedLocation = location
        )
    }

    fun toggleLocationMenu() {
        _state.value = _state.value.copy(
            isLocationMenuVisible = !_state.value.isLocationMenuVisible
        )
    }

    fun onNameChanged(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun onAgeChanged(age: String) {
        _state.value = _state.value.copy(age = age)
    }

    fun hideLocationMenu() {
        _state.value = _state.value.copy(
            isLocationMenuVisible = false
        )
    }

    fun onDogNameChanged(dogName: String) {
        _state.value = _state.value.copy(dogName = dogName)
    }

    fun selectDogGender(dogGender: SignUpContract.DogGender) {
        _state.value = _state.value.copy(dogGender = dogGender)
    }

    fun toggleNeutering() {
        _state.value = _state.value.copy(isNeutered = !_state.value.isNeutered)
    }

    fun onDogBreedChanged(dogBreed: String) {
        _state.value = _state.value.copy(dogBreed = dogBreed)
    }

    fun selectAgeKnown(ageKnown: SignUpContract.AgeKnown) {
        _state.value = _state.value.copy(ageKnown = ageKnown)
    }

    fun onDogAgeChanged(dogAge: String) {
        _state.value = _state.value.copy(dogAge = dogAge)
    }

    fun selectEnergyLevel(energyLevel: String) {
        _state.value = _state.value.copy(
            selectedEnergyLevel = energyLevel
        )
    }

    fun selectSocialLevel(socialLevel: String) {
        _state.value = _state.value.copy(
            selectedSocialLevel = socialLevel
        )
    }

    fun isNextButtonEnabled(): Boolean {
        return _state.value.selectedEnergyLevel.isNotEmpty() &&
                _state.value.selectedSocialLevel.isNotEmpty()
    }
}