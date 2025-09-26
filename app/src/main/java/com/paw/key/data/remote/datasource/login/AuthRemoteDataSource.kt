package com.paw.key.data.remote.datasource.login

import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.LoginResponseDto

interface AuthRemoteDataSource {
    suspend fun login(providerToken: String, provider: String): BaseResponse<LoginResponseDto>
}