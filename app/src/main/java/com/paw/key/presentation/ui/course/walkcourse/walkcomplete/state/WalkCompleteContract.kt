package com.paw.key.presentation.ui.course.walkcourse.walkcomplete.state

import androidx.compose.runtime.Immutable
import com.paw.key.presentation.ui.course.walkcourse.model.WalkInfoState

@Immutable
data class WalkCompleteState(
    val userProfile: String = "",
    val petName : String = "",
    val dateTime : String = "",
    val mapImage: String = "",

    val walkInfo: WalkInfoState = WalkInfoState()
)