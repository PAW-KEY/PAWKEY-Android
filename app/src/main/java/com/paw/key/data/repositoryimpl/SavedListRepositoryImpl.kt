package com.paw.key.data.repositoryimpl

import com.paw.key.data.remote.datasource.SavedListDataSource
import com.paw.key.domain.model.entity.savedlist.SavedListEntity
import com.paw.key.domain.model.entity.savedlist.SavedListPostEntity
import com.paw.key.domain.repository.SavedListRepository
import javax.inject.Inject

class SavedListRepositoryImpl @Inject constructor(
    private val savedListDataSource: SavedListDataSource,
) : SavedListRepository {
    override suspend fun getSavedList(userId: Int): Result<SavedListPostEntity> = runCatching {
        savedListDataSource.getSavedList(userId).data.toEntity()
    }
}