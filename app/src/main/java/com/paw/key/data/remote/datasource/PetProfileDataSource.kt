package com.paw.key.data.remote.datasource

import com.paw.key.data.service.PetProfileService
import javax.inject.Inject

class PetProfileDataSource @Inject constructor(
    private val petprofileservice: PetProfileService
) {
    suspend fun getPetProfiles(userId: Int) = petprofileservice.getPetProfiles(userId)
}