package com.paw.key.presentation.ui.home.viewmodel

import DistrictDto
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.domain.repository.home.HomeRegionRepository
import com.paw.key.domain.repository.onboarding.OnboardingRegionRepository
import com.paw.key.presentation.ui.home.state.HomeContract
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val regionRepository: OnboardingRegionRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeContract.HomeState())
    val state: StateFlow<HomeContract.HomeState> = _state.asStateFlow()

    private val _regionList = MutableStateFlow<List<DistrictDto>>(emptyList())
    val regionList: StateFlow<List<DistrictDto>> = _regionList.asStateFlow()

    val userId = PreferenceDataStore.getUserId()

    init {
        fetchRegion()
    }

    fun toggleLocationMenu() {
        _state.update { currentState ->
            currentState.copy(
                isLocationMenuVisible = !currentState.isLocationMenuVisible
            )
        }
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
        _state.update { currentState ->
            currentState.copy(
                selectedDong = dongName,
                selectedDongId = dongId
            )
        }
    }

    private fun fetchRegion() {
        viewModelScope.launch {
            try {
                val result = regionRepository.getOnboardingRegion(userId.first())
                result.onSuccess { response ->
                    Log.d("HomeViewModel", "Region loaded: ${response.data.districtDtos.size}")
                    _regionList.value = response.data.districtDtos
                }.onFailure { exception ->
                    Log.e("HomeViewModel", "구/동 가져오기 실패: ${exception.message}")
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "fetchRegion Exception: ${e.message}")
            }
        }
    }
}