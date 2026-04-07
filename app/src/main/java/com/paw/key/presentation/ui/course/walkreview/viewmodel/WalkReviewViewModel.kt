package com.paw.key.presentation.ui.course.walkreview.viewmodel

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.paw.key.domain.entity.image.ImageDomainType
import com.paw.key.domain.entity.image.ImagePresignedEntity
import com.paw.key.domain.entity.posts.CategoryOptionEntity
import com.paw.key.domain.entity.posts.PostsInfoEntity
import com.paw.key.domain.repository.image.ImageRepository
import com.paw.key.domain.repository.posts.PostsRepository
import com.paw.key.presentation.ui.community.model.FilterCategoryUiModel
import com.paw.key.presentation.ui.community.model.toUiModel
import com.paw.key.presentation.ui.course.walkreview.model.toUiModel
import com.paw.key.presentation.ui.course.walkreview.navigation.WalkReview
import com.paw.key.presentation.ui.course.walkreview.state.WalkReviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WalkReviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postsRepository: PostsRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {
    private val routeId = savedStateHandle.toRoute<WalkReview>().routeId
    private val routeImageId = savedStateHandle.toRoute<WalkReview>().routeImageId

    private val _state = MutableStateFlow(WalkReviewState())
    val state = _state.asStateFlow()

    init {
        fetchPostsFilter()

        if (routeId != null) {
            fetchRouteSummary(routeId)
        }
    }

    private fun fetchPostsFilter() {
        viewModelScope.launch {
            postsRepository.getCategoriesFilter()
                .onSuccess { data ->
                    _state.update { it.copy(filterUiModel = data.toUiModel()) }
                }
                .onFailure(Timber::e)
        }
    }

    fun fetchRouteSummary(routeId: Int) {
        viewModelScope.launch {
            postsRepository.getPostSummary(routeId)
                .onSuccess { result ->
                    _state.update { currentState ->
                        currentState.copy(
                            routeSummary = result.toUiModel()
                        )
                    }
                }
                .onFailure(Timber::e)
        }
    }

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

    fun updateReviewTitle(title: String) {
        val limitedTitle = title.take(14)

        _state.update {
            it.copy(walkReviewTitle = limitedTitle)
        }
    }

    fun updateReviewContent(content : String) {
        val limitedContent = content.take(250)

        _state.update {
            it.copy(
                walkReviewContent = limitedContent
            )
        }
    }
    fun completeWalkReview(isPublic: Boolean) {
        val currentState = _state.value
        val currentRouteId = routeId ?: return

        viewModelScope.launch {
            try {
                val uploadedWalkImageIds = mutableListOf<Int>()

                currentState.walkReviewImageList.forEach { uri ->
                    val localUriString = uri.toString()

                    val presignedResult = imageRepository.presignedImage(
                        ImagePresignedEntity(
                            domain = ImageDomainType.WALK,
                            contentType = "image/webp"
                        )
                    ).getOrElse { throw Exception("Presigned URL 발급 실패") }

                    imageRepository.uploadS3(
                        presignedUrl = presignedResult.uploadUrl,
                        uriString = localUriString
                    ).getOrElse { throw Exception("S3 업로드 실패") }

                    val registerParam = "${presignedResult.imageUrl}#${localUriString}"
                    val registerResult = imageRepository.registerImage(
                        uriString = registerParam,
                        domainType = ImageDomainType.WALK
                    ).getOrElse { throw Exception("서버에 이미지 정보 등록 실패") }

                    uploadedWalkImageIds.add(registerResult.imageId)
                }

                val categoryOptions = currentState.selectedOptionIds.map { (categoryId, optionIds) ->
                    CategoryOptionEntity(
                        categoryId = categoryId,
                        selectedOptionIds = optionIds
                    )
                }

                val postsInfo = PostsInfoEntity(
                    title = currentState.walkReviewTitle,
                    description = currentState.walkReviewContent,
                    isPublic = isPublic,
                    routeId = currentRouteId,
                    routeImageId = routeImageId ?: 3,
                    walkImageIds = uploadedWalkImageIds,
                    selectedOptionsForCategories = categoryOptions,
                    imageUrls = uploadedWalkImageIds
                )

                postsRepository.postPosts(postsInfo).onSuccess {
                    Timber.d("게시물 등록 성공")
                    _state.update {
                        it.copy(isComplete = true)
                    }
                }.onFailure { e ->
                    Timber.e(e, "게시물 등록 실패 (API 오류)")
                }

            } catch (e: Exception) {
                Timber.e(e, "이미지 업로드 및 게시물 등록 전체 프로세스 중 에러 발생")
            }
        }
    }

    fun onFilterClick(optionId: Int, category: FilterCategoryUiModel) {
        _state.update {
            it.copy(
                selectedOptionIds = it.getUpdatedOptionIds(optionId, category)
            )
        }
    }
}