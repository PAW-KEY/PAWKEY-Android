package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun HomeBanner(
    modifier: Modifier = Modifier,
    firstText : String = "내 반려견은 어떤 성향을 가지고 있을까?\n",
    secondText : String = "간단한 테스트를 통해 반려견 성향을 알아보세요!"
) {
    Box (
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PawKeyTheme.colors.primary
            )
    ) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp, top = 20.dp, bottom = 20.dp)
                .align(Alignment.CenterStart),
            text = buildAnnotatedString {
                withStyle(style = PawKeyTheme.typography.bodyBold.toSpanStyle()) {
                    append(firstText)
                }
                withStyle(style = PawKeyTheme.typography.subButtonDefault.toSpanStyle()) {
                    append(secondText)
                }
            },
            color = PawKeyTheme.colors.background
        )

        Image(
            painter = painterResource(R.drawable.img_walk_info),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp)
                .size(87.dp,76.dp)
        )
    }
}

@Preview
@Composable
private fun HomeBannerPreview() {
    PawKeyTheme {
        HomeBanner()
    }
}
