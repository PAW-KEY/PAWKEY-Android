package com.paw.key.data.remote.datasource.datasourceimpl

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.paw.key.core.util.suspendRunCatching
import com.paw.key.data.remote.datasource.login.KakaoAuthDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class KakaoAuthDataSourceImpl @Inject constructor() : KakaoAuthDataSource {
    override suspend fun signIn(context: Context): Result<String> = suspendRunCatching {
        val accessToken = getKakaoAccessToken(context)
        accessToken
    }

    private suspend fun getKakaoAccessToken(context: Context): String =
        suspendCancellableCoroutine { continuation ->
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                when {
                    error != null -> {
                        continuation.resumeWithException(error)
                    }
                    token != null -> {
                        continuation.resume(token.accessToken)
                    }
                    else -> {
                        continuation.resumeWithException(
                            IllegalStateException("Token and error are both null")
                        )
                    }
                }
            }

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        continuation.resumeWithException(error)
                        return@loginWithKakaoTalk
                    }
                    when {
                        token != null -> callback(token, null)
                        error != null -> {
                            UserApiClient.instance.loginWithKakaoAccount(
                                context,
                                callback = callback
                            )
                        }
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }

}