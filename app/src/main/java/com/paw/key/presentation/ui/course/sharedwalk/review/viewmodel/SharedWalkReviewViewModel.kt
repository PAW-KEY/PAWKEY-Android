package com.paw.key.presentation.ui.course.sharedwalk.review.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.model.entity.sharedwalk.SharedWalkReviewCategory
import com.paw.key.domain.model.entity.sharedwalk.SharedWalkReviewEntity
import com.paw.key.domain.model.entity.walkreview.WalkReviewRecordCategory
import com.paw.key.domain.repository.sharedwalk.SharedWalkRepository
import com.paw.key.domain.repository.walkreview.WalkReviewRepository
import com.paw.key.presentation.ui.course.sharedwalk.review.state.SharedWalkReviewSideEffect
import com.paw.key.presentation.ui.course.sharedwalk.review.state.SharedWalkReviewState
import com.paw.key.presentation.ui.course.walkreview.util.toUiModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SharedWalkReviewViewModel @Inject constructor(
    private val repository: WalkReviewRepository,
    private val sharedRepository : SharedWalkRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SharedWalkReviewState())
    val state : StateFlow<SharedWalkReviewState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SharedWalkReviewSideEffect>()
    val sideEffect : SharedFlow<SharedWalkReviewSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun postSharedWalkReview(userId: Int, routeId: Int) {
        viewModelScope.launch {
            val categoryList = state.value.categoryList.map { category ->
                SharedWalkReviewCategory(
                    categoryId = category.categoryId,
                    selectedOptionIds = category.options.filter { it.isSelected }.map { it.optionId }
                )
            }

            val review = SharedWalkReviewEntity (
                routeId = routeId,
                categories = categoryList,
            )

            Log.d("SharedWalkReviewSideEffect", "리뷰 : $review")
            sharedRepository.postSharedWalkReviewRegister(
                userId = userId,
                review = review
            ).onSuccess {
                _sideEffect.emit(SharedWalkReviewSideEffect.NavigateNext)
                Log.d("SharedWalkReviewSideEffect", "리뷰 등록 성공!")
            }.onFailure {
                _sideEffect.emit(SharedWalkReviewSideEffect.ShowSnackBar("리뷰 등록 실패!"))
                Log.e("SharedWalkReviewSideEffect", "리뷰 등록 실패!")
            }
        }
    }

    fun getSharedWalkReviewCategory(userId: Int) {
        viewModelScope.launch {
            repository.getWalkReviewCategory(
                userId = userId
            ).onSuccess {
                _state.update { currentState ->
                    currentState.copy(
                        categoryList = it.categoryList.map { entity -> entity.toUiModel() }
                    )
                }
                Log.d("SharedWalk", "카테고리 가져오기 성공!")
            }.onFailure {
                _sideEffect.emit(SharedWalkReviewSideEffect.ShowSnackBar("카테고리 가져오기 실패!"))
                Log.e("SharedWalk", "카테고리 가져오기 실패!")
            }
        }
    }

    fun getSharedWalkReviewInfo(routeId: Int) {
        viewModelScope.launch {
            repository.getWalkReviewInfo(
                userId = 2,
                routeId = routeId
            ).onSuccess {
                _state.update { currentState ->
                    currentState.copy(
                        location = it.routeDto.locationDescription,
                        time = it.routeDto.dateDescription,
                        tags = it.routeDto.descriptionTags.toPersistentList(),
                        petName = it.petName
                    )
                }
                Log.d("SharedWalkReviewSideEffect", "산책 정보 가져오기 성공!")
            }.onFailure {
                _sideEffect.emit(SharedWalkReviewSideEffect.ShowSnackBar("산책 정보 가져오기 실패!"))
                Log.e("SharedWalkReviewSideEffect", "산책 정보 가져오기 실패!")
            }
        }
    }

    fun onClickFeedback(categoryId: Int, optionId: Int) {
        _state.update { current ->
            val updatedCategories = current.categoryList.map { category ->
                if (category.categoryId == categoryId) {
                    category.copy(
                        options = category.options.map { option ->
                            option.copy(isSelected = option.optionId == optionId)
                        }
                    )
                } else category
            }

            current.copy(categoryList = updatedCategories)
        }
    }

    fun onClickSharedReview() {
        _state.update {
            it.copy(
                isDialogVisible = true
            )
        }
    }
}