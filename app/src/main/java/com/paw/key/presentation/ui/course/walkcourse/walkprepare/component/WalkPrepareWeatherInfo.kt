package com.paw.key.presentation.ui.course.walkcourse.walkprepare.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun WalkPrepareWeatherInfo(
    modifier: Modifier = Modifier,
    title: String = "숨이 얼어붙어요...오늘은 나가지말아요",
    subTitle: String = "실외 금지! 실내 놀이로 대체",
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = PawKeyTheme.colors.primary)
    ) {
        Image(
            painter = painterResource(R.drawable.img_walk_info),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp)
                .size(100.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth()
                .padding(start = 110.dp, end = 20.dp, top = 24.dp, bottom = 24.dp)
        ) {
            // Todo: body bold로 변경
            Text(
                text = title,
                style = PawKeyTheme.typography.bodyActive,
                color = PawKeyTheme.colors.background,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subTitle,
                style = PawKeyTheme.typography.subButtonDefault,
                color = PawKeyTheme.colors.defaultBright
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WalkPrepareWeatherInfoPreview() {
    PawKeyTheme {
        WalkPrepareWeatherInfo()
    }
}