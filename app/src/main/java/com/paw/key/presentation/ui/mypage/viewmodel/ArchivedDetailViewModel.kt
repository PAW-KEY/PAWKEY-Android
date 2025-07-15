package com.paw.key.presentation.ui.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
<<<<<<< HEAD
=======
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.walklist.WalkListRepository
>>>>>>> 1386bd85a13bbc6bb0cd43764a44b72cf0699838
import com.paw.key.presentation.ui.mypage.state.ArchivedDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchivedDetailViewModel @Inject constructor(
    private val walkListDetailRepository: WalkListRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ArchivedDetailState())
    val state: StateFlow<ArchivedDetailState>
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
                            walkingImageUrls = it.walkingImageUrls,
                            postContent = result.content
                        )
                    }
                    Log.d("getWalkDetail", "getWalkDetail: ${result.content}")
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