package com.paw.key.data.repositoryimpl.filter

import com.paw.key.data.remote.datasource.filter.FilterOptionDataSource
import com.paw.key.domain.model.entity.filter.Category
import com.paw.key.domain.model.entity.filter.CategoryOption
import com.paw.key.domain.model.entity.filter.FilterEntity
import com.paw.key.domain.repository.filter.FilterOptionRepository
import javax.inject.Inject

class FilterOptionRepositoryImpl @Inject constructor(
    private val dataSource: FilterOptionDataSource
) : FilterOptionRepository {

    override suspend fun getFilterOptions(userId: Int): Result<FilterEntity> = runCatching {
        dataSource.getFilterOptions(userId).toEntity()
    }
}