package com.paw.key.domain.repository.petprofile

import com.paw.key.domain.entity.petprofile.PetProfileEntity

interface PetProfileRepository {
    suspend fun getPetProfiles(userId: Int): Result<List<PetProfileEntity>>
}