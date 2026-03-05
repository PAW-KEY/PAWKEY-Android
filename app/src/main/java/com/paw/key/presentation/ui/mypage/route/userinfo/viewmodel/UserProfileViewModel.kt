package com.paw.key.presentation.ui.mypage.route.userinfo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.localstorage.LocalStorageRepository
import com.paw.key.domain.repository.user.UserRepository
import com.paw.key.presentation.ui.mypage.route.userinfo.model.UserProfileSideEffect
import com.paw.key.presentation.ui.mypage.route.userinfo.model.UserProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(UserProfileState())
    val state: StateFlow<UserProfileState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<UserProfileSideEffect>()
    val sideEffect: MutableSharedFlow<UserProfileSideEffect> = _sideEffect

    init {
        getUserProfiles()
    }

    fun getUserProfiles() {
        viewModelScope.launch {
            userRepository.getUserProfiles()
                .onSuccess { result ->
                    _sideEffect.emit(UserProfileSideEffect.ShowSnackBar("유저 프로필 불러오기 성공"))

                    _state.update { state ->
                        state.copy(
                            name = result.name,
                            gender = result.gender,
                            birth = result.birth,
                            email = result.email
                        )
                    }
                }
                .onFailure { e ->
                    Log.e("UserProfileViewModel", "유저 프로필 불러오기 실패", e)
                    _sideEffect.emit(UserProfileSideEffect.ShowSnackBar(e.message ?: "알 수 없는 오류"))
                }
        }
    }
}
