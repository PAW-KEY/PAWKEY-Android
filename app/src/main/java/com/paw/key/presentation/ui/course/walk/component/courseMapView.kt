package com.paw.key.presentation.ui.course.walk.component

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.graphics.gl.GLSurfaceView
import com.kakao.vectormap.label.Label
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.TrackingManager
import com.kakao.vectormap.route.RouteLine
import com.kakao.vectormap.route.RouteLineOptions
import com.kakao.vectormap.route.RouteLineSegment
import com.kakao.vectormap.route.RouteLineStyle
import com.kakao.vectormap.route.RouteLineStylesSet
import com.kakao.vectormap.shape.DimScreenLayer
import com.paw.key.R
import java.lang.Math.toDegrees
import java.lang.Math.toRadians
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun courseMapView(
    lifeCycle : Lifecycle,
    context : Context,
    currentUserLocation : LatLng?,
    isTrackingEnabled : Boolean,
    isPauseTracking : Boolean,
    isStopTracking : Boolean,
    poiPoints : List<LatLng>,
    onLabelClick : (LatLng, String) -> Unit,
    onDisposeCallback : () -> Unit
) : MapView {
    val mapView = remember {
        MapView(context)
    }

    var kakaoMapState by remember {
        mutableStateOf<KakaoMap?>(null)
    }

    val stableCallback = rememberUpdatedState(onLabelClick)
    val stableOnDisposeCallback = rememberUpdatedState(onDisposeCallback)

    var centerLabel by remember {
        mutableStateOf<Label?>(null)
    }

    // ------------------------------------------------
    // 트래킹

    var dimScreenLayer by remember {
        mutableStateOf<DimScreenLayer?>(null)
    }

    var currentDrawnRouteLine by remember {
        mutableStateOf<RouteLine?>(null)
    }

    val drawRouteOnMap: (KakaoMap, List<LatLng>) -> Unit = { kakaoMap, pointsToDraw ->
        if (pointsToDraw.isNotEmpty()) {
            currentDrawnRouteLine?.remove()
            currentDrawnRouteLine = null

            val routeLineStyle = RouteLineStyle.from(
                12f,
                ContextCompat.getColor(context, R.color.green_500)
            )

            val routeStylesSet = RouteLineStylesSet.from(routeLineStyle)

            val routeSegments = listOf(
                RouteLineSegment.from(pointsToDraw).setStyles(routeLineStyle)
            )

            val routeLineOptions = RouteLineOptions.from(routeSegments)
                .setStylesSet(routeStylesSet)

            currentDrawnRouteLine = kakaoMap.routeLineManager?.layer?.addRouteLine(routeLineOptions)
            currentDrawnRouteLine?.show()
        }
    }

    /*val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val locationRequest = remember {
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000) // 1초마다, 높은 정확도
            .setWaitForAccurateLocation(true)
            .build()
    }

    val locationCallback = remember(isPauseTracking) {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    if (isPauseTracking) { // isRecording 상태를 직접 사용
                        val newLatLng = LatLng.from(location.latitude, location.longitude)
                        currentLocation = newLatLng
                        Log.d("CourseMapView", "Updated location: $newLatLng, accuracy: ${location.accuracy}")
                    }
                }
            }
        }
    }*/

    LaunchedEffect(poiPoints) {
        kakaoMapState?.let { map ->
            drawRouteOnMap(map, poiPoints)
        }
    }

    DisposableEffect(lifeCycle) {
        val observer = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                mapView.start(
                    object : MapLifeCycleCallback() {
                        override fun onMapDestroy() {
                            // 지도 종료 처리
                            currentDrawnRouteLine = null
                        }

                        override fun onMapError(error: Exception) {
                            Log.e("MapView", "지도 오류 발생: $error")
                        }
                    },
                    object : KakaoMapReadyCallback() {
                        override fun onMapReady(kakaoMap: KakaoMap) {
                            kakaoMapState = kakaoMap
                            dimScreenLayer = kakaoMap.dimScreenManager?.dimScreenLayer

                            centerLabel = kakaoMap.labelManager?.layer?.addLabel(
                                // userLocation이 null일 경우
                                LabelOptions.from("dotLabel", currentUserLocation ?: LatLng.from(37.497942, 127.027619))
                                    .setStyles(
                                        LabelStyle.from(
                                            R.drawable.user_poi
                                        ).setAnchorPoint(0.5f, 0.5f)
                                    )
                                    .setRank(5)
                            )

                            val initialCameraPosition = currentUserLocation ?: LatLng.from(37.497942, 127.027619)

                            kakaoMap.moveCamera(
                                CameraUpdateFactory.newCenterPosition(
                                    initialCameraPosition, 19
                                )
                            )

                            drawRouteOnMap(kakaoMap, poiPoints)

                            kakaoMap.setOnPoiClickListener { _, latLng, _, name -> //name = poi id
                                stableCallback.value(latLng, name)
                            }
                        }

                        override fun getPosition(): LatLng {
                            //userLocation = LatLng.from(locationY, locationX)
                            return currentUserLocation ?: LatLng.from(37.497942, 127.027619)
                        }
                    }
                )
            }

            override fun onResume(owner: LifecycleOwner) {
                mapView.resume()
            }

            override fun onPause(owner: LifecycleOwner) {
                mapView.pause()
                kakaoMapState?.moveCamera(
                    CameraUpdateFactory.fitMapPoints(
                        poiPoints.toTypedArray(), 700
                    )
                )
            }
        }

        lifeCycle.addObserver(observer)

        onDispose {
            lifeCycle.removeObserver(observer)
            //fusedLocationClient.removeLocationUpdates(locationCallback)
            onDisposeCallback()
            stableOnDisposeCallback.value()
        }
    }

    LaunchedEffect(currentUserLocation, centerLabel, kakaoMapState) {
        if (currentUserLocation != null && centerLabel != null && kakaoMapState != null) {
            centerLabel?.moveTo(currentUserLocation)
            Log.d("courseMapview", "Center label moved to: $currentUserLocation")
        }
    }

    LaunchedEffect(isTrackingEnabled) {
        kakaoMapState?.moveCamera(
            CameraUpdateFactory.newCenterPosition(
                currentUserLocation, 18
            )
        )
    }

    LaunchedEffect(isPauseTracking) {
        if (!isPauseTracking) {
            mapView.isClickable = false
            dimScreenLayer?.setColor(Color.Black.copy(alpha = 0.5f).toArgb())
            dimScreenLayer?.setVisible(true)
        } else {
            mapView.isClickable = true
            dimScreenLayer?.setVisible(false)
        }
    }

    return mapView
}


fun calculateMidpoint(point1: LatLng, point2: LatLng): LatLng {
    val lonAvg = (point1.longitude + point2.longitude) / 2.0

    val lat1Rad = toRadians(point1.latitude)
    val lon1Rad = toRadians(point1.longitude)
    val lat2Rad = toRadians(point2.latitude)
    val lon2Rad = toRadians(point2.longitude)

    val Bx = cos(lat2Rad) * cos(lon2Rad - lon1Rad)
    val By = cos(lat2Rad) * sin(lon2Rad - lon1Rad)
    val latMid = atan2(sin(lat1Rad) + sin(lat2Rad), sqrt((cos(lat1Rad) + Bx) * (cos(lat1Rad) + Bx) + By * By))
    val lonMid = lon1Rad + atan2(By, cos(lat1Rad) + Bx)

    return LatLng.from(toDegrees(latMid), toDegrees(lonMid))
}
