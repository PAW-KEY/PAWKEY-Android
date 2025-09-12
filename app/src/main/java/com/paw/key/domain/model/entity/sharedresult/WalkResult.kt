package com.paw.key.domain.model.entity.sharedresult

import android.graphics.Bitmap
import com.naver.maps.geometry.LatLng

data class WalkResult(
    val bitmap: Bitmap?,
    val totalTime: Long,
    val distance: Float,
    val steps: Int,
    val points : List<LatLng>
)