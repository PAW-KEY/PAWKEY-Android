package com.paw.key.domain.repository.filter

import com.paw.key.domain.model.entity.filter.FilterEntity


interface FilterOptionRepository {
    suspend fun getFilterOptions(userId: Int): Result<FilterEntity>
}