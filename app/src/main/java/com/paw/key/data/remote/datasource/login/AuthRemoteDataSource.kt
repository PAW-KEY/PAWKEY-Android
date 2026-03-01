package com.paw.key.data.remote.datasource.login

import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.LoginResponseDto
import com.paw.key.data.dto.response.RefreshTokenResponseDto

interface AuthRemoteDataSource {
    suspend fun login(idToken: String, deviceId: String): LoginResponseDto

    suspend fun loginKakao(idToken: String, deviceId: String): LoginResponseDto

    suspend fun refreshToken(refreshToken: String, deviceId: String): RefreshTokenResponseDto

    suspend fun logout(deviceId: String): BaseResponse<Unit>
}