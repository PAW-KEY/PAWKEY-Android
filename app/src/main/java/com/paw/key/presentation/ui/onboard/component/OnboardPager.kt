package com.paw.key.presentation.ui.onboard.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paw.key.R
import com.paw.key.core.designsystem.component.PageIndicator
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Preview(showBackground = true)
@Composable
private fun PreviewOnboardPager() {
    PawKeyTheme {
        OnboardPager(
            jobList = listOf(
                OnboardingPosting(
                    title = "우리 강아지를 위한 산책",
                    subtitle = "DOGKY와 즐거운 산책을 시작해봐요!",
                    backImg = R.drawable.doki_welcome,
                    isLarge = false
                ),
            )
        )
    }
}

@Composable
fun OnboardPager(
    jobList: List<OnboardingPosting>,
    modifier: Modifier = Modifier,
) {
    val pageCount = jobList.size
    val pagerState = rememberPagerState(pageCount = { pageCount })

    val currentPage = pagerState.settledPage
    val currentItem = jobList.getOrNull(currentPage)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = PawKeyTheme.colors.white1)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            OnboardingListItem(
                backImg = jobList[page].backImg,
                isLarge = jobList[page].isLarge,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Crossfade(targetState = currentItem?.title) { title ->
                title?.let {
                    Text(
                        text = it,
                        style = PawKeyTheme.typography.header2.copy(lineHeight = 36.sp),
                        color = PawKeyTheme.colors.contents,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Crossfade(targetState = currentItem?.subtitle) { subtitle ->
                subtitle?.takeIf { it.isNotEmpty() }?.let {
                    Text(
                        text = it,
                        style = PawKeyTheme.typography.subButtonDefault,
                        color = PawKeyTheme.colors.defaultDark,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        PageIndicator(
            numberOfPages = pageCount,
            selectedPage = currentPage,
            selectedColor = PawKeyTheme.colors.primary,
            defaultColor = PawKeyTheme.colors.gray100,
            space = 4.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 25.dp)
        )
    }
}

@Composable
fun OnboardingListItem(
    backImg: Int,
    modifier: Modifier = Modifier,
    isLarge: Boolean = false,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .fillMaxSize()
            .background(PawKeyTheme.colors.white1)
    ) {
        Image(
            painter = painterResource(id = backImg),
            contentDescription = null,
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(bottom = 20.dp)
                .then(
                    if (isLarge) Modifier.fillMaxSize()
                    else Modifier.aspectRatio(375f / 332f)
                ),
            contentScale = if (isLarge) ContentScale.Crop else ContentScale.Fit
        )
    }
}

data class OnboardingPosting(
    val title: String,
    val subtitle: String,
    val backImg: Int,
    val isLarge: Boolean = false
)