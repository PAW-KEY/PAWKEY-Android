package com.paw.key.presentation.ui.course.walkcourse.walkprepare

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.walk.WalkRepository
import com.paw.key.domain.repository.walkpreparation.WalkPreparationRepository
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.model.WalkPreparationMessageModel
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.model.WalkPrepareItemModel
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.state.WalkPrepareSideEffect
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.state.WalkPrepareState
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.state.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WalkPrepareViewModel @Inject constructor(
    private val preparationRepository: WalkPreparationRepository,
    private val walkRepository: WalkRepository
) : ViewModel() {
    private val _state = MutableStateFlow(WalkPrepareState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<WalkPrepareSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        fetchWalkPreparationMessage()
    }

    fun fetchWalkPreparationMessage() {
        viewModelScope.launch {
            preparationRepository.getWalkPreparationMessage()
                .onSuccess { result ->
                    _state.update { currentState ->
                        currentState.copy(
                            walkPreparationMessage = WalkPreparationMessageModel(
                                mainMessage = result.mainMessage,
                                subMessage = result.subMessage
                            )
                        )
                    }
                }
        }
    }

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
    
    fun updateWalkPreparation() {
        viewModelScope.launch {
            val currentState = _state.value
            val preparationData = currentState.toEntity()

            preparationRepository.patchWalkPreparation(
                entity = preparationData
            )
        }
    }

    fun startWalk() {
        viewModelScope.launch {
            walkRepository.startWalk(deviceInfo = "ANDROID")
                .onSuccess {
                    Timber.e("startWalk success ${it.routeId}")
                    _sideEffect.emit(WalkPrepareSideEffect.NavigateToWalkCourse(it.routeId))
                }
                .onFailure {
                    Timber.e(it)
                    _sideEffect.emit(WalkPrepareSideEffect.ShowToastMessage("산책 시작에 실패하였습니다."))
                }
        }
    }

    /*fun finishWalk() {
        viewModelScope.launch {
            walkRepository.finishWalk(
                routeId = "routeId",
                walkFinish = state.value.toEntity()
            ).onSuccess {
                _sideEffect.emit(WalkPrepareSideEffect.NavigateToWalkCourse(it.routeId))
            }.onFailure {
                Timber.e(it)
                _sideEffect.emit(WalkPrepareSideEffect.ShowToastMessage("산책 종료에 실패하였습니다."))
            }
        }
    }*/
}
