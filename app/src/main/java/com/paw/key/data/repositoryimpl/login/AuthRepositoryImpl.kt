package com.paw.key.data.repositoryimpl.login

import android.content.Context
import com.paw.key.core.util.UserDataStore
import com.paw.key.core.util.suspendRunCatching
import com.paw.key.data.dto.response.LoginResponseDto
import com.paw.key.data.remote.datasource.login.AuthRemoteDataSource
import com.paw.key.data.remote.datasource.login.GoogleAuthDataSource
import com.paw.key.data.remote.datasource.login.KakaoAuthDataSource
import com.paw.key.domain.repository.login.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val googleAuthDataSource: GoogleAuthDataSource,
    private val kakaoAuthDataSource: KakaoAuthDataSource,
    @ApplicationContext private val context: Context,
) : AuthRepository {
    override suspend fun signInWithGoogle(context: Context): Result<String> =
        googleAuthDataSource.signIn(context).map { it.idToken }

    override suspend fun signInWithKakao(context: Context): Result<String> =
        kakaoAuthDataSource.signIn(context)

    override suspend fun login(idToken: String, deviceId: String): Result<LoginResponseDto> =
        suspendRunCatching {
            authRemoteDataSource.login(idToken, deviceId)
        }

    override suspend fun loginKakao(idToken: String, deviceId: String): Result<LoginResponseDto> =
        suspendRunCatching {
            authRemoteDataSource.loginKakao(idToken, deviceId)
        }

    override suspend fun refreshToken(): Result<Boolean> = suspendRunCatching {
        val currentRefreshToken = UserDataStore.getRefreshToken(context)
        val deviceId = getDeviceId()

        val response = authRemoteDataSource.refreshToken(currentRefreshToken, deviceId)

        UserDataStore.saveAcessToken(context, response.accessToken)
        UserDataStore.saveRefreshToken(context, response.refreshToken)
        true
    }

    override suspend fun logout(): Result<Boolean> = suspendRunCatching {
        val deviceId = getDeviceId()

        authRemoteDataSource.logout(deviceId)
        UserDataStore.removeToken(context)

        true
    }


    private suspend fun saveTokens(loginResponse: LoginResponseDto) {
        UserDataStore.saveAcessToken(context, loginResponse.accessToken)
        UserDataStore.saveRefreshToken(context, loginResponse.refreshToken)
        UserDataStore.saveIsNewUser(context, loginResponse.isNewUser)
    }

    private fun getDeviceId(): String {
        return android.provider.Settings.Secure.getString(
            context.contentResolver,
            android.provider.Settings.Secure.ANDROID_ID
        )
    }
}