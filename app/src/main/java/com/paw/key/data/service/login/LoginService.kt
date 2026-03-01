package com.paw.key.data.service.login

import com.paw.key.data.dto.request.LoginRequestDto
import com.paw.key.data.dto.request.LogoutRequestDto
import com.paw.key.data.dto.request.RefreshTokenRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.LoginResponseDto
import com.paw.key.data.dto.response.RefreshTokenResponseDto
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginService {
    @POST("auth/google/login")
    suspend fun login(
        @Body loginRequestDto: LoginRequestDto,
    ): LoginResponseDto

    @POST("auth/kakao/login")
    suspend fun loginKakao(
        @Body loginRequestDto: LoginRequestDto,
    ): LoginResponseDto

    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequestDto,
    ): RefreshTokenResponseDto


    @POST("auth/logout")
    suspend fun logout(
        @Body request: LogoutRequestDto,
    ): BaseResponse<Unit>
}