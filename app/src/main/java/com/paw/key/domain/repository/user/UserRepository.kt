package com.paw.key.domain.repository.user

import com.paw.key.domain.entity.user.PetBreedsEntity
import com.paw.key.domain.entity.user.UserInfoEntity
import com.paw.key.domain.entity.user.UserInfoResultEntity

interface UserRepository {
    suspend fun createUser(
        userInfoEntity: UserInfoEntity
    ): Result<UserInfoResultEntity>

    suspend fun getPetBreeds(): Result<PetBreedsEntity>
}
