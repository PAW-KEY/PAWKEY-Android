package com.paw.key.data.remote.datasource.datasourceimpl

import com.paw.key.data.dto.request.LoginRequestDto
import com.paw.key.data.dto.response.LoginResponseDto
import com.paw.key.data.remote.datasource.login.AuthRemoteDataSource
import com.paw.key.data.service.login.LoginService
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val loginService: LoginService,
) : AuthRemoteDataSource {
    override suspend fun login(idToken: String, deviceId: String): LoginResponseDto {
        return loginService.login(
            LoginRequestDto(
                idToken = idToken,
                deviceId = deviceId
            )
        )
    }

    override suspend fun loginKakao(idToken: String, deviceId: String): LoginResponseDto {
        return loginService.loginKakao(
            LoginRequestDto(
                idToken = idToken,
                deviceId = deviceId
            )
        )
    }
}