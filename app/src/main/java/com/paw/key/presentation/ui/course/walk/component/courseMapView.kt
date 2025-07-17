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
    var trackingManager by remember {
        mutableStateOf<TrackingManager?>(null)
    }

    var dimScreenLayer by remember {
        mutableStateOf<DimScreenLayer?>(null)
    }

    var currentDrawnRouteLine by remember {
        mutableStateOf<RouteLine?>(null)
    }

    /*val yeoksamCoordinates = listOf(
        LatLng.from(37.50097, 127.03734),  // 역삼동 중심 :contentReference[oaicite:1]{index=1}
        LatLng.from(37.50079, 127.03689),  // 역삼역 (L2) 정문 인근 :contentReference[oaicite:2]{index=2}
        LatLng.from(37.50001, 127.03549),  // 역삼역 지하철역 (GPS 웹 기준) :contentReference[oaicite:3]{index=3}
        LatLng.from(37.49950, 127.03322),  // 역삼1동 중심 지역 :contentReference[oaicite:4]{index=4}
        LatLng.from(37.49900, 127.03856),  // 역삼동 중심 북동쪽 :contentReference[oaicite:5]{index=5}
        LatLng.from(37.49999, 127.03719),  // 테헤란로 중심가 (중간 위치) ← 위도/경도 참고 위 :contentReference[oaicite:6]{index=6}
        LatLng.from(37.49850, 127.03800),  // 강남대로 인근
        LatLng.from(37.49800, 127.03450),  // 논현로 인근
        LatLng.from(37.50150, 127.03700),  // 삼성역 방면 경계 지역
        LatLng.from(37.50050, 127.03900),  // 국기원/코엑스 방향 경계
    )*/

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

            kakaoMapState?.moveCamera(
                CameraUpdateFactory.fitMapPoints(
                    poiPoints.toTypedArray(), 150, 15
                )
            )
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
                            trackingManager = kakaoMap.trackingManager

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

                kakaoMapState?.moveCamera(
                    CameraUpdateFactory.fitMapPoints(
                        poiPoints.toTypedArray(), 150, 15
                    )
                )

                kakaoMapState?.moveCamera(
                    CameraUpdateFactory.newCenterPosition(
                        currentUserLocation, 19
                    )
                )
            }

            override fun onPause(owner: LifecycleOwner) {
                mapView.pause()

                kakaoMapState?.moveCamera(
                    CameraUpdateFactory.fitMapPoints(
                        poiPoints.toTypedArray(), 150, 15
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

    LaunchedEffect(currentUserLocation, isTrackingEnabled, centerLabel, kakaoMapState) {
        if (currentUserLocation != null && centerLabel != null && kakaoMapState != null) {
            centerLabel?.moveTo(currentUserLocation)

            /*kakaoMapState?.moveCamera(
                CameraUpdateFactory.newCenterPosition(
                    currentUserLocation, 18
                )
            )*/
        }
    }

    LaunchedEffect(centerLabel, trackingManager) {
        if (centerLabel != null && trackingManager != null) {
            trackingManager?.startTracking(centerLabel)
        }
    }

    LaunchedEffect(isPauseTracking) {
        if (!isPauseTracking) {
            trackingManager?.stopTracking()
            mapView.isClickable = false
            dimScreenLayer?.setColor(Color.Black.copy(alpha = 0.5f).toArgb())
            dimScreenLayer?.setVisible(true)
        } else {
            trackingManager?.startTracking(centerLabel)
            mapView.isClickable = true
            dimScreenLayer?.setVisible(false)
        }
    }

    return mapView
}
