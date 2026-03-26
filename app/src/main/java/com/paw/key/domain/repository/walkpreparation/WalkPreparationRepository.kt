package com.paw.key.domain.repository.walkpreparation

import com.paw.key.domain.entity.walkpreparation.WalkPreparationEntity
import com.paw.key.domain.entity.walkpreparation.WalkPreparationMessageEntity

interface WalkPreparationRepository {
    suspend fun getWalkPreparation() : Result<WalkPreparationEntity>

    suspend fun patchWalkPreparation(
        entity : WalkPreparationEntity
    ) : Result<WalkPreparationEntity>

    suspend fun getWalkPreparationMessage() : Result<WalkPreparationMessageEntity>
}