package com.paw.key.data.repositoryimpl

import com.paw.key.data.remote.datasource.ArchivedListDataSource
import com.paw.key.domain.entity.archivedlist.ArchivedListPostsEntity
import com.paw.key.domain.repository.ArchivedListRepository
import javax.inject.Inject

class ArchivedListRepositoryImpl @Inject constructor(
    private val archivedListDataSource: ArchivedListDataSource,
) : ArchivedListRepository {
    override suspend fun getArchivedList(userId: Int): Result<ArchivedListPostsEntity> = runCatching {
        archivedListDataSource.getArchivedList(userId).data.toEntity()
    }
}