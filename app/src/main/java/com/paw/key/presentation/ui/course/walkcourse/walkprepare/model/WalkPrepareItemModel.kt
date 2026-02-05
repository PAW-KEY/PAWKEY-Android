package com.paw.key.presentation.ui.course.walkcourse.walkprepare.model

import androidx.compose.foundation.text.input.TextFieldState

data class WalkPrepareItemModel(
    val id : Int = 0,
    val walkItem: TextFieldState = TextFieldState()
)
