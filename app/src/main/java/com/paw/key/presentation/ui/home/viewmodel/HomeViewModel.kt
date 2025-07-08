package com.paw.key.presentation.ui.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
    var isLocationMenuVisible by mutableStateOf(false)
        private set

    fun toggleLocationMenu() {
        isLocationMenuVisible = !isLocationMenuVisible
    }

    fun hideLocationMenu() {
        isLocationMenuVisible = false
    }
}
