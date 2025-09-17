package com.paw.key.domain.repository.login

import android.content.Context
import com.paw.key.data.dto.response.LoginResponseDto

interface AuthRepository {
    suspend fun signInWithGoogle(context: Context): Result<String>
    suspend fun login(providerToken: String, provider: String): Result<LoginResponseDto>
}