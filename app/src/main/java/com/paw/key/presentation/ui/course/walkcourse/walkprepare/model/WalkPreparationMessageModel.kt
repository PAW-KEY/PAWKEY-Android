package com.paw.key.presentation.ui.course.walkcourse.walkprepare.model

import com.paw.key.domain.entity.walkpreparation.WalkPreparationMessageEntity

data class WalkPreparationMessageModel(
    val mainMessage: String = "",
    val subMessage: String = ""
)

fun WalkPreparationMessageEntity.toUiModel(): WalkPreparationMessageModel {
    return WalkPreparationMessageModel(
        mainMessage = mainMessage,
        subMessage = subMessage
    )
}
