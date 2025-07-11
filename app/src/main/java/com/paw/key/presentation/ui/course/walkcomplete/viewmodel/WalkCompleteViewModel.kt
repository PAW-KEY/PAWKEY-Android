package com.paw.key.presentation.ui.course.walkcomplete.viewmodel

import android.content.Context
import android.graphics.Bitmap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.BitmapRepository
import com.paw.key.presentation.ui.course.walkcomplete.state.WalkCompleteContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalkCompleteViewModel @Inject constructor(
    private val bitmapRepository: BitmapRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(WalkCompleteContract.WalkCompleteState())
    val state: StateFlow<WalkCompleteContract.WalkCompleteState>
        get() = _state.asStateFlow()

    private val _savedMapBitmap = MutableStateFlow<Bitmap?>(null)
    val savedMapBitmap: StateFlow<Bitmap?>
        get() = _savedMapBitmap.asStateFlow()

    init {
        viewModelScope.launch {
            bitmapRepository.getSavedBitmap().collectLatest { bitmap ->
                _savedMapBitmap.value = bitmap
            }
        }
    }
}