package com.paw.key.data.repositoryimpl.login

import android.content.Context
import com.paw.key.core.util.UserDataStore
import com.paw.key.core.util.suspendRunCatching
import com.paw.key.data.dto.response.LoginResponseDto
import com.paw.key.data.remote.datasource.login.AuthRemoteDataSource
import com.paw.key.data.remote.datasource.login.GoogleAuthDataSource
import com.paw.key.domain.repository.login.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val googleAuthDataSource: GoogleAuthDataSource,
    @ApplicationContext private val context: Context
) : AuthRepository {

    override suspend fun signInWithGoogle(context: Context): Result<String> =
        googleAuthDataSource.signIn(context).map { it.idToken }

    override suspend fun login(idToken: String, deviceId: String): Result<LoginResponseDto> =
        suspendRunCatching {

            val loginResponse = authRemoteDataSource.login(idToken, deviceId)

            UserDataStore.saveAcessToken(
                context = this.context,
                token = loginResponse.accessToken
            )
            UserDataStore.saveRefreshToken(
                context = this.context,
                token = loginResponse.refreshToken
            )

            loginResponse
        }
}