package com.paw.key.data.local.datasourceimpl

import android.content.Context
import com.paw.key.core.util.UserDataStore
import com.paw.key.data.local.datasource.UserLocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserLocalDataSource {

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        UserDataStore.saveAcessToken(context, accessToken)
        UserDataStore.saveRefreshToken(context, refreshToken)
    }
}