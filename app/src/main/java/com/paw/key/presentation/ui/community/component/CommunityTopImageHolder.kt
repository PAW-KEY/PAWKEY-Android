package com.paw.key.presentation.ui.community.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.component.PageIndicator
import com.paw.key.core.designsystem.component.UrlImage
import com.paw.key.core.designsystem.theme.PawKeyTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun CommunityTopImageHolder(
    imageList: ImmutableList<String>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { imageList.size })

    Box(
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(360f/141f)
        ) { page ->
            UrlImage(
                url = imageList[page],
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        PageIndicator(
            numberOfPages = imageList.size,
            selectedPage = pagerState.currentPage,
            selectedColor = PawKeyTheme.colors.primary,
            defaultColor = PawKeyTheme.colors.defaultMiddle,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}

@Preview
@Composable
private fun CommunityTopImageHolderPreview() {
    PawKeyTheme {
        CommunityTopImageHolder(
            imageList = persistentListOf("", "", "")
        )
    }
}