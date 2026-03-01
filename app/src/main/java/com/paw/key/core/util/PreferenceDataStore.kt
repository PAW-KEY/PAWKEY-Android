package com.paw.key.core.util

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.naver.maps.geometry.LatLng
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

// 위치 정보를 위한 새로운 키들
private val SELECTED_GU_ID_KEY = intPreferencesKey("selected_gu_id")
private val SELECTED_DONG_ID_KEY = intPreferencesKey("selected_dong_id")
private val SELECTED_GU_NAME_KEY = stringPreferencesKey("selected_gu_name")
private val SELECTED_DONG_NAME_KEY = stringPreferencesKey("selected_dong_name")
private val ACTIVE_REGION_KEY = stringPreferencesKey("active_region")

private fun List<LatLng>.toPreferenceString(): String =
    joinToString(";") { "${it.latitude},${it.longitude}" }

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
        petName: String,
    ) {
        summaryStore.edit {
            it[USER_ID_KEY] = userId
            it[USER_NAME_KEY] = userName
            it[PET_ID_KEY] = petId
            it[PET_NAME_KEY] = petName
        }
    }

    fun getUserId(): Flow<Int> = summaryStore.data.map {
        it[USER_ID_KEY] ?: 43
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
        val petName: String,
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

    // ===== 위치 정보 관련 새로운 함수들 =====

    /**
     * 선택된 위치 정보를 저장합니다
     */
    suspend fun saveLocationInfo(
        guId: Int,
        dongId: Int,
        guName: String,
        dongName: String,
    ) {
        summaryStore.edit { preferences ->
            preferences[SELECTED_GU_ID_KEY] = guId
            preferences[SELECTED_DONG_ID_KEY] = dongId
            preferences[SELECTED_GU_NAME_KEY] = guName
            preferences[SELECTED_DONG_NAME_KEY] = dongName
        }
    }

    /**
     * 구 정보만 저장합니다 (동 선택 전)
     */
    suspend fun saveGuInfo(guId: Int, guName: String) {
        summaryStore.edit { preferences ->
            preferences[SELECTED_GU_ID_KEY] = guId
            preferences[SELECTED_GU_NAME_KEY] = guName
            // 구가 변경되면 기존 동 정보 초기화
            preferences.remove(SELECTED_DONG_ID_KEY)
            preferences.remove(SELECTED_DONG_NAME_KEY)
        }
    }

    /**
     * 동 정보만 저장합니다
     */
    suspend fun saveDongInfo(dongId: Int, dongName: String) {
        summaryStore.edit { preferences ->
            preferences[SELECTED_DONG_ID_KEY] = dongId
            preferences[SELECTED_DONG_NAME_KEY] = dongName
        }
    }

    /**
     * 활동 지역 정보를 저장합니다 (activeRegion)
     */
    suspend fun saveActiveRegion(activeRegion: String) {
        summaryStore.edit { preferences ->
            preferences[ACTIVE_REGION_KEY] = activeRegion
        }
    }

    // 개별 조회 함수들
    fun getSelectedGuId(): Flow<Int> = summaryStore.data.map {
        it[SELECTED_GU_ID_KEY] ?: 0
    }

    fun getSelectedDongId(): Flow<Int> = summaryStore.data.map {
        it[SELECTED_DONG_ID_KEY] ?: 0
    }

    fun getSelectedGuName(): Flow<String> = summaryStore.data.map {
        it[SELECTED_GU_NAME_KEY] ?: ""
    }

    fun getSelectedDongName(): Flow<String> = summaryStore.data.map {
        it[SELECTED_DONG_NAME_KEY] ?: ""
    }

    fun getActiveRegion(): Flow<String> = summaryStore.data.map {
        it[ACTIVE_REGION_KEY] ?: ""
    }

    // 위치 정보 통합 조회
    data class LocationInfo(
        val guId: Int,
        val dongId: Int,
        val guName: String,
        val dongName: String,
        val activeRegion: String,
    ) {
        val displayLocation: String
            get() = if (guName.isNotEmpty() && dongName.isNotEmpty()) {
                "$guName $dongName"
            } else if (guName.isNotEmpty()) {
                guName
            } else {
                "위치를 선택해주세요"
            }

        val isLocationSelected: Boolean
            get() = guId != 0 && dongId != 0
    }

    /**
     * 모든 위치 정보를 한번에 조회합니다
     */
    fun getLocationInfo(): Flow<LocationInfo> = summaryStore.data.map { preferences ->
        LocationInfo(
            guId = preferences[SELECTED_GU_ID_KEY] ?: 0,
            dongId = preferences[SELECTED_DONG_ID_KEY] ?: 0,
            guName = preferences[SELECTED_GU_NAME_KEY] ?: "",
            dongName = preferences[SELECTED_DONG_NAME_KEY] ?: "",
            activeRegion = preferences[ACTIVE_REGION_KEY] ?: ""
        )
    }

    /**
     * 위치 정보를 초기화합니다
     */
    suspend fun clearLocationInfo() {
        summaryStore.edit { preferences ->
            preferences.remove(SELECTED_GU_ID_KEY)
            preferences.remove(SELECTED_DONG_ID_KEY)
            preferences.remove(SELECTED_GU_NAME_KEY)
            preferences.remove(SELECTED_DONG_NAME_KEY)
            preferences.remove(ACTIVE_REGION_KEY)
        }
    }
}

object UserDataStore {
    private val ACCESS_TOKEN = "ACCESS_TOKEN"
    private val REFRESH_TOKEN = "REFRESH_TOKEN"
    private val IS_NEW_USER = "IS_NEW_USER"
    private val PREFERENCES_NAME = "user_preferences"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveAcessToken(context: Context, token: String) {
        val sharedPreferences = getSharedPreferences(context)
        with(sharedPreferences.edit()) {
            putString(ACCESS_TOKEN, token)
            commit()
        }
    }

    fun saveRefreshToken(context: Context, token: String) {
        val sharedPreferences = getSharedPreferences(context)
        with(sharedPreferences.edit()) {
            putString(REFRESH_TOKEN, token)
            commit()
        }
    }

    fun getAccessToken(context: Context): String {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
    }

    fun getRefreshToken(context: Context): String {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(REFRESH_TOKEN, "") ?: ""
    }

    fun removeToken(context: Context) {
        val sharedPreferences = getSharedPreferences(context)
        with(sharedPreferences.edit()) {
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
            remove(IS_NEW_USER)
            commit()
        }
    }

    fun saveIsNewUser(context: Context, isNewUser: Boolean) {
        val sharedPreferences = getSharedPreferences(context)
        with(sharedPreferences.edit()) {
            putBoolean(IS_NEW_USER, isNewUser)
            commit()
        }
    }

    fun getIsNewUser(context: Context): Boolean {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getBoolean(IS_NEW_USER, true)
    }
}


