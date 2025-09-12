package com.paw.key.presentation.ui.course.walk.model

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import com.naver.maps.geometry.LatLng
import com.paw.key.core.util.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class MapState(
    val initialState: UiState<Boolean> = UiState.Loading, // 권한 획득 state
    val currentLocation: LatLng? = null,
    val poiPoints: PersistentList<LatLng> = persistentListOf(),
    val totalDistance: Float = 0f,
    val isTrackingEnabled: Boolean = true, // 카메라 추적 모드
    val shouldCaptureMap: Boolean = false,
    val capturedMapBitmap: Bitmap? = null
)