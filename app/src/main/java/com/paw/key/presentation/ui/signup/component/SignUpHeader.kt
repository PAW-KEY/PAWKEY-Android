package com.paw.key.presentation.ui.signup.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpHeader() {
    PawKeyTheme {
        SignUpHeader(
            progress = 0.5F,
            title = "회원가입",
            onBackClick = {}
        )
    }
}

@Composable
fun SignUpHeader(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    progress: Float = 1f,
) {
    val stepProgress = (progress / 3f).coerceIn(0f, 1f)

    val animatedProgress by animateFloatAsState(
        targetValue = stepProgress,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "progress_animation"
    )

    Column (
        modifier = modifier
            .fillMaxWidth()
            .background(color = PawKeyTheme.colors.background)
    ){
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_black),
                contentDescription = "back",
                tint = PawKeyTheme.colors.contents,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .noRippleClickable(onBackClick)
            )

            Text(
                text = title,
                color = PawKeyTheme.colors.contents,
                style = PawKeyTheme.typography.subTitle,
                textAlign = TextAlign.Center
            )
        }

        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            color = PawKeyTheme.colors.primary,
            trackColor = PawKeyTheme.colors.defaultMiddle,
            strokeCap = StrokeCap.Square,
            gapSize = 0.dp
        )
    }
}