package com.paw.key.presentation.ui.course.walk

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLException
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.PixelCopy
import android.widget.Toast
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.kakao.vectormap.graphics.gl.GLSurfaceView
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.CameraUpdateReason
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationOverlay
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PathOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.paw.key.R
import com.paw.key.core.designsystem.component.LoadingScreen
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.core.util.UiState
import com.paw.key.core.extension.noRippleClickable
import com.paw.key.presentation.ui.course.util.FusedLocationSource
import com.paw.key.core.util.PermissionRequestEffect
import com.paw.key.presentation.ui.course.util.StepCountListener
import com.paw.key.presentation.ui.course.util.rememberCustomFusedLocationSource
import com.paw.key.presentation.ui.course.util.rememberStepCounter
import com.paw.key.presentation.ui.course.walk.component.WalkRecordItem
import com.paw.key.presentation.ui.course.walk.component.WalkRecordRow
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseRecord.DistanceRecord
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseRecord.StepsRecord
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseRecord.TimeRecord
import com.paw.key.presentation.ui.course.walk.state.WalkCourseContract.WalkCourseSideEffect
import com.paw.key.presentation.ui.course.walk.viewmodel.WalkCourseViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.drop
import java.nio.IntBuffer
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.opengles.GL10


@OptIn(ExperimentalNaverMapApi::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun WalkCourseRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: (routeId : Int) -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    isSharedWalk : Boolean = false,
    viewModel: WalkCourseViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val state by viewModel.state.collectAsStateWithLifecycle()

    val cameraPositionState = rememberCameraPositionState()

    val userId = PreferenceDataStore.getUserId()

    var hasLocationPermission by remember { mutableStateOf(false) }

    val fusedLocationClient = rememberCustomFusedLocationSource(
        useTestPoints = false,
        cameraPositionState = cameraPositionState,
        hasLocationPermission = hasLocationPermission
    )

    val stepCounter = rememberStepCounter()

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is WalkCourseSideEffect.ShowSnackBar -> snackBarHostState.showSnackbar(
                        sideEffect.message
                    )

                    is WalkCourseSideEffect.NavigateNext -> navigateNext(sideEffect.regionId)
                    WalkCourseSideEffect.NavigateUp -> navigateUp()
                }
            }
    }

    val requiredPermissions = remember {
        mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                add(Manifest.permission.ACTIVITY_RECOGNITION)
            }
        }.toTypedArray()
    }

    PermissionRequestEffect(
        permissions = requiredPermissions,
        onResult = { isGranted ->
            hasLocationPermission = isGranted
            if (isGranted) {
                viewModel.onPermissionsGranted()
                fusedLocationClient.setRealTimeLocationListener(viewModel)
                /*fusedLocationClient.activate {
                    if (state.recordingState.isRecording) {
                        viewModel.startTracking()
                    }
                }*/
            } else {
                Toast.makeText(context, "산책 기록을 위해 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val formattedTotalTime by remember {
        derivedStateOf {
            formatTime(state.totalTimeMillis)
        }
    }

    val formatDistance by remember {
        derivedStateOf {
            formatDistance(state.mapState.totalDistance)
        }
    }

    var mapProperties by remember {
        mutableStateOf(MapProperties())
    }

    /*// 0~9 = 0, 10~19 = 1 을 감지
    val distanceInTens by remember(state.totalDistance) { // ViewModel의 totalDistance를 참조
        derivedStateOf {
            (state.totalDistance / 10).toInt() // Float을 Int로 변환
        }
    }

    // 이전 10m 단위 값을 저장하여 중복 호출 방지
    var lastRecordedDistanceInTens by remember {
        mutableIntStateOf(-1)
    }*/



    LaunchedEffect(state.recordingState.isRecording, stepCounter) {
        if (state.recordingState.isRecording) {
            stepCounter.setStepCountListener(object : StepCountListener {
                override fun onStepCountChanged(sessionSteps: Long) {
                    viewModel.onRawStepData(sessionSteps)
                }
                override fun onSensorNotFound() {
                    Toast.makeText(context, "걸음 수 측정 센서가 없는 기기입니다.", Toast.LENGTH_SHORT).show()
                }
            })
            stepCounter.activate()
        } else {
            stepCounter.deactivate()
        }
    }

    LaunchedEffect(state.mapState.poiPoints.size) {
        if (state.mapState.poiPoints.size >= 2) {
            val bounds = LatLngBounds.from(state.mapState.poiPoints)
            cameraPositionState.animate(
                CameraUpdate.fitBounds(bounds, 300)
            )
        }
    }

    LaunchedEffect(state.mapState.isTrackingEnabled) {
        mapProperties = mapProperties.copy(
            locationTrackingMode = if (state.mapState.isTrackingEnabled) {
                LocationTrackingMode.Follow
            } else {
                LocationTrackingMode.NoFollow
            }
        )
    }

    LaunchedEffect(cameraPositionState) {
        snapshotFlow { cameraPositionState.cameraUpdateReason }
            .drop(1) // flow 가 시작될 때의 이전 값 무시
            .collect { reason ->
                if (reason == CameraUpdateReason.GESTURE && state.mapState.isTrackingEnabled) {
                    viewModel.disableTracking()
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

    when (state.mapState.initialState) {
        is UiState.Empty -> Unit
        is UiState.Failure -> Unit

        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Success -> {
            WalkCourseScreen(
                paddingValues = paddingValues,
                navigateUp = navigateUp,
                cameraPositionState = cameraPositionState,
                currentLocation = state.mapState.currentLocation,
                routeLineCoords = state.mapState.poiPoints,
                locationSource = fusedLocationClient,
                context = context,
                totalDistance = formatDistance,
                mapProperties = mapProperties,
                isSharedWalk = isSharedWalk,
                currentSteps = state.stepCounterState.sessionSteps,
                totalTime = formattedTotalTime,
                isRecording = state.recordingState.isRecording, // 산책 중단, 계속 여부
                isTracking = state.mapState.isTrackingEnabled, // 산책 포커싱
                onClickTracking = {
                    viewModel.fetchTrackingEnable()
                    Log.d("WalkCourseRoute", "onClickTracking ${state.mapState.isTrackingEnabled}")
                },
                onPauseTracking = { // 일시정지

                },
                onStartTracking = { // 계속하기

                },
                onStopTracking = {
                    /*scope.launch {
                        viewModel.postWalkCourseData(userId = userId.first())
                    }*/
                },
                onCaptured = { bitmap ->
                    // Todo : bitmap 안쓸거임
                },
                modifier = modifier,
            )
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun WalkCourseScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    cameraPositionState: CameraPositionState,
    locationSource: FusedLocationSource,
    context: Context,
    currentLocation : LatLng?,
    routeLineCoords : ImmutableList<LatLng>,
    totalDistance: String,
    currentSteps: Long,
    totalTime: String,
    mapProperties: MapProperties,
    isSharedWalk: Boolean,
    isTracking: Boolean, // 포커싱 여부
    isRecording: Boolean, // 산책 중단, 계속 여부
    onClickTracking: () -> Unit, // 따라다니기
    onStartTracking: () -> Unit, // 계속하기
    onPauseTracking: () -> Unit, // 잠시 중단
    onStopTracking: () -> Unit, // 종료하기
    onCaptured: (Bitmap?) -> Unit,
    modifier: Modifier = Modifier,
) {
    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                logoGravity = Gravity.BOTTOM or Gravity.START,
            )
        )
    }

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
            NaverMap (
                modifier = Modifier
                    .fillMaxSize(),
                cameraPositionState = cameraPositionState,
                locationSource = locationSource,
                locale = Locale.KOREA,
                uiSettings = mapUiSettings,
                properties = mapProperties,
            ) {
                if (currentLocation != null) {
                    LocationOverlay(
                        position = currentLocation ,
                        icon = OverlayImage.fromResource(R.drawable.user_poi),
                    )
                }

                if (routeLineCoords.isNotEmpty() && routeLineCoords.size >= 2) {
                    PathOverlay(
                        coords = routeLineCoords,
                        width = 5.dp,
                        color = PawKeyTheme.colors.green500,
                        outlineWidth = 0.dp
                    )
                }
            }

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

                if (isRecording) {
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
                                    .fillMaxWidth()
                                    .padding(top = 12.dp)
                            )
                        } else {
                            Text(
                                text = "산책이 중단되었어요",
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
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        FloatingActionButton(
                            shape = CircleShape,
                            onClick = onClickTracking,
                            containerColor = if (isTracking) PawKeyTheme.colors.green500 else Color.White,
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_course_map_tap_location_on),
                                contentDescription = "내 위치",//stringResource(id = R.string.lo)
                                tint = Color.Black
                            )
                        }
                    }

                    if (isRecording) {
                        PawkeyButton(
                            text = "중지하기",
                            enabled = true,
                            onClick = {
                                onPauseTracking()

                                // Todo : 맵 캡처 로직 변경 예정
                                /*scope.launch {
                                    val glSurfaceView = mapView.surfaceView as? GLSurfaceView
                                    if (glSurfaceView != null) {
                                        withContext(Dispatchers.IO) {
                                            captureMapToBitmap(
                                                glSurfaceView
                                            ) { capturedBitmap ->
                                                capturedBitmap?.let {
                                                    onCaptured(it)
                                                    Log.d("WalkCourseScreen", "맵 캡처 성공!")
                                                } ?: run {
                                                    Log.e(
                                                        "WalkCourseScreen",
                                                        "맵 캡처 실패: 비트맵이 null입니다."
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }*/
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

fun captureMapToBitmap(surfaceView: GLSurfaceView, onCaptured: (Bitmap?) -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        captureUsingPixelCopy(surfaceView, onCaptured)
    } else {
        surfaceView.queueEvent {
            val egl = EGLContext.getEGL() as EGL10
            val gl = egl.eglGetCurrentContext().gl as GL10

            val context = surfaceView.context
            val density = context.resources.displayMetrics.density
            val screenWidth = context.resources.displayMetrics.widthPixels

            val contentWidth = (screenWidth - 32)
            val targetHeight = (156 * density).toInt()

            // OpenGL로 전체 비트맵 캡처
            val fullBitmap = createBitmapFromGLSurface(0, 0, surfaceView.width, surfaceView.height, gl)

            val croppedBitmap = fullBitmap?.let { bitmap ->
                val centerX = bitmap.width / 2
                val centerY = bitmap.height / 2

                val cropStartX = (centerX - contentWidth / 2).coerceAtLeast(0)
                val cropStartY = (centerY - targetHeight / 2).coerceAtLeast(0)

                val safeWidth = minOf(contentWidth, bitmap.width - cropStartX)
                val safeHeight = minOf(targetHeight, bitmap.height - cropStartY)

                Bitmap.createBitmap(bitmap, cropStartX, cropStartY, safeWidth, safeHeight)
            }

            onCaptured(croppedBitmap)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun captureUsingPixelCopy(
    surfaceView: GLSurfaceView,
    onCaptured: (Bitmap?) -> Unit
) {
    val rawBitmap = Bitmap.createBitmap(surfaceView.width, surfaceView.height, Bitmap.Config.ARGB_8888)

    try {
        PixelCopy.request(surfaceView, rawBitmap, { copyResult ->
            if (copyResult == PixelCopy.SUCCESS) {
                val croppedBitmap = cropCenterWithAspectRatio(rawBitmap, 16f / 11f)
                onCaptured(croppedBitmap)
            } else {
                Log.e("PixelCopy", "PixelCopy 실패: $copyResult")
                onCaptured(null)
            }
        }, Handler(Looper.getMainLooper()))
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        onCaptured(null)
    }
}

fun cropCenterWithAspectRatio(
    bitmap: Bitmap,
    targetAspectRatio: Float
): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val currentAspectRatio = width.toFloat() / height.toFloat()

    val cropWidth: Int
    val cropHeight: Int

    if (currentAspectRatio > targetAspectRatio) {
        // 현재 이미지가 더 넓음 → 좌우 잘라야 함
        cropHeight = height
        cropWidth = (height * targetAspectRatio).toInt()
    } else {
        // 현재 이미지가 더 높음 → 위아래 잘라야 함
        cropWidth = width
        cropHeight = (width / targetAspectRatio).toInt()
    }

    val startX = ((width - cropWidth) / 2).coerceAtLeast(0)
    val startY = ((height - cropHeight) / 2).coerceAtLeast(0)

    val safeWidth = minOf(cropWidth, width - startX)
    val safeHeight = minOf(cropHeight, height - startY)

    return Bitmap.createBitmap(bitmap, startX, startY, safeWidth, safeHeight)
}


fun createBitmapFromGLSurface(x: Int, y: Int, w: Int, h: Int, gl: GL10): Bitmap? {
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

    val currentAspectRatio = w.toFloat() / h.toFloat()

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