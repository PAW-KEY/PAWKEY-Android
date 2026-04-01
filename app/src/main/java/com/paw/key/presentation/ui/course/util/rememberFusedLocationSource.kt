package com.paw.key.presentation.ui.course.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.annotation.UiThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationSource
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 실시간 위치가 변경되었음을 알려주는 인터페이스
interface RealTimeLocationListener {
    fun onLocationChanged(location: Location)
}

// 3m 씩 이동한다고 가정하고 실시간 테스트를 위한 좌표
private val testPoints = listOf(
    LatLng(37.4979000000, 127.0276000000), // 시작점

    // 북쪽으로 직진 (3m × 3번)
    LatLng(37.4979269500, 127.0276000000),
    LatLng(37.4979539000, 127.0276000000),
    LatLng(37.4979808500, 127.0276000000),

    // 동쪽으로 꺾음 (3m × 2번)
    LatLng(37.4979808500, 127.0276340000),
    LatLng(37.4979808500, 127.0276680000),

    // 남쪽으로 직진 (3m × 3번)
    LatLng(37.4979539000, 127.0276680000),
    LatLng(37.4979269500, 127.0276680000),
    LatLng(37.4979000000, 127.0276680000),

    // 서쪽으로 꺾어 시작점으로 복귀
    LatLng(37.4979000000, 127.0276340000),
    LatLng(37.4979000000, 127.0276000000) // 시작점
)

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun rememberCustomFusedLocationSource(
    // 테스트 모드를 제어하기 위한 파라미터
    useTestPoints: Boolean = false,
    cameraPositionState: CameraPositionState,
    hasLocationPermission: Boolean
): FusedLocationSource {
    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val locationSource = remember(useTestPoints) {
        FusedLocationSource(context, fusedLocationClient, useTestPoints)
    }

    var hasMovedToInitialLocation by remember { mutableStateOf(false) }

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission && !hasMovedToInitialLocation) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@LaunchedEffect
            }

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    /*Log.d("rememberCustomFusedLocationSource", "lastLocation: $it")
                    val latLng = LatLng(it.latitude, it.longitude)
                    cameraPositionState.move(CameraUpdate.scrollTo(latLng))*/
                    cameraPositionState.move(CameraUpdate.scrollTo(LatLng(it.latitude, it.longitude)))
                    hasMovedToInitialLocation = true
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            locationSource.deactivate()
        }
    }
    return locationSource
}

class FusedLocationSource(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val useTestPoints: Boolean = false
) : LocationSource {
    private var listener: LocationSource.OnLocationChangedListener? = null
    private var realTimeListener: RealTimeLocationListener? = null
    private var isListening = false

    // 테스트를 위한 코루틴
    private var coroutineScope: CoroutineScope? = null
    private var simulationJob: Job? = null

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location? = locationResult.lastLocation
            if (lastLocation != null) {
                Log.d("FusedLocationSource", "onLocationResult: $lastLocation")
                // 지도에 현 위치 업데이트
                listener?.onLocationChanged(lastLocation)
                // 이건 실시간으로 위치 전달 업데이트
                realTimeListener?.onLocationChanged(lastLocation)
            }
        }
    }

    fun setRealTimeLocationListener(listener: RealTimeLocationListener) {
        this.realTimeListener = listener
    }

    /*@UiThread
    override fun activate(listener: LocationSource.OnLocationChangedListener) {
        this.listener = listener
        if (!isListening) {
            // 권한 확인
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            isListening = true
        }
    }*/
    @UiThread
    override fun activate(listener: LocationSource.OnLocationChangedListener) {
        Log.d("FusedLocationSource", "activate() called!")
        this.listener = listener
        if (!isListening) {
            isListening = true
            if (useTestPoints) {
                coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
                startSimulation()
            } else {
                startRealLocationUpdates()
            }
        }
    }

    private fun startSimulation() {
        simulationJob = coroutineScope?.launch {
            for (point in testPoints) {
                val mockLocation = Location("TestProvider").apply {
                    latitude = point.latitude
                    longitude = point.longitude
                }

                listener?.onLocationChanged(mockLocation)
                realTimeListener?.onLocationChanged(mockLocation)

                delay(3000L)
            }
        }
    }

    private fun startRealLocationUpdates() {
        // 권한 확인
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun deactivate() {
        if (isListening) {
            if (useTestPoints) {
                coroutineScope?.cancel()
                coroutineScope = null
                simulationJob?.cancel()
            } else {
                fusedLocationClient.removeLocationUpdates(locationCallback)
            }
            isListening = false
            listener = null
            realTimeListener = null
        }
    }

    companion object {
        private val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000L).apply {
            setMinUpdateIntervalMillis(1000L)
            setMinUpdateDistanceMeters(2.0f)
        }.build()
    }
}