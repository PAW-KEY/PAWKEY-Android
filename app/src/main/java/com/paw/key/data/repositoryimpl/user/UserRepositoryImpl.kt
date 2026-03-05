package com.paw.key.data.repositoryimpl.user

import com.paw.key.core.util.suspendRunCatching
import com.paw.key.data.dto.request.user.UserWithDrawRequestDto
import com.paw.key.data.dto.request.user.toDto
import com.paw.key.data.remote.datasource.user.UserDataSource
import com.paw.key.domain.entity.petprofile.PetProfileEntity
import com.paw.key.domain.entity.user.PetBreedsEntity
import com.paw.key.domain.entity.user.UserInfoEntity
import com.paw.key.domain.entity.user.UserInfoResultEntity
import com.paw.key.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun createUser(userInfoEntity: UserInfoEntity): Result<UserInfoResultEntity> =
        suspendRunCatching {
            userDataSource.createUser(
                dto = userInfoEntity.toDto()
            ).data.toEntity()
        }

    override suspend fun deleteUser(provider: String): Result<Unit> =
        suspendRunCatching {
            userDataSource.deleteUser(
                dto = UserWithDrawRequestDto(
                    provider = provider
                )
            ).data
        }

    override suspend fun getPetBreeds(): Result<PetBreedsEntity> =
        suspendRunCatching {
            userDataSource.getPetBreeds().data.toEntity()
        }

    override suspend fun getPetProfiles(petId: Int): Result<PetProfileEntity> =
        suspendRunCatching {
            userDataSource.getPetProfiles(petId).data.toEntity()
        }

}