package com.paw.key.presentation.ui.mypage.petinfo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.petprofile.PetProfileRepository
import com.paw.key.presentation.ui.mypage.petinfo.model.PetProfileSideEffect
import com.paw.key.presentation.ui.mypage.petinfo.model.PetProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetProfileViewModel @Inject constructor(
    private val petProfileRepository: PetProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PetProfileState())
    val state: StateFlow<PetProfileState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<PetProfileSideEffect>() // 필요 시 따로 Contract로 분리 가능
    val sideEffect: MutableSharedFlow<PetProfileSideEffect> = _sideEffect

    fun getPetProfiles(userId: Int) {
        viewModelScope.launch {
            petProfileRepository.getPetProfiles(userId)
                .onSuccess { result ->
                    Log.d("PetProfileViewModel", "펫 프로필 불러오기 성공: ${result.size}마리")
                    _sideEffect.emit(PetProfileSideEffect.ShowSnackBar("펫 프로필 불러오기 성공 (${result.size}마리)"))
                    // 필요하면 내부 상태 저장
                    // _state.update { it.copy(profiles = result) }
                    _state.update { it ->
                        it.copy(
                            imageUrl = result.first().imageUrl,
                            name = result.first().name,
                            gender = result.first().gender,
                            breed = result.first().breed,
                            age = result.first().age.toString(),
                            energyLevel = result.first().traits.first().option,
                            socialLevel = result.first().traits.first().option,
                            isNeutered = result.first().isNeutered
                        )
                    }
                }
                .onFailure { e ->
                    Log.e("PetProfileViewModel", "펫 프로필 불러오기 실패", e)
                    _sideEffect.emit(PetProfileSideEffect.ShowSnackBar(e.message ?: "알 수 없는 오류"))
                }
        }
    }
}