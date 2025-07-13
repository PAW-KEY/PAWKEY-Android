package com.paw.key.presentation.ui.course.entire.tab.map.component

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
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.Label
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.paw.key.R

@Composable
fun tapMapView(
    lifeCycle : Lifecycle,
    context : Context,
    currentUserLocation : LatLng?,
    isTrackingEnabled : Boolean,
    onDisposeCallback : () -> Unit,
) : MapView {
    val mapView = remember {
        MapView(context)
    }

    var kakaoMapState by remember {
        mutableStateOf<KakaoMap?>(null)
    }

    var centerLabel by remember {
        mutableStateOf<Label?>(null)
    }

    val stableOnDisposeCallback = rememberUpdatedState(onDisposeCallback)

    DisposableEffect(lifeCycle) {
        val observer = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                mapView.start(
                    object : MapLifeCycleCallback() {
                        override fun onMapDestroy() {
                        }

                        override fun onMapError(error: Exception) {
                            Log.e("MapView", "지도 오류 발생: $error")
                        }
                    },
                    object : KakaoMapReadyCallback() {
                        override fun onMapReady(kakaoMap: KakaoMap) {
                            kakaoMapState = kakaoMap
                            //trackingManager = kakaoMap.trackingManager

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

                            /*kakaoMap.shapeManager?.layer?.addPolygon(
                                PolygonOptions.from("circlePolygon")
                                    .setDotPoints(DotPoints.fromCircle(currentUserLocation, 1.0f))
                                    .setStylesSet(
                                        PolygonStylesSet.from(
                                            PolygonStyles.from(context.getColor(R.color.purple_700))
                                        )
                                    )
                            )*/

                            val initialCameraPosition = currentUserLocation ?: LatLng.from(37.497942, 127.027619)

                            kakaoMap.moveCamera(
                                CameraUpdateFactory.newCenterPosition(
                                    initialCameraPosition, 21
                                )
                            )
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
            //onDisposeCallback()
            stableOnDisposeCallback.value()
            /*sensorManager.unregisterListener(stepSensorEventListener)
            isWalking(false)*/
        }
    }

    LaunchedEffect(currentUserLocation, centerLabel, kakaoMapState) {
        if (currentUserLocation != null && centerLabel != null && kakaoMapState != null) {
            centerLabel?.moveTo(currentUserLocation)
            Log.d("TapMapView", "카메라 $currentUserLocation (Tracking Enabled)")
        }
    }

    LaunchedEffect(isTrackingEnabled) {
        kakaoMapState?.moveCamera(
            CameraUpdateFactory.newCenterPosition(
                currentUserLocation, 21 // 줌 레벨을 유지하거나 필요에 따라 변경
            )
        )
    }


    return mapView
}