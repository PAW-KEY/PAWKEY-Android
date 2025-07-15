package com.paw.key.presentation.ui.mypage.state

import androidx.compose.runtime.Immutable

object ArchivedDetailContract {
    @Immutable
    data class ArchivedDetailState(
        val title: String,
        val petName: String,
        val date: String,
        val location: String,
        val distance: String,
        val time: String,
        val option: List<String> = emptyList(),
        val imageUrl: String
    )
}