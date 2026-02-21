package com.paw.key.data.repositoryimpl.login

import android.content.Context
import com.paw.key.core.util.suspendRunCatching
import com.paw.key.data.dto.response.LoginResponseDto
import com.paw.key.data.remote.datasource.login.AuthRemoteDataSource
import com.paw.key.data.remote.datasource.login.GoogleAuthDataSource
import com.paw.key.data.remote.datasource.login.KakaoAuthDataSource
import com.paw.key.domain.repository.login.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val googleAuthDataSource: GoogleAuthDataSource,
    private val kakaoAuthDataSource: KakaoAuthDataSource,
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
}