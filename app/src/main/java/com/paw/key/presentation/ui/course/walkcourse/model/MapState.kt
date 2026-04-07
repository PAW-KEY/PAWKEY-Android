package com.paw.key.presentation.ui.course.walkcourse.model

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import com.naver.maps.geometry.LatLng
import com.paw.key.core.util.UiState
import com.paw.key.domain.entity.walk.WalkPoint
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

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

fun LatLng.toEntity(
    routeId: String,
    timestamp: Int
): WalkPoint {
    return WalkPoint(
        routeId = routeId,
        lat = this.latitude,
        lng = this.longitude,
        timestamp = timestamp
    )
}

// 서버에서 온 [경도, 위도] 리스트를 Naver Map LatLng 불변 리스트로 변환
fun List<List<Double>>.toPersistentLatLngList(): PersistentList<LatLng> {
    return this.mapNotNull { coord ->
        // 안전하게 데이터가 2개 이상일 때만 처리
        if (coord.size >= 2) {
            val lng = coord[0] // (경도)
            val lat = coord[1] // (위도)

            LatLng(lat, lng)
        } else {
            null
        }
    }.toPersistentList()
}