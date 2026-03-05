package com.paw.key.domain.repository.user

import com.paw.key.domain.entity.petprofile.PetProfileEntity
import com.paw.key.domain.entity.user.PetBreedsEntity
import com.paw.key.domain.entity.user.UserInfoEntity
import com.paw.key.domain.entity.user.UserInfoResultEntity

interface UserRepository {
    suspend fun createUser(
        userInfoEntity: UserInfoEntity
    ): Result<UserInfoResultEntity>

    suspend fun deleteUser(
        provider: String
    ): Result<Unit>

    suspend fun getPetBreeds(): Result<PetBreedsEntity>

    suspend fun getPetProfiles(petId: Int): Result<PetProfileEntity>
}
