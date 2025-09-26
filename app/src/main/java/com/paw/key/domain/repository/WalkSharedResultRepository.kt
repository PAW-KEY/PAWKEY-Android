package com.paw.key.domain.repository

import android.graphics.Bitmap
import com.naver.maps.geometry.LatLng
import com.paw.key.domain.model.entity.sharedresult.WalkResult
import kotlinx.coroutines.flow.Flow

interface WalkSharedResultRepository {
    suspend fun saveResult(bitmap: Bitmap?, totalTime: Long, distance: Float, steps: Int, points : List<LatLng>)
    fun getResult(): Flow<WalkResult?>
    fun clear()
}