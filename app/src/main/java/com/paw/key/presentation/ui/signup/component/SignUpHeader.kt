package com.paw.key.presentation.ui.signup.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpHeader() {
    PawKeyTheme {
        SignUpHeader(
            progress = 0.5F,
            title = "회원가입",
            subtitle = "견주님에 대해 알려주세요."
        )
    }
}

@Composable
fun SignUpHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    progress: Float = 1F,
) {

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "progress_animation"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(131.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1F)
                    .fillMaxSize()
            ) {
                Text(
                    text = title,
                    color = PawKeyTheme.colors.black,
                    style = PawKeyTheme.typography.body16Sb,
                    modifier = Modifier
                        .padding(top = 16.dp),
                )

                Spacer(modifier = Modifier.weight(1F))

                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = PawKeyTheme.colors.green500,
                    trackColor = PawKeyTheme.colors.gray100,
                    strokeCap = StrokeCap.Square,
                    gapSize = 0.dp,
                    drawStopIndicator = {}
                )
            }
        }
        Text(
            text = subtitle,
            color = PawKeyTheme.colors.black,
            style = PawKeyTheme.typography.head22Sb,
            modifier = Modifier
                .padding(top = 36.dp)
                .padding(horizontal = 16.dp)
        )
    }
}