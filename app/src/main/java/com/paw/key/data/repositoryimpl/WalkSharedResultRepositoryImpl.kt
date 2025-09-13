package com.paw.key.data.repositoryimpl

import android.graphics.Bitmap
import android.util.Log
import com.naver.maps.geometry.LatLng
import com.paw.key.domain.model.entity.sharedresult.WalkResult
import com.paw.key.domain.repository.WalkSharedResultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalkSharedResultRepositoryImpl @Inject constructor(
) : WalkSharedResultRepository {
    private val _walkResult = MutableStateFlow<WalkResult?>(null)

    override suspend fun saveResult(
        bitmap: Bitmap?,
        totalTime: Long,
        distance: Float,
        steps: Int,
        points: List<LatLng>
    ) {
        _walkResult.value = WalkResult(bitmap, totalTime, distance, steps, points)
        Log.d("WalkSharedResultRepositoryImpl", "Result saved: $bitmap")
        Log.d("WalkSharedResultRepositoryImpl", "Result saved: $totalTime")
        Log.d("WalkSharedResultRepositoryImpl", "Result saved: $distance")
        Log.d("WalkSharedResultRepositoryImpl", "Result saved: $steps")
    }

    override fun getResult(): Flow<WalkResult?> = _walkResult.asStateFlow()

    override fun clear() {
        _walkResult.value = null
    }
}
