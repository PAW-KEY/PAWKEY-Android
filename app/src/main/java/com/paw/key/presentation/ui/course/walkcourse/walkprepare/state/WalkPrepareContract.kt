package com.paw.key.presentation.ui.course.walkcourse.walkprepare.state

import com.paw.key.presentation.ui.course.walkcourse.walkprepare.model.WalkPrepareItemModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class WalkPrepareState(
    val walkPrepareItemList: ImmutableList<WalkPrepareItemModel> = persistentListOf(),
) {
    val dummyWalkPrepare = listOf(
        WalkPrepareItemModel(1, "배변봉투"),
        WalkPrepareItemModel(2, "리드줄"),
        WalkPrepareItemModel(3, "물"),
        WalkPrepareItemModel(4, "간식"),
    ).toImmutableList()
}