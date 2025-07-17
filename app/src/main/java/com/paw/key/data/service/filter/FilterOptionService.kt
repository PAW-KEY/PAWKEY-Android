package com.paw.key.data.service.filter

import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.filter.FilterOptionResponse
import retrofit2.http.GET
import retrofit2.http.Header


interface FilterOptionService {
    @GET ("posts/filter")
    suspend fun getFilterOptions(
        @Header("X-USER-ID") userId: Int,
    ): BaseResponse<FilterOptionResponse>
}