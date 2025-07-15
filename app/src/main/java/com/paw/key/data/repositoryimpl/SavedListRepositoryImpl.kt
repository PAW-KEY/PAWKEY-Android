package com.paw.key.data.repositoryimpl

import com.paw.key.data.remote.datasource.SavedListDataSource
import javax.inject.Inject

class SavedListRepositoryImpl @Inject constructor(
    private val savedListDataSource: SavedListDataSource
){
    suspend fun getSavedList(userId: Int) = savedListDataSource.getSavedList(userId)
}