package com.paw.key.data.remote.datasource.login

import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.LoginResponseDto

interface AuthRemoteDataSource {
    suspend fun login(idToken: String, deviceId: String): LoginResponseDto

    suspend fun loginKakao(idToken: String, deviceId: String): LoginResponseDto
}