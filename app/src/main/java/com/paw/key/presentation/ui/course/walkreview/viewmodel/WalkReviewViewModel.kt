package com.paw.key.presentation.ui.course.walkreview.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.course.walkreview.state.WalkReviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WalkReviewViewModel @Inject constructor(
) : ViewModel() {
    private val _state = MutableStateFlow(WalkReviewState())
    val state = _state.asStateFlow()

    fun updateImageList(uri : Uri) {
        _state.update {
            it.copy(
                walkReviewImageList = it.walkReviewImageList.add(uri)
            )
        }
    }

    fun deleteImage(index : Int) {
        _state.update {
            it.copy(
                walkReviewImageList = it.walkReviewImageList.removeAt(index)
            )
        }
    }

    fun updateReviewTitle(title : String) {
        _state.update {
            it.copy(
                walkReviewTitle = title
            )
        }
    }

    fun updateReviewContent(content : String) {
        _state.update {
            it.copy(
                walkReviewContent = content
            )
        }
    }

    fun onFilterClick(
        item: String,
        categoryList: List<String>,
        isSingle: Boolean
    ) {
        val newFilterList = _state.value.getUpdatedFilterList(item, categoryList, isSingle)

        _state.update {
            it.copy(
                walkReviewSelectedFilterData = newFilterList
            )
        }
    }

    fun completeWalkReview(isPublic : Boolean) {
        // Todo 나중에 검증 로직 추가 - 예를 들면 데이터가 다 필요한지 등 - 필터
        _state.update {
            it.copy(
                isComplete = true
            )
        }
    }
}