package com.paw.key.domain.repository.login

import android.content.Context
import com.paw.key.data.dto.response.LoginResponseDto

interface AuthRepository {
    suspend fun signInWithGoogle(context: Context): Result<String>
    suspend fun signInWithKakao(context: Context): Result<String>
    suspend fun login(idToken: String, deviceId: String): Result<LoginResponseDto>
    suspend fun loginKakao(idToken: String, deviceId: String): Result<LoginResponseDto>
    suspend fun refreshToken(): Result<Boolean>
    suspend fun logout(): Result<Boolean>
}