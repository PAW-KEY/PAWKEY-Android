package com.paw.key.data.repositoryimpl.localstorage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.paw.key.domain.repository.localstorage.LocalStorageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalStorageRepository {
    private val sharedPreferences: SharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString(ACCESS_TOKEN, accessToken)
            putString(REFRESH_TOKEN, refreshToken)
            apply()
        }
    }

    override suspend fun getAccessToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
    }

    override suspend fun getRefreshToken(): String {
        return sharedPreferences.getString(REFRESH_TOKEN, "") ?: ""
    }

    override suspend fun removeTokens() {
        sharedPreferences.edit().apply {
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
            apply()
        }
    }

    override suspend fun saveUserId(userId: Int) {
        sharedPreferences.edit().apply {
            putInt(USER_ID, userId)
            apply()
        }
    }

    override suspend fun getUserId(): Int {
        return sharedPreferences.getInt(USER_ID, -1)
    }

    override suspend fun savePetId(petId: Int) {
        sharedPreferences.edit().apply {
            putInt(PET_ID, petId)
            apply()
        }
    }

    override suspend fun getPetId(): Int {
        return sharedPreferences.getInt(PET_ID, -1)
    }

    override suspend fun saveDeviceId(deviceId: String) {
        sharedPreferences.edit().apply {
            putString(DEVICE_ID, deviceId)
            apply()
        }
    }

    override suspend fun getDeviceId(): String {
        val savedId = sharedPreferences.getString(DEVICE_ID, null)
        if (!savedId.isNullOrEmpty()) {
            return savedId
        }

        // ID가 없으면 새로 생성해서 저장
        val newId = UUID.randomUUID().toString()
        sharedPreferences.edit().apply {
            putString(DEVICE_ID, newId)
            apply()
        }
        return newId
    }

    override suspend fun clearInfo() {
        sharedPreferences.edit().apply {
            remove(USER_ID)
            remove(PET_ID)
            remove(DEVICE_ID)
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
            apply()
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "user_preferences"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val DEVICE_ID = "device_id"
        private const val USER_ID = "user_id"
        private const val PET_ID = "pet_id"
    }
}