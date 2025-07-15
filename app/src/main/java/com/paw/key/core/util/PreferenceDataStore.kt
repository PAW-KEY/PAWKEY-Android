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
    suspend fun saveWalkSummary(
        context: Context,
        points: List<LatLng>,
        totalDistance: Float,
        totalTime: Long,
        totalSteps: Int,
    ) {
        context.summaryStore.edit { preferences ->
            preferences[POINTS_KEY] = points.toPreferenceString()
            preferences[TOTAL_DISTANCE_KEY] = totalDistance
            preferences[TOTAL_TIME_KEY] = totalTime
            preferences[TOTAL_STEPS_KEY] = totalSteps
        }
    }

    fun getPoints(context: Context): Flow<List<LatLng>> {
        return context.summaryStore.data.map { preferences ->
            preferences[POINTS_KEY]?.toLatLngList() ?: emptyList()
        }
    }

    fun getTotalDistance(context: Context): Flow<Float> {
        return context.summaryStore.data.map { preferences ->
            preferences[TOTAL_DISTANCE_KEY] ?: 0f
        }
    }

    fun getTotalTime(context: Context): Flow<Long> {
        return context.summaryStore.data.map { preferences ->
            preferences[TOTAL_TIME_KEY] ?: 0L
        }
    }

    fun getTotalSteps(context: Context): Flow<Int> {
        return context.summaryStore.data.map { preferences ->
            preferences[TOTAL_STEPS_KEY] ?: 0
        }
    }

    suspend fun clearWalkSummary(context: Context) {
        context.summaryStore.edit { preferences ->
            preferences.remove(POINTS_KEY)
            preferences.remove(TOTAL_DISTANCE_KEY)
            preferences.remove(TOTAL_TIME_KEY)
            preferences.remove(TOTAL_STEPS_KEY)
        }
    }

    suspend fun saveLoginInfo(
        context: Context,
        email: String,
        password: String
    ) {
        context.summaryStore.edit { preferences ->
            preferences[LOGIN_EMAIL_KEY] = email
            preferences[LOGIN_PASSWORD_KEY] = password
        }
    }

    fun getLoginEmail(context: Context): Flow<String> {
        return context.summaryStore.data.map { preferences ->
            preferences[LOGIN_EMAIL_KEY] ?: ""
        }
    }

    fun getLoginPassword(context: Context): Flow<String> {
        return context.summaryStore.data.map { preferences ->
            preferences[LOGIN_PASSWORD_KEY] ?: ""
        }
    }

    data class LoginInfo(
        val email: String,
        val password: String
    )

    fun getLoginInfo(context: Context): Flow<LoginInfo> {
        return context.summaryStore.data.map { preferences ->
            LoginInfo(
                email = preferences[LOGIN_EMAIL_KEY] ?: "",
                password = preferences[LOGIN_PASSWORD_KEY] ?: ""
            )
        }
    }

    suspend fun clearLoginInfo(context: Context) {
        context.summaryStore.edit { preferences ->
            preferences.remove(LOGIN_EMAIL_KEY)
            preferences.remove(LOGIN_PASSWORD_KEY)
        }
    }

    suspend fun saveUserInfo(
        context: Context,
        userId: Int,
        userName: String,
        petId: Int,
        petName: String
    ) {
        context.summaryStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
            preferences[USER_NAME_KEY] = userName
            preferences[PET_ID_KEY] = petId
            preferences[PET_NAME_KEY] = petName
        }
    }

    fun getUserId(context: Context): Flow<Int> {
        return context.summaryStore.data.map { preferences ->
            preferences[USER_ID_KEY] ?: 0
        }
    }

    fun getUserName(context: Context): Flow<String> {
        return context.summaryStore.data.map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }
    }

    fun getPetId(context: Context): Flow<Int> {
        return context.summaryStore.data.map { preferences ->
            preferences[PET_ID_KEY] ?: 0
        }
    }

    fun getPetName(context: Context): Flow<String> {
        return context.summaryStore.data.map { preferences ->
            preferences[PET_NAME_KEY] ?: ""
        }
    }

    data class UserInfo(
        val userId: Int,
        val userName: String,
        val petId: Int,
        val petName: String
    )

    fun getUserInfo(context: Context): Flow<UserInfo> {
        return context.summaryStore.data.map { preferences ->
            UserInfo(
                userId = preferences[USER_ID_KEY] ?: 0,
                userName = preferences[USER_NAME_KEY] ?: "",
                petId = preferences[PET_ID_KEY] ?: 0,
                petName = preferences[PET_NAME_KEY] ?: ""
            )
        }
    }

    suspend fun clearUserInfo(context: Context) {
        context.summaryStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
            preferences.remove(USER_NAME_KEY)
            preferences.remove(PET_ID_KEY)
            preferences.remove(PET_NAME_KEY)
        }
    }

    suspend fun clearAllData(context: Context) {
        context.summaryStore.edit { preferences ->
            preferences.clear()
        }
    }
}