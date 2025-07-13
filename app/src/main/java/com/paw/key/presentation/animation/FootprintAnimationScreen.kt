package com.paw.key.presentation.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.presentation.ui.main.state.MainContract
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FootprintAnimationScreen(
    footprints: PersistentList<MainContract.Footprint>,
    onAnimationFinished: (MainContract.Footprint) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color.Transparent
            )
    ) {
        footprints.forEach { footprint ->
            AnimatedFootprint(
                footprint = footprint,
                onAnimationFinished = {
                    onAnimationFinished(footprint)
                }
            )
        }
    }
}


@Composable
fun AnimatedFootprint(footprint: MainContract.Footprint, onAnimationFinished: () -> Unit) {
    val footprintSize = 36.dp
    val alpha = remember {
        Animatable(0f)
    }

    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(footprint.id) {
        // 발바닥 스케일 조절 애니메이션
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 300)
            )
        }
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 300)
            )
        }

        delay(800) // 발바닥이 완전히 나타난 후 잠시 유지되는 시간

        launch {
            alpha.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 500)
            )
        }
        launch {
            scale.animateTo(
                targetValue = 0.8f,
                animationSpec = tween(durationMillis = 500)
            )
        }

        onAnimationFinished()
    }

    Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_walk_fill),
        contentDescription = stringResource(R.string.animation_footprint_description),
        modifier = Modifier
            .offset(
                x = footprint.position.x.toDp() - footprintSize / 2,
                y = footprint.position.y.toDp() - footprintSize / 2
            )
            .size(footprintSize)
            .graphicsLayer(
                alpha = alpha.value,
                scaleX = scale.value,
                scaleY = scale.value
            )
    )
}

@Composable
fun Float.toDp(): Dp {
    return with(LocalDensity.current) { this@toDp.toDp() }
}