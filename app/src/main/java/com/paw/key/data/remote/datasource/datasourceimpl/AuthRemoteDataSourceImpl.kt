package com.paw.key.data.remote.datasource.datasourceimpl

import com.paw.key.data.dto.request.LoginRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.LoginResponseDto
import com.paw.key.data.remote.datasource.login.AuthRemoteDataSource
import com.paw.key.data.service.login.LoginService
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val loginService: LoginService,
) : AuthRemoteDataSource {
    override suspend fun login(
        providerToken: String,
        provider: String,
    ): BaseResponse<LoginResponseDto> =
        loginService.login(providerToken, LoginRequestDto(provider))
}
