package com.paw.key.data.di

import com.paw.key.domain.repository.localstorage.LocalStorageRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val localStorageRepository: LocalStorageRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            localStorageRepository.getAccessToken()
        }

        val newRequest = chain.request().newBuilder().apply {
            header("Authorization", "Bearer $token")
        }.build()

        return chain.proceed(newRequest)
    }
}