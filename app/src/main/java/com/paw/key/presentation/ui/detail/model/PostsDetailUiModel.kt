package com.paw.key.presentation.ui.detail.model

import com.paw.key.domain.entity.posts.PostsDetailEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class PostsDetailUiModel(
    val postId: Int = -1,
    val title: String = "",
    val description: String = "",
    val isPublic: Boolean = false,
    val isMine: Boolean = false,
    val authorInfo: AuthorInfoUiModel = AuthorInfoUiModel(),
    val routeDisplay: RouteDisplayUiModel = RouteDisplayUiModel(),
    val categoryTagTexts: ImmutableList<String> = persistentListOf(),
    val walkImages: ImmutableList<WalkImageUiModel> = persistentListOf(),
)

fun PostsDetailEntity.toUiModel() = PostsDetailUiModel(
    postId = postId,
    title = title,
    description = description,
    isPublic = isPublic,
    isMine = isMine,
    authorInfo = authorInfo.toUiModel(),
    routeDisplay = routeDisplay.toUiModel(),
    categoryTagTexts = categoryTagTexts.toImmutableList(),
    walkImages = walkImages.map { it.toUiModel() }.toImmutableList()
)