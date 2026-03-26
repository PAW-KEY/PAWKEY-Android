package com.paw.key.data.network

import com.paw.key.core.model.AccessToken
import com.paw.key.core.model.RefreshToken
import com.paw.key.core.util.suspendRunCatching
import com.paw.key.data.dto.request.auth.AuthReissueRequestDto
import com.paw.key.data.service.auth.ReissueService
import com.paw.key.domain.repository.localstorage.LocalStorageRepository
import javax.inject.Inject

class TokenRefreshServiceImpl @Inject constructor(
    private val reissueService: ReissueService,
    private val tokenManager: LocalStorageRepository
) : TokenRefreshService {
    override suspend fun refresh(
        refreshToken: String,
        deviceId: String
    ): Result<Pair<AccessToken, RefreshToken>> = suspendRunCatching {
        val response = reissueService.reissueToken(
            body = AuthReissueRequestDto(
                refreshToken = refreshToken,
                deviceId = deviceId
            )
        )

        val data = response

        tokenManager.saveTokens(data.accessToken, data.refreshToken)

        Pair(
            AccessToken(data.accessToken),
            RefreshToken(data.refreshToken)
        )
    }
}
