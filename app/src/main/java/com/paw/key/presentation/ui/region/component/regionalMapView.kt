package com.paw.key.presentation.ui.region.component

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.kakao.vectormap.shape.MapPoints
import com.kakao.vectormap.shape.PolygonOptions
import com.kakao.vectormap.shape.PolygonStyle
import com.kakao.vectormap.shape.PolygonStyles
import com.kakao.vectormap.shape.PolygonStylesSet
import com.paw.key.R

@Composable
fun regionalMapView(
    lifeCycle: Lifecycle,
    context: Context,
    currentUserLocation: LatLng?,
    polyPoints: List<List<LatLng>>,
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

    var centerLabel by remember {
        mutableStateOf<Label?>(null)
    }

    // ------------------------------------------------
    //



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

                            val fillColor = 0x5039BA28

                            val strokeColor = 0xFF39BA28.toInt()

                            polyPoints.forEach {
                                kakaoMap.shapeManager?.layer?.addPolygon(
                                    PolygonOptions.from()
                                        .setMapPoints(MapPoints.fromLatLng(it))
                                        .setStylesSet(
                                            PolygonStylesSet.from(
                                                PolygonStyles.from(
                                                    PolygonStyle.from (//(int zoomLevel, int color, float strokeWidth, int strokeColor)
                                                        13,
                                                        fillColor,
                                                        10f,
                                                        strokeColor
                                                    )
                                                )
                                            )
                                        )
                                )?.show()
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
        }
    }

    LaunchedEffect(centerLabel) {
        if (currentUserLocation != null && centerLabel != null) {
            centerLabel?.moveTo(currentUserLocation)
            kakaoMapState?.moveCamera(
                CameraUpdateFactory.newCenterPosition(
                    currentUserLocation, 18
                )
            )
        }
    }

    return mapView
}

