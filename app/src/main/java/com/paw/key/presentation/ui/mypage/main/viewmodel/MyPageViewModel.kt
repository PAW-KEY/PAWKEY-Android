package com.paw.key.presentation.ui.mypage.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.localstorage.LocalStorageRepository
import com.paw.key.domain.repository.user.UserRepository
import com.paw.key.presentation.ui.mypage.main.model.MyPageSideEffect
import com.paw.key.presentation.ui.mypage.main.model.MyPageState
import com.paw.key.presentation.ui.mypage.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localRepository: LocalStorageRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MyPageState())
    val state: StateFlow<MyPageState>
        get() = _state.asStateFlow() //get할때마다 업데이트

    private val _sideEffect = MutableSharedFlow<MyPageSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        getUserProfiles()
        getPetProfiles()
    }

    fun getUserProfiles() {
        viewModelScope.launch {
            userRepository.getUserProfiles()
                .onSuccess { user ->
                    _state.update { state ->
                        state.copy(ownerName = "${user.name}님")
                    }
                }.onFailure { e ->
                    _sideEffect.emit(MyPageSideEffect.ShowSnackBar("유저 프로필 불러오기 실패"))
                }
        }
    }

    fun getPetProfiles() {
        viewModelScope.launch {
            val petId = localRepository.getPetId()

            userRepository.getPetProfiles(petId)
                .onSuccess { result ->
                    _state.update { currentState ->
                        currentState.copy(
                            petInfo = result.toUiModel()
                        )
                    }
                }.onFailure {
                    _sideEffect.emit(MyPageSideEffect.ShowSnackBar("펫 프로필 불러오기 실패"))
                }
        }
    }

    fun removeUser() {
        viewModelScope.launch {
            //val provider = localRepository.getProvider()

            userRepository.deleteUser("KAKAO")
                .onSuccess {
                    _sideEffect.emit(MyPageSideEffect.NavigateToLogin)
                }
                .onFailure {
                    Timber.e(it)
                    _sideEffect.emit(MyPageSideEffect.ShowSnackBar("유저 삭제 실패"))
                }
        }
    }
}
