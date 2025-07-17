package com.paw.key.presentation.ui.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.walklist.WalkListRepository
import com.paw.key.presentation.ui.mypage.state.SavedDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedDetailViewModel @Inject constructor(
    private val walkListDetailRepository: WalkListRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SavedDetailState())
    val state: StateFlow<SavedDetailState>
        get() = _state.asStateFlow()

    fun onClickImage(imageUrl: String) {
        _state.update {
            it.copy(
                clickImage = imageUrl
            )
        }
    }

    fun getWalkDetail(userId: Int, postId: Int) {
        viewModelScope.launch {
            walkListDetailRepository.getWalkListDetail(userId, postId)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            postTitle = result.title,
                            petName = result.authorInfo.petName,
                            createdAt = result.createdAt,
                            regionName = result.regionName,
                            categorySummary = result.categoryTags.categoryOptionSummary,
                            routeMapImageUrl = result.routeMapImageUrl,
                            walkingImageUrls = result.walkingImageUrls,
                            postContent = result.content,
                            petProfileImage = result.authorInfo.petProfileImage,
                        )
                    }
                }
                .onFailure {
                    Log.e("getWalkDetail", "getWalkDetail: ${it.message}")
                }
        }
    }

    fun getWalkTopPopular(userId: Int, routeId : Int) {
        viewModelScope.launch {
            walkListDetailRepository.getWalkTopPopular(userId, routeId)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            categoryTop3 = result.categoryTop3,
                            totalReviewCount = result.totalReviewCount
                        )
                    }
                    Log.d("getWalkTopPopular", "getWalkTopPopular: ${result.categoryTop3}")
                }
                .onFailure {
                    Log.e("getWalkTopPopular", "getWalkTopPopular: 실패ㅐㅐ")
                }
        }
    }
}