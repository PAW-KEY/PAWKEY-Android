package com.paw.key.data.repositoryimpl

import com.paw.key.data.remote.datasource.PetProfileDataSource
import com.paw.key.domain.model.entity.petprofile.PetProfileEntity
import com.paw.key.domain.repository.petprofile.PetProfileRepository
import javax.inject.Inject

class PetProfileRepositoryImpl @Inject constructor(
    private val dataSource: PetProfileDataSource,
) : PetProfileRepository {

    override suspend fun getPetProfiles(userId: Int): Result<List<PetProfileEntity>> = runCatching {
        dataSource.getPetProfiles(userId).data.map { it.toEntity() }
    }
}