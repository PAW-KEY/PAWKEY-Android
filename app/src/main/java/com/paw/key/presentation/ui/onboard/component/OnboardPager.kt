package com.paw.key.presentation.ui.onboard.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
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
                    backImg = R.drawable.img_onboarding_1
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

    val currentPage = pagerState.currentPage
    val currentItem = jobList.getOrNull(currentPage)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenHeightDp.dp * 0.7f)
            .background(color = PawKeyTheme.colors.white1)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            OnboardingListItem(backImg = jobList[page].backImg)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Crossfade(targetState = currentItem?.title) { title ->
                title?.let {
                    Text(
                        text = it,
                        style = PawKeyTheme.typography.head24B.copy(lineHeight = 36.sp),
                        color = PawKeyTheme.colors.black,
                    )
                }
            }

            Crossfade(targetState = currentItem?.subtitle) { subtitle ->
                subtitle?.takeIf { it.isNotEmpty() }?.let {
                    Text(
                        text = it,
                        style = PawKeyTheme.typography.body16M,
                        color = PawKeyTheme.colors.gray400,
                    )
                }
            }
        }

        PageIndicator(
            numberOfPages = pageCount,
            selectedPage = currentPage,
            selectedColor = PawKeyTheme.colors.primary,
            defaultColor = PawKeyTheme.colors.gray100,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}

@Composable
fun OnboardingListItem(backImg: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(PawKeyTheme.colors.white1)
    ) {
        Image(
            painter = painterResource(id = backImg),
            contentDescription = null,
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .size(360.dp),
        )
    }
}

data class OnboardingPosting(
    val title: String,
    val subtitle: String,
    val backImg: Int,
)