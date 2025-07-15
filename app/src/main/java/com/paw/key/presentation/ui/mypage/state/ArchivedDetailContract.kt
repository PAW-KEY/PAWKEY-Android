package com.paw.key.presentation.ui.mypage.state

import androidx.compose.runtime.Immutable
import com.paw.key.domain.model.entity.walklist.CategoryTop3Entity

@Immutable
data class ArchivedDetailState(
    val postId: Int = 0,
    val routeId: Int = 0,
    val postTitle: String = "",
    val postContent: String = "",
    val isLiked: Boolean = false,
    val petName: String = "",
    val petProfileImage: String = "",
    val regionName: String = "",
    val createdAt: String = "",
    val categorySummary: List<String> = emptyList(),
    val routeMapImageUrl: String = "",
    val walkingImageUrls: List<String> = emptyList(),

    val categoryTop3: List<CategoryTop3Entity> = emptyList(),
    val totalReviewCount: Int = 0
)
