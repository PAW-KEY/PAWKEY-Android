package com.paw.key.presentation.ui.detail

import androidx.compose.runtime.Immutable
import com.paw.key.presentation.ui.detail.model.PostsDetailUiModel
import com.paw.key.presentation.ui.detail.model.ReviewUiModel

@Immutable
data class DetailState(
    val postDetail: PostsDetailUiModel = PostsDetailUiModel(),
    val reviewDetail: ReviewUiModel = ReviewUiModel()
)

sealed interface DetailSideEffect {
    data object navigateToCommunity: DetailSideEffect
}