package com.paw.key.presentation.ui.course.walk

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.GLException
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapView
import com.kakao.vectormap.graphics.gl.GLSurfaceView
import com.paw.key.core.designsystem.component.LoadingScreen
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.UiState
import com.paw.key.core.util.noRippleClickable
import com.paw.key.presentation.ui.course.walk.component.WalkRecordItem
import com.paw.key.presentation.ui.course.walk.component.WalkRecordRow
import com.paw.key.presentation.ui.course.walk.component.courseMapView
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseRecord.DistanceRecord
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseRecord.StepsRecord
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseRecord.TimeRecord
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseSideEffect
import com.paw.key.presentation.ui.course.walk.viewmodel.WalkCourseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.nio.IntBuffer
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.opengles.GL10
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun WalkCourseRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    isSharedWalk : Boolean = false,
    viewModel: WalkCourseViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val totalTime by viewModel.totalTime.collectAsStateWithLifecycle()

    val formattedTotalTime by remember(totalTime) {
        derivedStateOf {
            formatTime(totalTime)
        }
    }

    val formatDistance by remember(state.totalDistance) {
        derivedStateOf {
            formatDistance(state.totalDistance)
        }
    }

    // 0~9 = 0, 10~19 = 1 을 감지
    val distanceInTens by remember(state.totalDistance) { // ViewModel의 totalDistance를 참조
        derivedStateOf {
            (state.totalDistance / 10).toInt() // Float을 Int로 변환
        }
    }

    // 이전 10m 단위 값을 저장하여 중복 호출 방지
    var lastRecordedDistanceInTens by remember {
        mutableIntStateOf(-1)
    }

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    // --- 걸음 수 + 이동거리
    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    val stepCounterSensor: Sensor? = remember {
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    val stepSensorEventListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER && state.isRecording) {
                    val totalStepsFromSensor = event.values[0].toLong()
                    Log.d("StepCounter", "Raw Steps from SensorEventListener: $totalStepsFromSensor")

                    viewModel.onSensorDataChanged(totalStepsFromSensor)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }
        }
    }

    val locationRequest = remember {
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000) // 1초마다, 높은 정확도
            .setWaitForAccurateLocation(true) // false = 가장 빠른 위치 / true = 가장 정확한 위치
            .build()
    }

    val locationCallback = remember(viewModel) {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    if (state.isRecording) {
                        val newLatLng = LatLng.from(location.latitude, location.longitude)
                        viewModel.updateLocationAndCalculateDistance(newLatLng, location.accuracy)
                        Log.d("WalkCourseRoute", "Updated location: $newLatLng, accuracy: ${location.accuracy}")
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        val currentLocation = getCurrentLocation(
            context,
            fusedLocationClient,
        )

        viewModel.updateState {
            copy(
                isRecording = true,
                currentLocation = currentLocation,
                initialLocationState = UiState.Success(currentLocation)
            )
        }

        Log.e("SearchMapRoute", "Current Location: ${state.currentLocation}")
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is WalkCourseSideEffect.ShowSnackBar -> snackBarHostState.showSnackbar(
                        sideEffect.message
                    )

                    WalkCourseSideEffect.NavigateNext -> navigateNext()
                    WalkCourseSideEffect.NavigateUp -> navigateUp()
                }
            }
    }

    LaunchedEffect(state.isRecording) {
        Log.d("WalkCourseRoute", "isRecording: ${state.isRecording}")
        if (state.isRecording) {
            try {
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
                Log.d("WalkCourseRoute", "Location updates requested.")
            } catch (e: SecurityException) {
                Log.e("WalkCourseRoute", "위치 권한 없음: ${e.message}")
                snackBarHostState.showSnackbar("위치 권한이 필요합니다.")
                viewModel.updateState {
                    copy(isLocationTracking = false)
                }
            }
        } else {
            fusedLocationClient.removeLocationUpdates(locationCallback)
            Log.d("WalkCourseRoute", "Location updates removed.")
        }
    }

    /*LaunchedEffect(distanceInTens) {
        // 거리가 10m씩 변경되었을 경우
        if (distanceInTens > 0 && distanceInTens > lastRecordedDistanceInTens) { // 0m 제외, 새로운 단위일 때만
            println("새로운 10m 단위 도달! 현재 거리: ${state.totalDistance}m")
            lastRecordedDistanceInTens = distanceInTens
        }

        state.currentLocation?.let { viewModel.addLocation(it) }

        viewModel.updateState {
            copy(
                currentLocation = state.currentLocation
            )
        }

        Log.e("SearchMapRoute", "Added POI at 10m interval: ${state.poiPoints}")
    }*/

    LaunchedEffect(state.isRecording) {
        if (state.isRecording) {
            while (true) {
                delay(1000L)
                viewModel.incrementTotalTime()
            }
        }
    }

    DisposableEffect(stepCounterSensor) {
        if (stepCounterSensor != null) {
            sensorManager.registerListener(
                stepSensorEventListener,
                stepCounterSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

        onDispose {
            if (stepCounterSensor != null) {
                sensorManager.unregisterListener(stepSensorEventListener)
            }
        }
    }
    
    when (state.initialLocationState) {
        is UiState.Empty -> Unit
        is UiState.Failure -> Unit

        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Success -> {
            val mapView = courseMapView(
                lifeCycle = lifecycleOwner.lifecycle,
                context = context,
                onLabelClick = { _, _ -> },
                currentUserLocation = state.currentLocation,
                poiPoints = if (isSharedWalk) {
                    listOf()
                } else {
                    state.poiPoints
                },
                isTrackingEnabled = state.isTrackingEnabled,
                isPauseTracking = state.isRecording, // true = 잠시 중단, false = 시작
                isStopTracking = state.isLocationTracking, // true = 진짜 중단
                updateLocationAndCalculateDistance = { newLatLng, accuracy ->
                    // 정확한 거리를 계산하여 거리를 기록하는 함수
                    viewModel.updateLocationAndCalculateDistance(newLatLng,accuracy)
                },
            )

            LaunchedEffect(Unit) {
                state.currentLocation?.let {
                    viewModel.addInitLocation(
                        location = it
                    )
                }
            }

            LaunchedEffect(state.shouldCaptureMap, state.poiPoints) {
                if (state.shouldCaptureMap) {
                    delay(500L)

                    val glSurfaceView = mapView.surfaceView as? GLSurfaceView
                    if (glSurfaceView != null) {
                        withContext(Dispatchers.IO) {
                            captureMapToBitmap(glSurfaceView) { capturedBitmap ->
                                capturedBitmap?.let {
                                    viewModel.onMapCaptured(it) // 캡처된 비트맵을 ViewModel로 전달
                                    Log.d("WalkCourseRoute", "맵 캡처 성공! (triggered by shouldCaptureMap)")
                                } ?: run {
                                    Log.e("WalkCourseRoute", "맵 캡처 실패: 비트맵이 null입니다.")
                                    viewModel.mapCaptureCompleted()
                                }
                            }
                        }
                    } else {
                        viewModel.mapCaptureCompleted()
                    }
                }
            }

            WalkCourseScreen(
                paddingValues = paddingValues,
                navigateUp = navigateUp,
                navigateNext = navigateNext,
                scope = scope,
                snackBarHostState = snackBarHostState,
                mapView = mapView,
                totalDistance = formatDistance,
                isSharedWalk = isSharedWalk,
                currentSteps = state.steps,
                totalTime = formattedTotalTime,
                isTracking = state.isRecording, // true = 잠시 중단, false = dim
                onClickTracking = {
                    viewModel.updateState {
                        copy(
                            isTrackingEnabled = !this.isTrackingEnabled
                        )
                    }
                },
                onPauseTracking = {
                    viewModel.updateState {
                        copy(
                            isRecording = !this.isRecording,
                            shouldCaptureMap = true
                        )
                    }
                },
                onStartTracking = {
                    viewModel.updateState {
                        copy(
                            isRecording = !this.isRecording,
                        )
                    }
                },
                onStopTracking = {
                    viewModel.updateState {
                        copy(
                            isLocationTracking = !this.isLocationTracking
                        )
                    }
                },
                onCaptured = { bitmap ->
                    //viewModel.onMapCaptured(bitmap)
                },
                modifier = modifier,
            )
        }
    }
}

@Composable
fun WalkCourseScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    totalDistance: String,
    currentSteps: Long,
    totalTime: String,
    isSharedWalk: Boolean,
    isTracking: Boolean, // 버튼 상태
    onClickTracking: () -> Unit,
    onStartTracking: () -> Unit, // 계속하기
    onPauseTracking: () -> Unit, // 잠시 중단
    onStopTracking: () -> Unit, // 종료하기
    onCaptured: (Bitmap?) -> Unit,
    mapView: MapView,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .padding(paddingValues),
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        }
    ) { pv ->
        Box(
            modifier = Modifier
                .padding(pv)
        ) {
            AndroidView(
                factory = { mapView },
                modifier = Modifier
                    .align(Alignment.Center)
            )

            Column (
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WalkRecordRow(
                    totalDistance = totalDistance,
                    totalTime = totalTime,
                    currentSteps = currentSteps.toInt(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )

                if (isTracking) {
                    Spacer(modifier = Modifier.weight(1f))
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Todo : 텍스트 스타일 24b로 변경 예쩡
                        if (isSharedWalk) {
                            Text(
                                text = "산책이 중단되었어요!",
                                textAlign = TextAlign.Center,
                                style = PawKeyTheme.typography.head22B,
                                color = PawKeyTheme.colors.white1,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Text(
                                text = "산책을 정말 종료하시겠어요?",
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                style = PawKeyTheme.typography.body16M,
                                color = PawKeyTheme.colors.white2,
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            Text(
                                text = "산책을 종료하시겠어요?",
                                textAlign = TextAlign.Center,
                                style = PawKeyTheme.typography.head22B,
                                color = PawKeyTheme.colors.white1,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Text(
                                text = "아직 설정된 산책 루트를 다 돌지 못했어요 🥲",
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                style = PawKeyTheme.typography.body16M,
                                color = PawKeyTheme.colors.white2,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                        .navigationBarsPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        FloatingActionButton(
                            shape = CircleShape,
                            onClick = onClickTracking,
                            containerColor = Color.White
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "내 위치",//stringResource(id = R.string.lo)
                                tint = Color.Black
                            )
                        }
                    }

                    if (isTracking) {
                        PawkeyButton(
                            text = "산책 기록 종료",
                            enabled = true,
                            onClick = {
                                onPauseTracking()

                                scope.launch {
                                    val glSurfaceView = mapView.surfaceView as? GLSurfaceView
                                    if (glSurfaceView != null) {
                                        withContext(Dispatchers.IO) {
                                            captureMapToBitmap(glSurfaceView) { capturedBitmap ->
                                                capturedBitmap?.let {
                                                    onCaptured(it)
                                                    Log.d("WalkCourseScreen", "맵 캡처 성공!")
                                                } ?: run {
                                                    Log.e("WalkCourseScreen", "맵 캡처 실패: 비트맵이 null입니다.")
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    } else {
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ){
                            Text(
                                text = "계속 산책하기",
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        Color.White,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .noRippleClickable {
                                        onStartTracking()
                                    }
                                    .border(
                                        width = 1.dp,
                                        color = PawKeyTheme.colors.green500,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 24.dp, vertical = 16.dp)
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = "산책 종료하기",
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        color = PawKeyTheme.colors.green500,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .noRippleClickable {
                                        navigateNext()
                                        onStopTracking()
                                    }
                                    .padding(horizontal = 24.dp, vertical = 16.dp),
                                color = PawKeyTheme.colors.white1
                            )
                        }
                    }
                }
            }
        }
    }
}

fun captureMapToBitmap(surfaceView: GLSurfaceView, onCaptured: (Bitmap?) -> Unit) {
    surfaceView.queueEvent {
        val egl = EGLContext.getEGL() as EGL10
        val gl = egl.eglGetCurrentContext().gl as GL10
        val bitmap = createBitmapFromGLSurface(0, 0, surfaceView.width, surfaceView.height, gl)

        onCaptured(bitmap)
    }
}

private fun createBitmapFromGLSurface(x: Int, y: Int, w: Int, h: Int, gl: GL10): Bitmap? {
    val bitmapBuffer = IntArray(w * h)
    val bitmapSource = IntArray(w * h)
    val intBuffer = IntBuffer.wrap(bitmapBuffer)
    intBuffer.position(0)

    try {
        gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer)
        var offset1: Int
        var offset2: Int

        for (i in 0 until h) {
            offset1 = i * w
            offset2 = (h - i - 1) * w

            for (j in 0 until w) {
                val texturePixel = bitmapBuffer[offset1 + j]
                val blue = (texturePixel shr 16) and 0xff
                val red = (texturePixel shl 16) and 0x00ff0000
                val pixel = (texturePixel and 0xff00ff00.toInt()) or red or blue
                bitmapSource[offset2 + j] = pixel
            }
        }
    } catch (e: GLException) {
        return null
    } catch (e: OutOfMemoryError) {
        return null
    }

    // 전체 비트맵 생성
    val fullBitmap = Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888)

    // 화면의 aspectRatio 계산 (Modifier.aspectRatio(340f / 150f) 와 동일하게)
    val targetAspectRatio = 16f / 9f // 사용하고자 하는 화면의 aspectRatio를 여기에 설정합니다.

    var cropWidth: Int
    var cropHeight: Int

    val currentAspectRatio = w.toFloat() / h.toFloat()

    if (currentAspectRatio > targetAspectRatio) {
        // 현재 비트맵이 목표보다 가로로 더 길면, 높이를 기준으로 너비를 계산하여 자름 (가로 양쪽 여백 발생)
        cropHeight = h
        cropWidth = (h * targetAspectRatio).toInt()
    } else {
        // 현재 비트맵이 목표보다 세로로 더 길거나 같으면, 너비를 기준으로 높이를 계산하여 자름 (세로 양쪽 여백 발생)
        cropWidth = w
        cropHeight = (w / targetAspectRatio).toInt()
    }

    val startX = ((w - cropWidth) / 2).coerceAtLeast(0)
    val startY = ((h - cropHeight) / 2).coerceAtLeast(0)

    val safeWidth = minOf(cropWidth, w - startX)
    val safeHeight = minOf(cropHeight, h - startY)

    // 잘라낸 비트맵 반환
    return Bitmap.createBitmap(fullBitmap, startX, startY, safeWidth, safeHeight)
}

suspend fun getCurrentLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient
): LatLng = suspendCancellableCoroutine { continuation ->
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000L)
        .setWaitForAccurateLocation(false)
        .setMaxUpdates(1) // 한 번만 업데이트 받음
        .build()

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            if (location != null) {
                continuation.resume(LatLng.from(location.latitude, location.longitude))
                fusedLocationClient.removeLocationUpdates(this)
            } else {
                continuation.resumeWithException(IllegalStateException("위치 정보를 가져올 수 없습니다"))
                fusedLocationClient.removeLocationUpdates(this)
            }
        }
    }

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    } else {
        continuation.resumeWithException(SecurityException("위치 권한을 확인해주세요"))
    }

    continuation.invokeOnCancellation {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    Log.e("getCurrentLocation", "getCurrentLocation ${continuation}")
}

fun formatTime(millis: Long): String {
    val totalSeconds = TimeUnit.MILLISECONDS.toSeconds(millis)
    //val hours = TimeUnit.SECONDS.toHours(totalSeconds)
    val minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) % 60
    val seconds = totalSeconds % 60

    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}

fun formatDistance(distance: Float): String {
    val distanceToKm = distance / 1000
    return String.format(Locale.getDefault(), "%.1f km", distanceToKm)
}

@Preview(showBackground = true)
@Composable
private fun WalkCourseScreenPreview() {
    PawKeyTheme {
        Row (
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = PawKeyTheme.colors.green500,
                    shape = RoundedCornerShape(12.dp)
                )
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                //.align(Alignment.CenterHorizontally)
        ){
            val recordItems = listOf(
                DistanceRecord,
                TimeRecord,
                StepsRecord,
            )

            recordItems.forEach { record ->
                /*if (record == TimeRecord) {
                    WalkRecordItem(
                        recordTitle = record.titleResId,
                        recordContent = "00:00",
                        modifier = Modifier
                            .weight(1f),
                    )
                }*/
                WalkRecordItem(
                    recordTitle = record.titleResId,
                    recordContent = "00:00",
                    modifier = Modifier
                        .weight(1f),
                )
            }
        }
    }
}