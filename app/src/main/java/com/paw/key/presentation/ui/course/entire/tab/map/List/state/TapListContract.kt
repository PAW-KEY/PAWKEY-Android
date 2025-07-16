package com.paw.key.presentation.ui.course.entire.tab.map.List.state

import androidx.compose.runtime.Immutable
import com.paw.key.domain.model.entity.filter.FilterEntity
import com.paw.key.domain.model.entity.list.ListEntity

class TapListContract {
    @Immutable
    data class TapListState(
        val isLoading: Boolean = false,
        val filterOptions: FilterEntity? = null,
        val postsResult: ListEntity? = null,

        val selectedSortOption: String = "",
        val selectedMood: String = "",
        val selectedDogFriend: String = "",

        val selectedSafety: List<String> = emptyList(),
        val selectedConvenience: List<String> = emptyList(),
        val selectedEnvironment: List<String> = emptyList(),

        val isMoodExpanded: Boolean = false,
        val isDogFriendExpanded: Boolean = false,
        val isSafetyExpanded: Boolean = false,
        val isConvenienceExpanded: Boolean = false,
        val isEnvironmentExpanded: Boolean = false,
    )

    object Options {
        val sortOptions = listOf(
            "최신순",
            "인기순"
        )
    }
}