package com.paw.key.presentation.ui.community

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(CommunityState())
    val state = _state.asStateFlow()

    fun onFilterClick(
        item: String,
        categoryList: List<String>,
        isSingle: Boolean
    ) {
        val newFilterList = _state.value.getUpdatedFilterList(item, categoryList, isSingle)

        _state.update {
            it.copy(
                communitySelectedFilterData = newFilterList
            )
        }
    }

    fun onRefreshFilter() {
        _state.update {
            it.copy(
                communitySelectedFilterData = persistentListOf()
            )
        }
    }

    fun postFilter() {

    }
}