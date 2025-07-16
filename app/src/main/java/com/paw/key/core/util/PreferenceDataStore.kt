package com.paw.key.core.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kakao.vectormap.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.summaryStore by preferencesDataStore(name = "summaryStore_pref")

private val POINTS_KEY = stringPreferencesKey("points")
private val TOTAL_DISTANCE_KEY = floatPreferencesKey("total_distance")
private val TOTAL_TIME_KEY = longPreferencesKey("total_time")
private val TOTAL_STEPS_KEY = intPreferencesKey("total_steps")

private val LOGIN_EMAIL_KEY = stringPreferencesKey("login_email")
private val LOGIN_PASSWORD_KEY = stringPreferencesKey("login_password")
private val USER_ID_KEY = intPreferencesKey("user_id")
private val USER_NAME_KEY = stringPreferencesKey("user_name")
private val PET_ID_KEY = intPreferencesKey("pet_id")
private val PET_NAME_KEY = stringPreferencesKey("pet_name")

private fun List<LatLng>.toPreferenceString(): String =
    joinToString(";") { "${it.latitude},${it.longitude}" }

private fun String.toLatLngList(): List<LatLng> =
    split(";").mapNotNull {
        val parts = it.split(",")
        if (parts.size == 2) {
            LatLng.from(parts[0].toDoubleOrNull() ?: return@mapNotNull null, parts[1].toDoubleOrNull() ?: return@mapNotNull null)
        }
        else null
    }

object PreferenceDataStore {

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val summaryStore
        get() = appContext.summaryStore


    suspend fun saveWalkSummary(
        points: List<LatLng>,
        totalDistance: Float,
        totalTime: Long,
        totalSteps: Int,
    ) {
        summaryStore.edit { preferences ->
            preferences[POINTS_KEY] = points.toPreferenceString()
            preferences[TOTAL_DISTANCE_KEY] = totalDistance
            preferences[TOTAL_TIME_KEY] = totalTime
            preferences[TOTAL_STEPS_KEY] = totalSteps
        }
    }

    fun getPoints(): Flow<List<LatLng>> = summaryStore.data.map {
        it[POINTS_KEY]?.toLatLngList() ?: emptyList()
    }

    fun getTotalDistance(): Flow<Float> = summaryStore.data.map {
        it[TOTAL_DISTANCE_KEY] ?: 0f
    }

    fun getTotalTime(): Flow<Long> = summaryStore.data.map {
        it[TOTAL_TIME_KEY] ?: 0L
    }

    fun getTotalSteps(): Flow<Int> = summaryStore.data.map {
        it[TOTAL_STEPS_KEY] ?: 0
    }

    suspend fun clearWalkSummary() {
        summaryStore.edit {
            it.remove(POINTS_KEY)
            it.remove(TOTAL_DISTANCE_KEY)
            it.remove(TOTAL_TIME_KEY)
            it.remove(TOTAL_STEPS_KEY)
        }
    }

    suspend fun saveLoginInfo(email: String, password: String) {
        summaryStore.edit {
            it[LOGIN_EMAIL_KEY] = email
            it[LOGIN_PASSWORD_KEY] = password
        }
    }

    fun getLoginEmail(): Flow<String> = summaryStore.data.map {
        it[LOGIN_EMAIL_KEY] ?: ""
    }

    fun getLoginPassword(): Flow<String> = summaryStore.data.map {
        it[LOGIN_PASSWORD_KEY] ?: ""
    }

    data class LoginInfo(val email: String, val password: String)

    fun getLoginInfo(): Flow<LoginInfo> = summaryStore.data.map {
        LoginInfo(
            email = it[LOGIN_EMAIL_KEY] ?: "",
            password = it[LOGIN_PASSWORD_KEY] ?: ""
        )
    }

    suspend fun clearLoginInfo() {
        summaryStore.edit {
            it.remove(LOGIN_EMAIL_KEY)
            it.remove(LOGIN_PASSWORD_KEY)
        }
    }

    suspend fun saveUserInfo(
        userId: Int,
        userName: String,
        petId: Int,
        petName: String
    ) {
        summaryStore.edit {
            it[USER_ID_KEY] = userId
            it[USER_NAME_KEY] = userName
            it[PET_ID_KEY] = petId
            it[PET_NAME_KEY] = petName
        }
    }

    fun getUserId(): Flow<Int> = summaryStore.data.map {
        it[USER_ID_KEY] ?: 0
    }

    fun getUserName(): Flow<String> = summaryStore.data.map {
        it[USER_NAME_KEY] ?: ""
    }

    fun getPetId(): Flow<Int> = summaryStore.data.map {
        it[PET_ID_KEY] ?: 0
    }

    fun getPetName(): Flow<String> = summaryStore.data.map {
        it[PET_NAME_KEY] ?: ""
    }

    data class UserInfo(
        val userId: Int,
        val userName: String,
        val petId: Int,
        val petName: String
    )

    fun getUserInfo(): Flow<UserInfo> = summaryStore.data.map {
        UserInfo(
            userId = it[USER_ID_KEY] ?: 0,
            userName = it[USER_NAME_KEY] ?: "",
            petId = it[PET_ID_KEY] ?: 0,
            petName = it[PET_NAME_KEY] ?: ""
        )
    }

    suspend fun clearUserInfo() {
        summaryStore.edit {
            it.remove(USER_ID_KEY)
            it.remove(USER_NAME_KEY)
            it.remove(PET_ID_KEY)
            it.remove(PET_NAME_KEY)
        }
    }

    suspend fun clearAllData() {
        summaryStore.edit { it.clear() }
    }
}