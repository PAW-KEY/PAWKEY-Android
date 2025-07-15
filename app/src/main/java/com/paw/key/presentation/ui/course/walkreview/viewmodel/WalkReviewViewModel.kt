package com.paw.key.presentation.ui.course.walkreview.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.core.util.PhotoUtils
import com.paw.key.data.dto.request.walkreview.WalkCourseReviewRequestDto
import com.paw.key.domain.model.entity.walkreview.WalkReviewRecordCategory
import com.paw.key.domain.model.entity.walkreview.WalkReviewRecordEntity
import com.paw.key.domain.repository.walkreview.WalkReviewRepository
import com.paw.key.presentation.ui.course.walkreview.state.WalkReviewContract.WalkReviewSideEffect
import com.paw.key.presentation.ui.course.walkreview.state.WalkReviewContract.WalkReviewState
import com.paw.key.presentation.ui.course.walkreview.util.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalkReviewViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: WalkReviewRepository
) : ViewModel() {
    private val _state = MutableStateFlow(WalkReviewState())
    val state : StateFlow<WalkReviewState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<WalkReviewSideEffect>()
    val sideEffect : MutableSharedFlow<WalkReviewSideEffect>
        get() = _sideEffect


    fun postWalkReview(routeId : Int, isShare : Boolean) {
        viewModelScope.launch {
            val category = state.value.categoryList.map { category ->
                WalkReviewRecordCategory(
                    categoryId = category.categoryId,
                    selectedOptionIds = category.options.filter { it.isSelected }.map { it.optionId }
                )
            }

            val requestEntity = WalkReviewRecordEntity(
                title = state.value.title,
                description = state.value.content,
                isPublic = isShare,
                categories = category,
                routeId = routeId.toLong()
            )

            val imageFiles = PhotoUtils.uriListToMultipartParts(
                uris = state.value.images,
                contentResolver = context.contentResolver
            )

            repository.postWalkReview(
                userId = 2,
                imageFiles = imageFiles,
                walkReviewRequest = requestEntity
            ).onSuccess {
                _sideEffect.emit(WalkReviewSideEffect.ShowSnackBar("리뷰 전송 성공!"))
                _sideEffect.emit(WalkReviewSideEffect.NavigateNext(routeId))
                Log.d("WalkReviewViewModel", "리뷰 전송 성공!")
            }.onFailure {
                _sideEffect.emit(WalkReviewSideEffect.ShowSnackBar("리뷰 전송 실패!"))
                Log.e("WalkReviewViewModel", "리뷰 전송 실패!")
            }
        }
    }

    fun getWalkReviewCategory() {
        viewModelScope.launch {
            repository.getWalkReviewCategory(
                userId = 2
            ).onSuccess {
                _state.update { currentState ->
                    currentState.copy(
                        categoryList = it.categoryList.map { entity -> entity.toUiModel() }
                    )
                }
                Log.d("WalkReviewViewModel", "카테고리 가져오기 성공!")
            }.onFailure {
                _sideEffect.emit(WalkReviewSideEffect.ShowSnackBar("카테고리 가져오기 실패!"))
                Log.e("WalkReviewViewModel", "카테고리 가져오기 실패!")
            }
        }
    }

    fun getWalkReviewInfo(routeId: Int) {
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
                Log.d("WalkReviewViewModel", "산책 정보 가져오기 성공!")
            }.onFailure {
                _sideEffect.emit(WalkReviewSideEffect.ShowSnackBar("산책 정보 가져오기 실패!"))
                Log.e("WalkReviewViewModel", "산책 정보 가져오기 실패!")
            }
        }
    }

    fun onClickPublic(isShare : Boolean) {
        _state.update {
            it.copy(
                isPublic = isShare
            )
        }
    }

    fun onOptionSelected(categoryId: Int, optionId: Int) {
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

    fun onTitleTextChanged(text : String) {
        _state.update {
            it.copy(
                title = text
            )
        }
    }

    fun onContentTextChanged(text : String) {
        _state.update {
            it.copy(
                content = text
            )
        }
    }

    fun onSelectCategoryOption(categoryId: Int, selectedOptionId: Int) {
        _state.update { current ->
            val updatedList = current.categoryList.map { category ->
                if (category.categoryId == categoryId) {
                    category.copy(
                        options = category.options.map { option ->
                            option.copy(
                                isSelected = option.optionId == selectedOptionId
                            )
                        }
                    )
                } else category
            }

            current.copy(categoryList = updatedList)
        }
    }

    fun onImagesSelected(uris: List<Uri>) {
        val currentImages = _state.value.images.toMutableList()
        currentImages.addAll(uris)

        _state.update {
            it.copy(
                images = currentImages.toPersistentList()
            )
        }
    }

    fun onImageDelete(uri : Uri?) {
        _state.update {
            it.copy(
                images = it.images.filter { currentUri ->
                    currentUri != uri
                }.toPersistentList()
            )
        }
    }
}