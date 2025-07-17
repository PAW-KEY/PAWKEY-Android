package com.paw.key.presentation.ui.course.entire.tab.map.List.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.data.dto.request.list.PostsListRequestDto
import com.paw.key.data.dto.request.list.TraitList
import com.paw.key.domain.repository.LikeRepository
import com.paw.key.domain.repository.filter.FilterOptionRepository
import com.paw.key.domain.repository.list.PostsListRepository
import com.paw.key.presentation.ui.course.entire.tab.map.List.state.TapListContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TapListViewModel @Inject constructor(
    private val filterOptionRepository: FilterOptionRepository,
    private val postsListRepository: PostsListRepository,
    private val likeRepository: LikeRepository
) : ViewModel() {
    private val _state = MutableStateFlow(TapListContract.TapListState())
    val state: StateFlow<TapListContract.TapListState> = _state.asStateFlow()

    private val userId = PreferenceDataStore.getUserId()

    fun loadInitialPosts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            println("POST 요청 시작 - 초기 데이터 로딩 (null 값들)")

            try {
                val request = PostsListRequestDto(
                    durationStart = null,
                    durationEnd = null,
                    selectedOptions = listOf(
                        TraitList(
                            categoryId = null,
                            optionIds = null
                        )
                    )
                )

                println("요청 데이터: $request")

                postsListRepository.postList(
                    userId = userId.first(),
                    request = request
                ).onSuccess { listEntity ->
                    val filteredPosts = listEntity.posts.filter { !it.isMine }
                    println("응답 성공 - 내 게시물 제외된 posts 개수: ${filteredPosts.size}")

                    _state.update {
                        it.copy(
                            isLoading = false,
                            postsResult = listEntity.copy(posts = filteredPosts)
                        )
                    }
                }.onFailure { exception ->
                    println("응답 실패: ${exception.message}")
                    _state.update { it.copy(isLoading = false) }
                    exception.printStackTrace()
                }
            } catch (e: Exception) {
                println("예외 발생: ${e.message}")
                _state.update { it.copy(isLoading = false) }
                e.printStackTrace()
            }
        }
    }

    fun loadFilterOptions() {
        viewModelScope.launch {
            filterOptionRepository.getFilterOptions(userId = userId.first())
                .onSuccess { filterEntity ->
                    _state.update {
                        it.copy(
                            filterOptions = filterEntity
                        )
                    }
                    Log.e("filterOptions", filterEntity.toString())
                }
                .onFailure { exception ->
                    exception.printStackTrace()
                }
        }
    }

    fun updateSortOption(option: String) {
        _state.update { it.copy(selectedSortOption = option) }
    }

    fun updateSortTime(option: String) {
        when (option) {
            "21분 이내" -> {
                _state.update {
                    it.copy(
                        selectedSortTimeStart = 0,
                        selectedSortTimeEnd = 21
                    )
                }
            }
            "21~40분" -> {
                _state.update {
                    it.copy(
                        selectedSortTimeStart = 21,
                        selectedSortTimeEnd = 40
                    )
                }
            }
            "41~60분" -> {
                _state.update {
                    it.copy(
                        selectedSortTimeStart = 41,
                        selectedSortTimeEnd = 60
                    )
                }
            }
            "1시간 이상" -> {
                _state.update {
                    it.copy(
                        selectedSortTimeStart = 61,
                        selectedSortTimeEnd = null
                    )
                }
            }
            else -> {
                _state.update {
                    it.copy(
                        selectedSortTimeStart = null,
                        selectedSortTimeEnd = null
                    )
                }
            }
        }

        _state.update {
            it.copy(
                selectedSortTime = if (it.selectedSortTime == option) "" else option
            )
        }
    }

    fun toggleLike(postId: Int, isLiked: Boolean) {
        viewModelScope.launch {
            if (isLiked) {
                likeRepository.unlikeCourse(userId = userId.first(), postId = postId)
            } else {
                likeRepository.likeCourse(userId = userId.first(), postId = postId)
            }
        }
    }

    fun updateMood(option: String) {
        _state.update {
            it.copy(
                selectedMood = if (it.selectedMood == option) "" else option
            )
        }
    }

    fun updateDogFriend(option: String) {
        _state.update {
            it.copy(
                selectedDogFriend = if (it.selectedDogFriend == option) "" else option
            )
        }
    }

    fun updateSafety(option: String) {
        _state.update { currentState ->
            val newSafety = if (currentState.selectedSafety.contains(option)) {
                currentState.selectedSafety.filter { it != option }
            } else {
                currentState.selectedSafety + option
            }
            currentState.copy(selectedSafety = newSafety)
        }
    }

    fun updateConvenience(option: String) {
        _state.update { currentState ->
            val newConvenience = if (currentState.selectedConvenience.contains(option)) {
                currentState.selectedConvenience.filter { it != option }
            } else {
                currentState.selectedConvenience + option
            }
            currentState.copy(selectedConvenience = newConvenience)
        }
    }

    fun updateEnvironment(option: String) {
        _state.update { currentState ->
            val newEnvironment = if (currentState.selectedEnvironment.contains(option)) {
                currentState.selectedEnvironment.filter { it != option }
            } else {
                currentState.selectedEnvironment + option
            }
            currentState.copy(selectedEnvironment = newEnvironment)
        }
    }

    fun toggleTimeExpanded() {
        _state.update { it.copy(isTimeExpanded = !it.isTimeExpanded) }
    }

    fun toggleMoodExpanded() {
        _state.update { it.copy(isMoodExpanded = !it.isMoodExpanded) }
    }

    fun toggleDogFriendExpanded() {
        _state.update { it.copy(isDogFriendExpanded = !it.isDogFriendExpanded) }
    }

    fun toggleSafetyExpanded() {
        _state.update { it.copy(isSafetyExpanded = !it.isSafetyExpanded) }
    }

    fun toggleConvenienceExpanded() {
        _state.update { it.copy(isConvenienceExpanded = !it.isConvenienceExpanded) }
    }

    fun toggleEnvironmentExpanded() {
        _state.update { it.copy(isEnvironmentExpanded = !it.isEnvironmentExpanded) }
    }

    fun resetAllOptions() {
        _state.update { currentState ->
            currentState.copy(
                selectedSortOption = "",
                selectedMood = "",
                selectedDogFriend = "",
                selectedSortTime = "",
                selectedSafety = emptyList(),
                selectedConvenience = emptyList(),
                selectedEnvironment = emptyList(),
                isMoodExpanded = false,
                isDogFriendExpanded = false,
                isSafetyExpanded = false,
                isConvenienceExpanded = false,
                isEnvironmentExpanded = false,
                isTimeExpanded = false
            )
        }
        loadInitialPosts()
    }

    fun applyOptions() {
        val currentState = _state.value

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val selectedOptions = buildSelectedOptionsList(currentState)

                val request = PostsListRequestDto(
                    durationStart = state.value.selectedSortTimeStart,
                    durationEnd = state.value.selectedSortTimeEnd,
                    selectedOptions = selectedOptions.ifEmpty { null }
                )

                postsListRepository.postList(
                    userId = userId.first(),
                    request = request
                ).onSuccess { listEntity ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            postsResult = listEntity
                        )
                    }
                    println("필터링된 게시물 로드 성공: ${listEntity.posts.size}개")
                }.onFailure { exception ->
                    _state.update { it.copy(isLoading = false) }
                    exception.printStackTrace()
                    println("필터링된 게시물 로드 실패: ${exception.message}")
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
                e.printStackTrace()
            }
        }
    }

    private fun buildSelectedOptionsList(state: TapListContract.TapListState): List<TraitList> {
        val selectedOptions = mutableListOf<TraitList>()
        val filterOptions = state.filterOptions ?: return emptyList()



        filterOptions.categoryList?.forEach { category ->
            val selectedOptionIds = mutableListOf<Int>()

            when (category.categoryName) {
                "분위기" -> {
                    if (state.selectedMood.isNotEmpty()) {
                        category.categoryOptions?.find { it.categoryOptionText == state.selectedMood }
                            ?.let { selectedOptionIds.add(it.categoryOptionId) }
                    }
                }
                "강아지 친구" -> {
                    if (state.selectedDogFriend.isNotEmpty()) {
                        category.categoryOptions?.find { it.categoryOptionText == state.selectedDogFriend }
                            ?.let { selectedOptionIds.add(it.categoryOptionId) }
                    }
                }
                "안전" -> {
                    state.selectedSafety.forEach { selectedSafety ->
                        category.categoryOptions?.find { it.categoryOptionText == selectedSafety }
                            ?.let { selectedOptionIds.add(it.categoryOptionId) }
                    }
                }
                "편의성" -> {
                    state.selectedConvenience.forEach { selectedConvenience ->
                        category.categoryOptions?.find { it.categoryOptionText == selectedConvenience }
                            ?.let { selectedOptionIds.add(it.categoryOptionId) }
                    }
                }
                "환경" -> {
                    state.selectedEnvironment.forEach { selectedEnvironment ->
                        category.categoryOptions?.find { it.categoryOptionText == selectedEnvironment }
                            ?.let { selectedOptionIds.add(it.categoryOptionId) }
                    }
                }
            }

            if (selectedOptionIds.isNotEmpty()) {
                selectedOptions.add(
                    TraitList(
                        categoryId = category.categoryId,
                        optionIds = selectedOptionIds
                    )
                )
            }
        }

        return selectedOptions
    }

    fun isAllOptionsSelected(): Boolean {
        val currentState = _state.value
        return currentState.selectedSortOption.isNotEmpty() ||
                currentState.selectedMood.isNotEmpty() ||
                currentState.selectedDogFriend.isNotEmpty() ||
                currentState.selectedSafety.isNotEmpty() ||
                currentState.selectedConvenience.isNotEmpty() ||
                currentState.selectedEnvironment.isNotEmpty()
    }

    fun isFilterApplied(): Boolean {
        return isAllOptionsSelected()
    }
}