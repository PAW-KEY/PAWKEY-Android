package com.paw.key.presentation.ui.onboard.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Preview(showBackground = true)
@Composable
private fun PreviewOnboardPager() {
    PawKeyTheme {
        OnboardPager(
            jobList = listOf(
                OnboardingPosting(
                    title = "우리의 강아지를 위한 산책,\nPAWKEY와 함께해요!",  // 원본 텍스트로 변경
                    subtitle = "",
                    backImg = R.drawable.onboard1
                ),
            )

        )
    }
}

@Composable
fun OnboardPager(
    jobList: List<OnboardingPosting>,
) {
    val pageCount = jobList.size
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(620.dp)
            .background(
                color = PawKeyTheme.colors.white1,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            val job = jobList[page]
            OnboardingListItem(
                title = job.title,
                subtitle = job.subtitle,
                backImg = job.backImg
            )
        }

        AnimatedPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            activeColor = PawKeyTheme.colors.green500,
            inactiveColor = PawKeyTheme.colors.green200
        )
    }
}

@Composable
fun OnboardingListItem(
    title: String,
    subtitle: String,
    backImg: Int,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = PawKeyTheme.colors.white1,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
            .clip(
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
    ) {

        Image(
            painter = painterResource(id = backImg),
            contentDescription = "sibal",
            modifier = Modifier
                .fillMaxSize()
                .height(596.dp)
                .align(Alignment.Center),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .padding(top = 80.dp, start = 24.dp, end = 24.dp)
                .zIndex(2F)
        ) {
            val annotatedTitle = if (title.contains("PAWKEY")) {
                buildAnnotatedString {
                    val pawkeyStart = title.indexOf("PAWKEY")
                    val pawkeyEnd = pawkeyStart + "PAWKEY".length

                    withStyle(style = SpanStyle(color = PawKeyTheme.colors.black)) {
                        append(title.substring(0, pawkeyStart))
                    }

                    withStyle(style = SpanStyle(color = PawKeyTheme.colors.green500)) {
                        append("PAWKEY")
                    }

                    withStyle(style = SpanStyle(color = PawKeyTheme.colors.black)) {
                        append(title.substring(pawkeyEnd))
                    }
                }
            } else {
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = PawKeyTheme.colors.green500)) {
                        append(title)
                    }
                }
            }
            Text(
                text = annotatedTitle,
                style = PawKeyTheme.typography.head24B.copy(lineHeight = 36.sp),
                color = PawKeyTheme.colors.green500,
                modifier = Modifier
            )

            if (subtitle.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = subtitle,
                    style = PawKeyTheme.typography.body16M,
                    color = PawKeyTheme.colors.gray400,
                )
            }
        }

    }
}

@Composable
fun AnimatedPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = Color.Blue,
    inactiveColor: Color = Color.Gray,
    indicatorWidth: Dp = 12.dp,
    indicatorHeight: Dp = 12.dp,
    spacing: Dp = 12.dp,
) {
    val density = LocalDensity.current
    val activeIndicatorWidth = 24.dp

    Canvas(
        modifier = modifier
            .height(indicatorHeight)
            .width(
                activeIndicatorWidth + (indicatorWidth * (pagerState.pageCount - 1)) +
                        spacing * (pagerState.pageCount - 1)
            )
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val indicatorWidthPx = with(density) { indicatorWidth.toPx() }
        val activeIndicatorWidthPx = with(density) { activeIndicatorWidth.toPx() }
        val indicatorHeightPx = with(density) { indicatorHeight.toPx() }
        val spacingPx = with(density) { spacing.toPx() }

        var startX = 0f

        for (i in 0 until pagerState.pageCount) {
            val isActive = i == pagerState.currentPage
            val currentWidth = if (isActive) activeIndicatorWidthPx else indicatorWidthPx

            drawRoundRect(
                color = if (isActive) activeColor else inactiveColor,
                topLeft = Offset(startX, (canvasHeight - indicatorHeightPx) / 2),
                size = Size(currentWidth, indicatorHeightPx),
                cornerRadius = CornerRadius(indicatorHeightPx / 2)
            )

            startX += currentWidth + spacingPx
        }
    }
}

data class OnboardingPosting(
    val title: String,
    val subtitle: String,
    val backImg: Int,
)