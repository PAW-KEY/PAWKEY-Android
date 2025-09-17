package com.paw.key.data.remote.datasource.login

import android.content.Context
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.LoginResponseDto

interface GoogleAuthDataSource {
    suspend fun signIn(context: Context): Result<GoogleIdTokenCredential>
}

interface AuthRemoteDataSource {
    suspend fun login(providerToken: String, provider: String): BaseResponse<LoginResponseDto>
}