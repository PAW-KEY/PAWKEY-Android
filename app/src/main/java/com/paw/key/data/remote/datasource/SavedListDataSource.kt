package com.paw.key.data.remote.datasource

import com.paw.key.data.service.SavedListService
import javax.inject.Inject

class SavedListDataSource @Inject constructor(
    private val savedListService: SavedListService
) {
    suspend fun getSavedList(userId: Int) = savedListService.getSavedList(userId)
}