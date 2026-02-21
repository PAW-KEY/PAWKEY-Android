package com.paw.key.presentation.ui.course.walkcourse.walkprepare.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
    title: String = "발이 차가워요.. 잠깐 다녀와요!",
    subTitle: String = "실외 금지! 실내 놀이로 대체",
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = PawKeyTheme.colors.background,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = PawKeyTheme.colors.primary,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(start = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "오늘의 산책 TIP",
                style = PawKeyTheme.typography.buttonSmall,
                color = PawKeyTheme.colors.defaultMiddle,
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = title,
                style = PawKeyTheme.typography.subTitle,
                color = PawKeyTheme.colors.contents,
            )


            Text(
                text = subTitle,
                style = PawKeyTheme.typography.bodySmall,
                color = PawKeyTheme.colors.contents
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))

        Image(
            painter = painterResource(R.drawable.img_walk_info),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 8.dp, end = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WalkPrepareWeatherInfoPreview() {
    PawKeyTheme {
        WalkPrepareWeatherInfo(
            subTitle = "10분 내 짧은 산책 / 패딩과 신발 필수"
        )
    }
}