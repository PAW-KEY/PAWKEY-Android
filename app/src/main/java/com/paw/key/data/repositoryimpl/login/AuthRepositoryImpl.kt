package com.paw.key.data.repositoryimpl.login

import android.content.Context
import com.paw.key.core.util.suspendRunCatching
import com.paw.key.data.dto.response.LoginResponseDto
import com.paw.key.data.local.datasource.UserLocalDataSource
import com.paw.key.data.remote.datasource.login.AuthRemoteDataSource
import com.paw.key.data.remote.datasource.login.GoogleAuthDataSource
import com.paw.key.domain.repository.login.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val googleAuthDataSource: GoogleAuthDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : AuthRepository {

    override suspend fun signInWithGoogle(context: Context): Result<String> =
        googleAuthDataSource.signIn(context).map { it.idToken }

    override suspend fun login(providerToken: String, provider: String): Result<LoginResponseDto> =
        suspendRunCatching {
            val loginResponse = authRemoteDataSource.login(providerToken, provider).data

            userLocalDataSource.saveTokens(
                accessToken = loginResponse.AccessToken,
                refreshToken = loginResponse.RefreshToken
            )

            loginResponse
        }
}