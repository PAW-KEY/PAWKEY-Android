package com.paw.key.data.local.datasource

interface UserLocalDataSource {
    suspend fun saveTokens(accessToken: String, refreshToken: String)
}