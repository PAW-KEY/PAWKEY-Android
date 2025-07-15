package com.paw.key.presentation.ui.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.petprofile.PetProfileRepository
import com.paw.key.presentation.ui.mypage.state.MyPageSideEffect
import com.paw.key.presentation.ui.mypage.state.MyPageState
import com.paw.key.presentation.ui.mypage.state.PetProfileSideEffect
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
    private val petProfileRepository: PetProfileRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MyPageState())
    val state: StateFlow<MyPageState>
        get() = _state.asStateFlow() //get할때마다 업데이트

    private val _sideEffect = MutableSharedFlow<MyPageSideEffect>()
    val sideEffect: MutableSharedFlow<MyPageSideEffect> = _sideEffect

    fun updateWalkInfo(walkCount: String, totalDistance: String) {
        _state.value = _state.value.copy(
            walkCount = walkCount,
            totalDistance = totalDistance
        )
    }
    fun getPetProfiles(userId: Int) {
        viewModelScope.launch {
            petProfileRepository.getPetProfiles(userId)
                .onSuccess {
                    _state.value = _state.value.copy(
                        petName = it.first().name,
                        petAge = it.first().age.toString(),
                        petGender = it.first().gender,
                        petTags = it.first().traits.map { trait -> trait.category }
                    )
                }.onFailure {
                    _sideEffect.emit(MyPageSideEffect.ShowSnackBar("펫 프로필 불러오기 실패"))
                }
        }
    }
}