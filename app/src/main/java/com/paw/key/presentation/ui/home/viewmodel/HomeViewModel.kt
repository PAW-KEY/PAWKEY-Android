package com.paw.key.presentation.ui.home.viewmodel

import DistrictDto
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.domain.repository.home.RegionCurrentRepository
import com.paw.key.domain.repository.onboarding.OnboardingRegionRepository
import com.paw.key.presentation.ui.home.state.HomeContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val regionRepository: OnboardingRegionRepository,
    private val regionCurrentRepository: RegionCurrentRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeContract.HomeState())
    val state: StateFlow<HomeContract.HomeState> = _state.asStateFlow()

    private val _regionList = MutableStateFlow<List<DistrictDto>>(emptyList())
    val regionList: StateFlow<List<DistrictDto>> = _regionList.asStateFlow()

    val userId = PreferenceDataStore.getUserId()

    init {
        fetchRegion()
        regionCurrent()
    }

    fun toggleLocationMenu() {
        _state.update { currentState ->
            currentState.copy(
                isLocationMenuVisible = !currentState.isLocationMenuVisible
            )
        }
    }

    fun onGuSelected(guName: String, guId: Int) {
        viewModelScope.launch {
            try {
                // DataStore에 구 정보 저장
                PreferenceDataStore.saveGuInfo(guId, guName)

                // 상태 업데이트
                _state.update { currentState ->
                    currentState.copy(
                        selectedLocation = currentState.selectedLocation.copy(
                            selectedGu = guName,
                            selectedGuId = guId,
                            // 구를 새로 선택하면 기존 동 선택 초기화
                            selectedDong = "",
                            selectedDongId = 0
                        ),
                        // 구 선택 후 메뉴 닫기
                        isLocationMenuVisible = false
                    )
                }
                Log.d("HomeViewModel", "구 선택 완료: $guName (ID: $guId)")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "구 선택 저장 실패: ${e.message}")
            }
        }
    }

    fun onDongSelected(dongName: String, dongId: Int) {
        viewModelScope.launch {
            try {
                // 현재 구 정보와 함께 전체 위치 정보 저장
                val currentLocation = _state.value.selectedLocation
                PreferenceDataStore.saveLocationInfo(
                    guId = currentLocation.selectedGuId,
                    dongId = dongId,
                    guName = currentLocation.selectedGu,
                    dongName = dongName
                )

                // 상태 업데이트
                _state.update { currentState ->
                    currentState.copy(
                        selectedLocation = currentState.selectedLocation.copy(
                            selectedDong = dongName,
                            selectedDongId = dongId
                        )
                    )
                }
                Timber.d("HomeViewmodel", "동 선택 완료: $dongName (ID: $dongId)")
            } catch (e: Exception) {
                Timber.e("HomeViewmodel", "동 선택 저장 실패: ${e.message}")
            }

        }
    }

    private fun regionCurrent() {
        _state.update { it.copy(uiState = it.uiState.copy(isLoading = true)) }

        viewModelScope.launch {
            try {
                val result = regionCurrentRepository.regionCurrent(userId.first())
                result.onSuccess { response ->
                    Log.d("HomeViewModel", "RegionCurrent 성공: ${response.fullRegionName}")
                    PreferenceDataStore.saveActiveRegion(response.fullRegionName)
                    Log.d("HomeViewModel", "activeRegion 저장 완료: ${response.fullRegionName}")
                    _state.update { currentState ->
                        currentState.copy(
                            currentRegion = HomeContract.CurrentRegionInfo(
                                currentId = response.currentRegionId,
                                currentName = response.fullRegionName
                            ),
                            uiState = currentState.uiState.copy(
                                isLoading = false,
                                error = null
                            )
                        )
                    }
                }.onFailure { exception ->
                    Log.e("HomeViewModel", "RegionCurrent 실패: ${exception.message}")
                    _state.update { currentState ->
                        currentState.copy(
                            uiState = currentState.uiState.copy(
                                isLoading = false,
                                error = exception.message
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "RegionCurrent Exception: ${e.message}")
                _state.update { currentState ->
                    currentState.copy(
                        uiState = currentState.uiState.copy(
                            isLoading = false,
                            error = e.message
                        )
                    )
                }
            }
        }
    }

    private fun fetchRegion() {
        _state.update { it.copy(uiState = it.uiState.copy(isLoading = true)) }

        viewModelScope.launch {
            try {
                val result = regionRepository.getOnboardingRegion(userId.first())
                result.onSuccess { response ->
                    Log.d("HomeViewModel", "Region loaded: ${response.data.districtDtos.size}")
                    _regionList.value = response.data.districtDtos
                    _state.update {
                        it.copy(
                            uiState = it.uiState.copy(
                                isLoading = false,
                                error = null
                            )
                        )
                    }
                }.onFailure { exception ->
                    Log.e("HomeViewModel", "구/동 가져오기 실패: ${exception.message}")
                    _state.update {
                        it.copy(
                            uiState = it.uiState.copy(
                                isLoading = false,
                                error = exception.message
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "fetchRegion Exception: ${e.message}")
                _state.update {
                    it.copy(
                        uiState = it.uiState.copy(
                            isLoading = false,
                            error = e.message
                        )
                    )
                }
            }
        }
    }

    fun toggleLike(postId: Int, isLiked: Boolean) {
        viewModelScope.launch {
            _state.update { currentState ->
                val updatedPostsResult = currentState.postsResult?.let { postsResult ->
                    postsResult.copy(
                        posts = postsResult.posts.map { post ->
                            if (post.postId == postId) {
                                post.copy(isLike = isLiked)
                            } else {
                                post
                            }
                        }
                    )
                }

                currentState.copy(
                    postsResult = updatedPostsResult
                )
            }
        }
    }
}