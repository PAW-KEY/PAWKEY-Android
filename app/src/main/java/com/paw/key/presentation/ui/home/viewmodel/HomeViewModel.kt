package com.paw.key.presentation.ui.home.viewmodel

import DistrictDto
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.home.HomeRegionRepository
import com.paw.key.domain.repository.onboarding.OnboardingRegionRepository
import com.paw.key.presentation.ui.home.state.HomeContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val regionRepository: OnboardingRegionRepository,
    private val homeRegionRepository: HomeRegionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeContract.HomeState())
    val state: StateFlow<HomeContract.HomeState> = _state.asStateFlow()

    private val _regionList = MutableStateFlow<List<DistrictDto>>(emptyList())
    val regionList: StateFlow<List<DistrictDto>> = _regionList.asStateFlow()

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
                val result = regionRepository.getOnboardingRegion(userId = 2)
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

    fun patchRegion(
        userId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            val currentState = _state.value
            val selectedDongId = currentState.selectedDongId

            if (selectedDongId == 0) {
                onFailure("구와 동을 모두 선택해주세요.")
                return@launch
            }

            try {
                val result = homeRegionRepository.patchRegion(userId, selectedDongId)

                result.onSuccess { response ->
                    Log.d("HomeViewModel", "Region patch successful")
                    onSuccess()
                }.onFailure { exception ->
                    Log.e("HomeViewModel", "patchRegion error: ${exception.message}", exception)
                    if (exception.message?.contains("S000") == true) {
                        Log.d("HomeViewModel", "API 성공 응답이지만 JSON 파싱 오류, 성공으로 처리")
                        onSuccess()
                    } else {
                        onFailure(exception.message ?: "알 수 없는 오류 발생")
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "patchRegion Exception: ${e.message}", e)
                if (e.message?.contains("S000") == true) {
                    Log.d("HomeViewModel", "API 성공 응답이지만 JSON 파싱 오류, 성공으로 처리")
                    onSuccess()
                } else {
                    onFailure(e.message ?: "알 수 없는 오류 발생")
                }
            }
        }
    }
}