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
            preferences.clear() // 모든 데이터 삭제
        }
    }
}