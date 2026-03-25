package com.paw.key.presentation.ui.mypage.userinfo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.localstorage.LocalStorageRepository
import com.paw.key.domain.repository.mypage.MypageRepository
import com.paw.key.domain.repository.userprofile.UserProfileRepository
import com.paw.key.presentation.ui.mypage.userinfo.model.UserProfileSideEffect
import com.paw.key.presentation.ui.mypage.userinfo.model.UserProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val mypageRepository: MypageRepository,
    private val localRepository: LocalStorageRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(UserProfileState())
    val state: StateFlow<UserProfileState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<UserProfileSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            getUserProfiles(localRepository.getUserId())
        }
    }

    fun onNameChange(value: String) = _state.update { it.copy(name = value) }
    fun onBirthChange(value: String) = _state.update { it.copy(birth = value) }
    fun onGenderChange(value: String) = _state.update { it.copy(gender = value) }

    private fun getUserProfiles(userId: Int) {
        viewModelScope.launch {
            userProfileRepository.getUserProfiles(userId)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            name         = result.name,
                            gender       = result.gender,
                            age          = result.age,
                            activeRegion = result.activeRegion,
                        )
                    }
                }
                .onFailure { e ->
                    _sideEffect.emit(UserProfileSideEffect.ShowSnackBar(e.message ?: "프로필을 불러오지 못했습니다"))
                }
        }
    }

    fun updateUser() {
        if (_state.value.isLoading) return
        val s = _state.value

        if (s.name.isBlank() || s.birth.isBlank() || s.gender.isBlank()) {
            viewModelScope.launch {
                _sideEffect.emit(UserProfileSideEffect.ShowSnackBar("모든 정보를 입력해주세요."))
            }
            return
        }

        val formattedBirth = s.birth.replace(".", "-").replace("/", "-")

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            mypageRepository.updateUser(
                name = s.name,
                birth = formattedBirth,
                gender = s.gender
            ).onSuccess {
                _sideEffect.emit(UserProfileSideEffect.ShowSnackBar("프로필이 수정되었습니다"))
                _sideEffect.emit(UserProfileSideEffect.NavigateUp)
                _state.update { it.copy(isLoading = false) }
            }.onFailure { e ->
                _sideEffect.emit(UserProfileSideEffect.ShowSnackBar(e.message ?: "수정에 실패했습니다"))
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}