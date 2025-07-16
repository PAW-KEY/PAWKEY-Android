package com.paw.key.domain.repository

import com.paw.key.domain.model.entity.savedlist.SavedListEntity

interface SavedListRepository {
    suspend fun getSavedList(userId: Int): Result<List<SavedListEntity>>
}