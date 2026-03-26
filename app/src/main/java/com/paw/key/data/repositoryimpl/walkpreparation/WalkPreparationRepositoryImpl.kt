package com.paw.key.data.repositoryimpl.walkpreparation

import com.paw.key.core.util.suspendRunCatching
import com.paw.key.data.dto.request.walkpreparation.toDto
import com.paw.key.data.remote.datasource.walkpreparation.WalkPreparationDataSource
import com.paw.key.domain.entity.walkpreparation.WalkPreparationEntity
import com.paw.key.domain.entity.walkpreparation.WalkPreparationMessageEntity
import com.paw.key.domain.repository.walkpreparation.WalkPreparationRepository
import javax.inject.Inject

class WalkPreparationRepositoryImpl @Inject constructor(
    private val walkPreparationDataSource: WalkPreparationDataSource
) : WalkPreparationRepository {
    override suspend fun getWalkPreparation(): Result<WalkPreparationEntity> = suspendRunCatching{
        walkPreparationDataSource.getWalkPreparation().data.toEntity()
    }

    override suspend fun patchWalkPreparation(entity: WalkPreparationEntity): Result<WalkPreparationEntity> = suspendRunCatching{
        walkPreparationDataSource.patchWalkPreparation(
            body = entity.toDto()
        ).data.toEntity()
    }

    override suspend fun getWalkPreparationMessage(): Result<WalkPreparationMessageEntity> = suspendRunCatching{
        walkPreparationDataSource.getWalkPreparationMessage().data.toEntity()
    }
}