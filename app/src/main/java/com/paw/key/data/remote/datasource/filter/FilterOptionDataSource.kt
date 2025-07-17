package com.paw.key.data.remote.datasource.filter

import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.filter.FilterOptionResponse
import com.paw.key.data.service.filter.FilterOptionService
import javax.inject.Inject


class FilterOptionDataSource @Inject constructor(
    private val filterOptionService: FilterOptionService
) {
    suspend fun getFilterOptions(userId: Int) = filterOptionService.getFilterOptions(userId).data
}
