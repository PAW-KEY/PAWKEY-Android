package com.paw.key.presentation.ui.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.walklist.WalkListRepository
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
    /*private val _state = MutableStateFlow(
        ArchivedDetailState(
            title = "한강 산책로",
            petName = "후추",
            date = "2025/06/02",
            location = "뚝섬유원지",
            distance = "4.5km",
            time = "1시간 30분 소요",
            option = listOf("풍경이 좋아요", "조용해요", "길이 깨끗해요"),
            imageUrl = "https://pawkey-server.com/image.jpg"
        )
    )*/

    private val _state = MutableStateFlow(ArchivedDetailState())
    val state: StateFlow<ArchivedDetailState>
        get() = _state.asStateFlow()

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

    fun getWalkTopPopular(userId: Int, postId: Int) {
        viewModelScope.launch {
            walkListDetailRepository.getWalkTopPopular(userId, postId)
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