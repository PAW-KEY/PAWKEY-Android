package com.paw.key.data.remote.datasource

import com.paw.key.data.service.ArchivedListService
import javax.inject.Inject

class ArchivedListDataSource @Inject constructor(
    private val archivedListService: ArchivedListService
) {
    suspend fun getArchivedList(userId: Int) = archivedListService.getArchivedList(userId)
}
