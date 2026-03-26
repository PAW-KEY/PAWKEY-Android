package com.paw.key.presentation.ui.mypage.route.petinfo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.localstorage.LocalStorageRepository
import com.paw.key.domain.repository.user.UserRepository
import com.paw.key.presentation.ui.mypage.model.toUiModel
import com.paw.key.presentation.ui.mypage.route.petinfo.model.PetProfileSideEffect
import com.paw.key.presentation.ui.mypage.route.petinfo.model.PetProfileState
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
class PetProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localRepository: LocalStorageRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PetProfileState())
    val state: StateFlow<PetProfileState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<PetProfileSideEffect>() // 필요 시 따로 Contract로 분리 가능
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        getPetProfiles()
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
                    _sideEffect.emit(PetProfileSideEffect.ShowSnackBar("펫 프로필 불러오기 실패"))
                }
        }
    }
}