package com.paw.key.presentation.ui.course.walk.component

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
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
import com.kakao.vectormap.shape.DotPoints
import com.kakao.vectormap.shape.PolygonOptions
import com.kakao.vectormap.shape.PolygonStyles
import com.kakao.vectormap.shape.PolygonStylesSet
import com.paw.key.R

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
    updateLocationAndCalculateDistance : (LatLng, Float) -> Unit,
) : MapView {
    val mapView = remember {
        MapView(context)
    }

    var kakaoMapState by remember {
        mutableStateOf<KakaoMap?>(null)
    }

    var currentLocation by remember {
        mutableStateOf(currentUserLocation)
    }

    val stableCallback = rememberUpdatedState(onLabelClick)

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
                ContextCompat.getColor(context, R.color.teal_200)
            )

            val routeStylesSet = RouteLineStylesSet.from(routeLineStyle)

            val routeSegments = listOf(
                RouteLineSegment.from(pointsToDraw).setStyles(routeLineStyle)
            )

            val routeLineOptions = RouteLineOptions.from(routeSegments)
                .setStylesSet(routeStylesSet)

            currentDrawnRouteLine = kakaoMap.routeLineManager?.layer?.addRouteLine(routeLineOptions)
            currentDrawnRouteLine?.show()

            /*kakaoMap.moveCamera(
                CameraUpdateFactory.fitMapPoints(
                    pointsToDraw.toTypedArray(), 100
                )
            )*/
        }
    }

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
                //fusedLocationClient.removeLocationUpdates(locationCallback)
                /*sensorManager.unregisterListener(stepSensorEventListener)
                fusedLocationClient.removeLocationUpdates(locationCallback)
                initialSensorSteps = null
                isWalking(false)*/
            }
        }

        lifeCycle.addObserver(observer)

        onDispose {
            lifeCycle.removeObserver(observer)
            //fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    LaunchedEffect(isTrackingEnabled, centerLabel) {
        if (currentUserLocation != null && centerLabel != null) {
            centerLabel?.moveTo(currentUserLocation)
            kakaoMapState?.moveCamera(
                CameraUpdateFactory.newCenterPosition(
                    currentUserLocation, 18
                )
            )
        }
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

