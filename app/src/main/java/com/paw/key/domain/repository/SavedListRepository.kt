package com.paw.key.domain.repository

import com.paw.key.domain.model.entity.savedlist.SavedListEntity
import com.paw.key.domain.model.entity.savedlist.SavedListPostEntity

interface SavedListRepository {
    suspend fun getSavedList(userId: Int): Result<SavedListPostEntity>
}