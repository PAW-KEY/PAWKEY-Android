package com.paw.key.presentation.ui.course.walkcomplete.state

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import com.naver.maps.geometry.LatLng

class WalkCompleteContract {
    @Immutable
    data class WalkCompleteState(
        val bitmap: Bitmap? = null,
        val poiPoints: List<LatLng> = emptyList(),
        val totalDistance: Float = 0f,
        val totalTime: Long = 0L,
        val totalSteps : Int = 0,
    )
}