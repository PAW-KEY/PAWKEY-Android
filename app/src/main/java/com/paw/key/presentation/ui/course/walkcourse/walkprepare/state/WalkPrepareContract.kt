package com.paw.key.presentation.ui.course.walkcourse.walkprepare.state

import androidx.compose.foundation.text.input.TextFieldState
import com.paw.key.domain.entity.walkpreparation.WalkPreparationEntity
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.model.WalkPreparationMessageModel
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.model.WalkPrepareItemModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class WalkPrepareState(
    val walkPrepareItemList: PersistentList<WalkPrepareItemModel> = persistentListOf(),
    val walkPreparationMessage: WalkPreparationMessageModel = WalkPreparationMessageModel(),
    val lastAddedItemId: Int? = null,
) {
    val dummyWalkPrepare = listOf(
        WalkPrepareItemModel(1, TextFieldState("")),
        WalkPrepareItemModel(2, TextFieldState("리드줄")),
        WalkPrepareItemModel(3, TextFieldState("물")),
        WalkPrepareItemModel(4, TextFieldState("간식")),
    ).toImmutableList()
}

fun WalkPrepareState.toEntity(): WalkPreparationEntity {
    return WalkPreparationEntity(
        preparationList = walkPrepareItemList
            .map { item -> item.walkItem.text.toString() }
            .filter { text -> text.isNotBlank() }
    )
}