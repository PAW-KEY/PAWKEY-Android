package com.paw.key.presentation.ui.course.walkcourse.walkprepare

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.model.WalkPrepareItemModel
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.state.WalkPrepareState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WalkPrepareViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(WalkPrepareState())
    val state = _state.asStateFlow()

    fun addWalkItem() {
        val currentList = _state.value.walkPrepareItemList
        val newId = (currentList.maxOfOrNull { it.id } ?: 0) + 1

        val newItem = WalkPrepareItemModel(id = newId, walkItem = TextFieldState(""))

        _state.update {
            it.copy(
                walkPrepareItemList = it.walkPrepareItemList.add(newItem),
                lastAddedItemId = newId
            )
        }
    }

    fun deleteWalkItem(id : Int) {
        _state.update { state ->
            val newList = state.walkPrepareItemList.filter { it.id != id }.toPersistentList()
            state.copy(walkPrepareItemList = newList)
        }
    }

    fun updateWalkItem(id: Int, newText: String) {
        _state.update { state ->
            val newList = state.walkPrepareItemList.map { item ->
                if (item.id == id) item.copy(walkItem = TextFieldState(newText)) else item
            }.toPersistentList()
            state.copy(walkPrepareItemList = newList)
        }
    }

    fun clearLastAddedItemId() {
        _state.update { it.copy(lastAddedItemId = null) }
    }
}
