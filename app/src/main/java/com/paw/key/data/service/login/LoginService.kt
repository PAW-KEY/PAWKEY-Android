package com.paw.key.data.service.login

import com.paw.key.data.dto.request.LoginRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface LoginService {
    @POST("api/v1/auth/login")
    suspend fun login(
        @Header("Authorization") providerToken: String,
        @Body loginRequestDto: LoginRequestDto
    ): BaseResponse<LoginResponseDto>
}