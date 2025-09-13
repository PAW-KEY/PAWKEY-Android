package com.paw.key.presentation.ui.course.sharedwalk.sharedroute

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.kakao.vectormap.graphics.gl.GLSurfaceView
import com.naver.maps.geometry.LatLng
import com.paw.key.R
import com.paw.key.core.designsystem.component.LoadingScreen
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.UiState
import com.paw.key.core.extension.noRippleClickable
import com.paw.key.presentation.ui.course.sharedwalk.sharedroute.state.SharedWalkCourseSideEffect
import com.paw.key.presentation.ui.course.sharedwalk.sharedroute.viewmodel.SharedWalkCourseViewModel
import com.paw.key.presentation.ui.course.walk.component.WalkRecordItem
import com.paw.key.presentation.ui.course.walk.component.WalkRecordRow
import com.paw.key.presentation.ui.course.walk.formatDistance
import com.paw.key.presentation.ui.course.walk.formatTime
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseRecord.DistanceRecord
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseRecord.StepsRecord
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseRecord.TimeRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import java.nio.IntBuffer
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.opengles.GL10
import kotlin.coroutines.resumeWithException


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SharedWalkCourseRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: (Int, Int) -> Unit,
    routeId : Int,
    pageId : Int,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    isSharedWalk : Boolean = true,
    viewModel: SharedWalkCourseViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getWalkSharedTrack(routeId)
    }

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

    LaunchedEffect(Unit) {
        val currentLocation = sharedGetCurrentLocation(
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
                    is SharedWalkCourseSideEffect.ShowSnackBar -> snackBarHostState.showSnackbar(
                        sideEffect.message
                    )

                    SharedWalkCourseSideEffect.NavigateNext -> navigateNext(routeId, pageId)
                    SharedWalkCourseSideEffect.NavigateUp -> navigateUp()
                }
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
            SharedWalkCourseScreen(
                paddingValues = paddingValues,
                navigateUp = navigateUp,
                navigateNext = {
                    navigateNext(routeId, pageId)
                },
                scope = scope,
                snackBarHostState = snackBarHostState,
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
                    viewModel.onStopTrackingEvent()
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
                    viewModel.onMapCaptured(bitmap)
                },
                modifier = modifier,

            )
        }
    }
}

@Composable
fun SharedWalkCourseScreen(
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
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .padding(paddingValues),
        snackbarHost = {

        }
    ) { pv ->
        Box(
            modifier = Modifier
                .padding(pv)
        ) {

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
                        if (!isSharedWalk) {
                            Text(
                                text = "산책이 중단되었어요!",
                                textAlign = TextAlign.Center,
                                style = PawKeyTheme.typography.head24B,
                                color = PawKeyTheme.colors.white1,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Text(
                                text = "산책을 정말 종료하시겠어요?",
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                style = PawKeyTheme.typography.body16M,
                                color = PawKeyTheme.colors.white2,
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .fillMaxWidth()
                            )
                        } else {
                            Text(
                                text = "산책이 중단되었어요.",
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
                    if (isTracking) {
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.weight(1f))

                            FloatingActionButton(
                                shape = CircleShape,
                                onClick = onClickTracking,
                                containerColor = PawKeyTheme.colors.white1,
                                modifier = Modifier
                                    .size(44.dp)
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_course_map_tap_location_on),
                                    contentDescription = "내 위치",//stringResource(id = R.string.lo)
                                    tint = Color.Unspecified
                                )
                            }
                        }

                        PawkeyButton(
                            text = "중지하기",
                            enabled = true,
                            onClick = {

                            },
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .padding(bottom = 44.dp)
                        )
                    } else {
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
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
                                    .padding(horizontal = 24.dp, vertical = 16.dp),
                                color = PawKeyTheme.colors.green500,
                                style = PawKeyTheme.typography.body16Sb
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
                                    .padding(horizontal = 28.dp, vertical = 16.dp),
                                color = PawKeyTheme.colors.white1,
                                style = PawKeyTheme.typography.body16Sb
                            )
                        }
                    }
                }
            }
        }
    }
}

fun sharedCaptureMapToBitmap(surfaceView: GLSurfaceView, onCaptured: (Bitmap?) -> Unit) {
    surfaceView.queueEvent {
        val egl = EGLContext.getEGL() as EGL10
        val gl = egl.eglGetCurrentContext().gl as GL10

        // 원하는 최종 크기를 먼저 계산
        val screenWidth = surfaceView.context.resources.displayMetrics.widthPixels
        val contentWidth = (screenWidth - 32)
        val targetHeight = (156 * surfaceView.context.resources.displayMetrics.density).toInt()

        val bitmap = sharedCreateBitmapFromGLSurface(0, 0, surfaceView.width, surfaceView.height, gl, contentWidth, targetHeight)
        onCaptured(bitmap)
    }
}

fun sharedCreateBitmapFromGLSurface(x: Int, y: Int, w: Int, h: Int, gl: GL10, targetWidth: Int, targetHeight: Int): Bitmap? {
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

    val targetAspectRatio = 16f / 11f

    var cropWidth: Int
    var cropHeight: Int

    // 비율 조정
    val currentAspectRatio = w.toFloat() / h.toFloat()

    // 가로와 세로의 비율 조정 - 가로가 크다면 세로를 증가, 세로가 크다면 가로로 증가
    if (currentAspectRatio > targetAspectRatio) {
        cropHeight = h
        cropWidth = (h * targetAspectRatio).toInt()
    } else {
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

suspend fun sharedGetCurrentLocation(
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
                //continuation.resume(LatLng.from(location.latitude, location.longitude))
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

@Preview(showBackground = true)
@Composable
private fun SharedWalkCourseScreenPreview() {
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