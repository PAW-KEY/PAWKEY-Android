package com.paw.key.data.network

import com.paw.key.core.model.AccessToken
import com.paw.key.core.model.RefreshToken


interface TokenRefreshService {
    suspend fun refresh(refreshToken: String, deviceId: String): Result<Pair<AccessToken, RefreshToken>>
}
