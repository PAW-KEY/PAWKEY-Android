package com.paw.key.presentation.ui.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.core.model.toUiModel
import com.paw.key.domain.repository.posts.PostsRepository
import com.paw.key.presentation.ui.community.model.FilterCategoryUiModel
import com.paw.key.presentation.ui.community.model.SortedType
import com.paw.key.presentation.ui.community.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CommunityState())
    val state = _state.asStateFlow()

    init {
        fetchPostsFilter()
    }

    fun fetchPosts(
        cursor: String? = null
    ) {
        viewModelScope.launch {
            postsRepository.getPostsFilter(
                sortBy = _state.value.selectedSortedType.name.lowercase(),
                cursor = cursor,
                size = 10,
                postsFilter = _state.value.toFilterEntity()
            ).onSuccess { result ->
                _state.update { state ->
                    val newList = if (cursor == null) {
                        result.posts.map { it.toUiModel() }.toPersistentList()
                    } else {
                        (state.communityRouteList + result.posts.map { it.toUiModel() }).toPersistentList()
                    }
                    state.copy(
                        communityRouteList = newList,
                        nextCursor = result.nextCursor,
                        hasNext = result.hasNext
                    )
                }
            }.onFailure(Timber::e)
        }
    }


    private fun fetchPostsFilter() {
        viewModelScope.launch {
            postsRepository.getCategoriesFilter()
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                           filterUiModel = result.toUiModel()
                        )
                    }
                }
                .onFailure(Timber::e)
        }
    }


    fun onFilterClick(optionId: Int, category: FilterCategoryUiModel) {
        _state.update {
            it.copy(selectedOptionIds = it.getUpdatedOptionIds(optionId, category))
        }
    }

    fun onRefreshFilter() {
        _state.update { it.copy(selectedOptionIds = persistentMapOf()) }
    }

    fun onSortTypeChanged(sortedType: SortedType) {
        _state.update { it.copy(selectedSortedType = sortedType) }
        fetchPosts(cursor = null)
    }

    fun onLoadMore() {
        if (_state.value.hasNext) {
            fetchPosts(cursor = _state.value.nextCursor)
        }
    }
}
