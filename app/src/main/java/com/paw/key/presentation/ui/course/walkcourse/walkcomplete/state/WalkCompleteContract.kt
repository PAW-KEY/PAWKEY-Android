package com.paw.key.presentation.ui.course.walkcourse.walkcomplete.state

import androidx.compose.runtime.Immutable
import com.paw.key.presentation.ui.course.walkcourse.walkcomplete.model.WalkFinishModel
import com.paw.key.presentation.ui.course.walkcourse.walkcomplete.model.WalkInfoModel
import com.paw.key.presentation.ui.course.walkcourse.walkcomplete.model.WalkMapInfoModel

@Immutable
data class WalkCompleteState(
    val walkCompleteUserInfo: WalkFinishModel = WalkFinishModel(),
    val walkCompleteFinishInfo: WalkInfoModel = WalkInfoModel(),
    val walkCompleteMapInfo: WalkMapInfoModel = WalkMapInfoModel()
)
