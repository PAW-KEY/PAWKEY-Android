package com.paw.key.domain.repository

import com.paw.key.domain.entity.archivedlist.ArchivedListPostsEntity

interface ArchivedListRepository {
    suspend fun getArchivedList(userId: Int): Result<ArchivedListPostsEntity>
}