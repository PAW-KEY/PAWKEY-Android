package com.paw.key.domain.repository.filter

import com.paw.key.domain.entity.filter.FilterEntity


interface FilterOptionRepository {
    suspend fun getFilterOptions(userId: Int): Result<FilterEntity>
}