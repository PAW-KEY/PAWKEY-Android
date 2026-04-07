package com.paw.key.presentation.ui.detail.model

import com.paw.key.domain.entity.posts.WalkImageEntity

data class WalkImageUiModel(
    val imageId: Int,
    val imageUrl: String
)

fun WalkImageEntity.toUiModel() = WalkImageUiModel(
    imageId = imageId,
    imageUrl = imageUrl
)