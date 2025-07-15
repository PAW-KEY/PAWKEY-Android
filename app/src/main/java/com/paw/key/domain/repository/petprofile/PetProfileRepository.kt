package com.paw.key.domain.repository.petprofile

import com.paw.key.domain.model.entity.petprofile.PetProfileEntity

interface PetProfileRepository {
    suspend fun getPetProfiles(userId: Int): Result<List<PetProfileEntity>>
}