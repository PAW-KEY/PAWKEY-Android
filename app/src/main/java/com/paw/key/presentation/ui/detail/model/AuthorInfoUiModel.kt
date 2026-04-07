package com.paw.key.presentation.ui.detail.model

import com.paw.key.domain.entity.posts.AuthorInfoEntity

data class AuthorInfoUiModel(
    val authorId: Int = -1,
    val petId: Int = -1,
    val petName: String = "",
    val petProfileImage: String = ""
)

fun AuthorInfoEntity.toUiModel() = AuthorInfoUiModel(
    authorId = authorId,
    petId = petId,
    petName = petName,
    petProfileImage = petProfileImage
)