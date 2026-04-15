package com.paw.key.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.paw.key.domain.repository.posts.PostsRepository
import com.paw.key.presentation.ui.detail.model.toUiModel
import com.paw.key.presentation.ui.detail.navigation.Detail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    private val routeId = savedStateHandle.toRoute<Detail>().routeId

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<DetailSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val routeIdDeferred = CompletableDeferred<Int>()

    init {
        fetchDetail(postId)
    }

    fun fetchDetail(
        postId: Int
    ) {
        viewModelScope.launch {
            postsRepository.getPostsDetail(postId = postId)
                .onSuccess { result ->
                    val uiModel = result.toUiModel()

                    _state.update { currentState ->
                        currentState.copy(
                            postDetail = uiModel,
                        )
                    }
                    routeIdDeferred.complete(uiModel.routeDisplay.routeId)
                    //fetchTopReview(routeId = uiModel.routeDisplay.routeId)
                }
                .onFailure(Timber::e)
        }
    }

    fun fetchTopReview(routeId: Int) {
        Timber.e("fetchTop $routeId")
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

    fun removePosts() {
        viewModelScope.launch {
            postsRepository.deletePosts(postId = postId)
                .onSuccess {
                    Timber.e("삭제 성공")
                    _sideEffect.emit(DetailSideEffect.navigateToCommunity)
                }
                .onFailure {
                    Timber.e(it)
                }
        }
    }
}