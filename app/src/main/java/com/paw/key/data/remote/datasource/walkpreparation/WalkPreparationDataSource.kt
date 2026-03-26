package com.paw.key.data.remote.datasource.walkpreparation

import com.paw.key.data.dto.request.walkpreparation.WalkPreparationRequestDto
import com.paw.key.data.service.walkpreparation.WalkPreparationService
import javax.inject.Inject

class WalkPreparationDataSource @Inject constructor(
    private val walkPreparationService: WalkPreparationService
) {
    suspend fun getWalkPreparation() = walkPreparationService.getWalkPreparation()

    suspend fun patchWalkPreparation(
        body : WalkPreparationRequestDto
    ) = walkPreparationService.patchWalkPreparation(
        body = body
    )

    suspend fun getWalkPreparationMessage() = walkPreparationService.getWalkPreparationMessage()
}