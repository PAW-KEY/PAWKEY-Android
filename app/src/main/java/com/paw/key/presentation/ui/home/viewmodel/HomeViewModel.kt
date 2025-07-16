package com.paw.key.presentation.ui.home.viewmodel

import DistrictDto
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.domain.repository.onboarding.OnboardingRegionRepository
import com.paw.key.presentation.ui.home.state.HomeContract
import dagger.hilt.android.lifecycle.HiltViewModel
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
    }

    fun onDongSelected(dongName: String, dongId: Int) {
        _state.update { currentState ->
            currentState.copy(
                selectedLocation = currentState.selectedLocation.copy(
                    selectedDong = dongName,
                    selectedDongId = dongId
                )
            )
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

//                val updatedCourseList = currentState.courseList.map { course ->
//                    if (course.postId == postId) {
//                        // ArchivedListEntity의 실제 프로퍼티명에 맞게 수정
//                        // isLiked 대신 isLike 또는 liked 등의 프로퍼티를 확인하고 사용
//                        course.copy(isLike = isLiked) // 또는 course.copy(liked = isLiked)
//                    } else {
//                        course
//                    }
//                }

                currentState.copy(
                    postsResult = updatedPostsResult,
//                    courseList = updatedCourseList
                )
            }
        }
    }

    fun clearError() {
        _state.update {
            it.copy(
                uiState = it.uiState.copy(error = null)
            )
        }
    }

    fun refreshPosts() {
        _state.update { it.copy(uiState = it.uiState.copy(isLoading = true)) }

        viewModelScope.launch {
            try {
                // 여기에 실제 포스트 데이터를 가져오는 로직 추가
                // val result = postsRepository.getPosts(...)

                _state.update {
                    it.copy(
                        uiState = it.uiState.copy(
                            isLoading = false,
                            error = null
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "refreshPosts Exception: ${e.message}")
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
}