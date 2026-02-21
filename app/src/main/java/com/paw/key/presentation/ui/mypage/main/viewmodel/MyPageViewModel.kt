package com.paw.key.presentation.ui.mypage.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.localstorage.LocalStorageRepository
import com.paw.key.domain.repository.petprofile.PetProfileRepository
import com.paw.key.domain.repository.userprofile.UserProfileRepository
import com.paw.key.presentation.ui.mypage.main.model.MyPageSideEffect
import com.paw.key.presentation.ui.mypage.main.model.MyPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val petProfileRepository: PetProfileRepository,
    private val userProfileRepository: UserProfileRepository,
    private val localRepository: LocalStorageRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MyPageState())
    val state: StateFlow<MyPageState>
        get() = _state.asStateFlow() //get할때마다 업데이트

    private val _sideEffect = MutableSharedFlow<MyPageSideEffect>()
    val sideEffect: MutableSharedFlow<MyPageSideEffect> = _sideEffect

    init {
        viewModelScope.launch {
            val userId = localRepository.getUserId()
            getUserProfiles(userId)
            getPetProfiles(userId)
        }
    }

    fun getUserProfiles(userId: Int) {
        viewModelScope.launch {
            userProfileRepository.getUserProfiles(userId)
                .onSuccess { user ->
                    _state.update { state ->
                        state.copy(ownerName = "${user.name}님")
                    }
                }.onFailure { e ->
                    _sideEffect.emit(MyPageSideEffect.ShowSnackBar("유저 프로필 불러오기 실패"))
                }
        }
    }

    fun getPetProfiles(userId: Int) {
        viewModelScope.launch {
            petProfileRepository.getPetProfiles(userId)
                .onSuccess {
                    _state.value = _state.value.copy(
                        petName = it.first().name,
                        petAge = it.first().age.toString(),
                        petGender = it.first().gender,
                        petImageUrl = it.first().imageUrl,
                        petTags = it.first().traits.map { trait -> trait.option},
                        walkCount = it.first().walkCount
                    )
                }.onFailure {
                    _sideEffect.emit(MyPageSideEffect.ShowSnackBar("펫 프로필 불러오기 실패"))
                }
        }
    }
}