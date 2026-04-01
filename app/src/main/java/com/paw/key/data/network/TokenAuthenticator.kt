package com.paw.key.data.network

import com.paw.key.core.app.AppRestarter
import com.paw.key.domain.repository.localstorage.LocalStorageRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val tokenManager: LocalStorageRepository,
    private val appRestarter: AppRestarter,
    private val refreshService: TokenRefreshService
) : Authenticator {
    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? = runBlocking {
        mutex.withLock {
            val currentToken = tokenManager.getAccessToken()

            // 이미 갱신되었는지 확인
            val reqToken = response.request.header("Authorization")?.substringAfter("Bearer ")
            if (reqToken != currentToken && currentToken.isNotEmpty()) {
                Timber.d("토큰 이미 갱신됨. 새 토큰으로 재시도: Bearer $currentToken")
                return@withLock response.request.newBuilder()
                    .header("Authorization", "Bearer $currentToken")
                    .build()
            }

            val refreshToken = tokenManager.getRefreshToken()
            val deviceId = tokenManager.getDeviceId()
            if (refreshToken.isEmpty()) {
                appRestarter.restartApp()
                return@withLock null
            }

            try {
                val tokenDto = refreshService.refresh(refreshToken, deviceId).getOrNull()
                    ?: run {
                        tokenManager.clearInfo()
                        appRestarter.restartApp()
                        return@withLock null
                    }

                val (accessToken, refreshToken) = tokenDto

                tokenManager.saveTokens(accessToken.value, refreshToken.value)

                return@withLock response.request.newBuilder()
                    .header("Authorization", "Bearer ${accessToken.value}")
                    .build()
            } catch (e: Exception) {
                Timber.e(e, "토큰 갱신 실패 - 앱 재시작")
                tokenManager.clearInfo()
                appRestarter.restartApp()
                return@withLock null
            }
        }
    }
}
