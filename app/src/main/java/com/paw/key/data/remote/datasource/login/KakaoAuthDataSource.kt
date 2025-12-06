package com.paw.key.data.remote.datasource.login

import android.content.Context

interface KakaoAuthDataSource {
    suspend fun signIn(context: Context): Result<String>
}