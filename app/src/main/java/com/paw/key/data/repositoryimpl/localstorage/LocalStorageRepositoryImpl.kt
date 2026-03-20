package com.paw.key.data.repositoryimpl.localstorage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.paw.key.domain.repository.localstorage.LocalStorageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalStorageRepository {
    private val sharedPreferences: SharedPreferences by lazy {
        createEncryptedSharedPreferences() ?: recreateAndCreate()
    }

    private fun buildMasterKey(): MasterKey {
        return MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private fun createEncryptedSharedPreferences(): SharedPreferences? {
        return try {
            EncryptedSharedPreferences.create(
                context,
                PREFERENCES_NAME,
                buildMasterKey(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            // 복호화 실패 (키 불일치, 재설치, 백업 복원 등)
            null
        }
    }

    // 손상된 SharedPreferences 파일 삭제
    private fun recreateAndCreate(): SharedPreferences {
        try {
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit(commit = true) { clear() }

            val prefsFile = File(
                context.filesDir.parent,
                "shared_prefs/$PREFERENCES_NAME.xml"
            )
            if (prefsFile.exists()) prefsFile.delete()
        } catch (e: Exception) {
            // 삭제 실패해도 계속 진행
        }

        // 파일 삭제 후 재생성
        return createEncryptedSharedPreferences()
            ?: throw IllegalStateException("EncryptedSharedPreferences 생성에 실패했습니다.")
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString(ACCESS_TOKEN, accessToken)
            putString(REFRESH_TOKEN, refreshToken)
            apply()
        }
    }

    override suspend fun getAccessToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN, "").orEmpty()
    }

    override suspend fun getRefreshToken(): String {
        return sharedPreferences.getString(REFRESH_TOKEN, "").orEmpty()
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

    override suspend fun saveUserProvider(provider: String) {
        sharedPreferences.edit().apply {
            putString(USER_PROVIDER, provider)
            apply()
        }
    }

    override suspend fun getUserProvider(): String {
        return sharedPreferences
            .getString(USER_PROVIDER, "").orEmpty()
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
        private const val USER_PROVIDER = "user_provider"
    }
}