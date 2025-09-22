package com.paw.key.data.remote.datasource.datasourceimpl

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.paw.key.BuildConfig
import com.paw.key.core.util.suspendRunCatching
import com.paw.key.data.dto.request.LoginRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.LoginResponseDto
import com.paw.key.data.remote.datasource.login.AuthRemoteDataSource
import com.paw.key.data.remote.datasource.login.GoogleAuthDataSource
import com.paw.key.data.service.login.LoginService
import javax.inject.Inject

class GoogleAuthDataSourceImpl @Inject constructor(
    private val credentialManager: CredentialManager,
) : GoogleAuthDataSource {
    override suspend fun signIn(context: Context): Result<GoogleIdTokenCredential> =
        suspendRunCatching {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(false)
                .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val response = credentialManager.getCredential(context, request)
            GoogleIdTokenCredential.createFrom(response.credential.data)
        }
}
