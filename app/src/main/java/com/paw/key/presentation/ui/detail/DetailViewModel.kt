package com.paw.key.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.paw.key.domain.repository.posts.PostsRepository
import com.paw.key.presentation.ui.detail.model.toUiModel
import com.paw.key.presentation.ui.detail.navigation.Detail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postsRepository: PostsRepository
) : ViewModel() {
    private val postId = savedStateHandle.toRoute<Detail>().postId

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    init {
        fetchDetail(postId)
        fetchTopReview()
    }

    fun fetchDetail(
        postId: Int
    ) {
        viewModelScope.launch {
            postsRepository.getPostsDetail(postId = postId)
                .onSuccess { result ->
                    _state.update { currentState ->
                        currentState.copy(
                            postDetail = result.toUiModel(),
                        )
                    }
                }
                .onFailure(Timber::e)
        }
    }

    fun fetchTopReview() {
        val routeId = _state.value.postDetail.routeDisplay.routeId
        viewModelScope.launch {
            postsRepository.getTop3Reviews(routeId = routeId)
                .onSuccess { result ->
                    _state.update { currentState ->
                        currentState.copy(
                            reviewDetail = result.toUiModel(),
                        )
                    }
                }
                .onFailure(Timber::e)
        }
    }
}