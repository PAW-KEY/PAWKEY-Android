package com.paw.key.presentation.ui.detail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.DokiButton
import com.paw.key.core.designsystem.component.SubChip
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.component.UrlImage
import com.paw.key.core.designsystem.component.walk.WalkReviewInfoHolder
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.detail.component.DetailImageHolder
import com.paw.key.presentation.ui.detail.component.DetailTopReview
import com.paw.key.presentation.ui.detail.component.DokiDeleteButton
import com.paw.key.presentation.ui.detail.component.FilterChipDivider

@Composable
fun DetailRoute(
    paddingValues: PaddingValues,
    navigateToSharedCourse: (routeId: String) -> Unit = {},
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DetailScreen(
        paddingValues = paddingValues,
        state = state,
        navigateToSharedCourse = navigateToSharedCourse
    )
}
@Composable
private fun DetailScreen(
    paddingValues: PaddingValues,
    state: DetailState,
    navigateToSharedCourse: (routeId: String) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    val maxVisibleItems = 5
    val visibleItems = if (isExpanded) state.postDetail.categoryTagTexts else state.postDetail.categoryTagTexts.take(maxVisibleItems)
    val hiddenCount = state.postDetail.categoryTagTexts.size - maxVisibleItems

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        TopBar(
            title = "루트 상세정보",
            thickness = 2
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            UrlImage(
                url = state.postDetail.routeDisplay.routeImageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .align(Alignment.TopCenter)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                    .verticalScroll(rememberScrollState())
                    .dropShadow(
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                        shadow = Shadow(
                            radius = 19f.dp,
                            alpha = 0.1f,
                            color = Color(0xff000000),
                        )
                    )
                    .background(
                        color = PawKeyTheme.colors.background,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            ) {
                Text(
                    text = state.postDetail.title,
                    style = PawKeyTheme.typography.header3,
                    color = PawKeyTheme.colors.contents,
                    modifier = Modifier.padding(16.dp)
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = PawKeyTheme.colors.defaultButton,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    UrlImage(
                        url = state.postDetail.authorInfo.petProfileImage,
                        modifier = Modifier
                            .size(43.dp)
                            .clip(RoundedCornerShape(50.dp))
                    )

                    Text(
                        text = state.postDetail.authorInfo.petName,
                        style = PawKeyTheme.typography.subTitle,
                        color = PawKeyTheme.colors.defaultDark
                    )
                }

                HorizontalDivider(
                    thickness = 1.dp,
                    color = PawKeyTheme.colors.defaultButton,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                )

                WalkReviewInfoHolder(
                    icon = R.drawable.ic_walk_review_location,
                    content = state.postDetail.routeDisplay.locationText,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp)
                )

                WalkReviewInfoHolder(
                    icon = R.drawable.ic_walk_review_time,
                    content = state.postDetail.routeDisplay.dateTimeText,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )

                WalkReviewInfoHolder(
                    icon = R.drawable.ic_walk_review_course_info,
                    content = state.postDetail.routeDisplay.metaTagTexts.joinToString(separator = " | "),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .animateContentSize(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    visibleItems.forEach { item ->
                        SubChip(
                            text = item,
                            isActionChip = true,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(11.dp))


                if (!isExpanded) {
                    FilterChipDivider(
                        hiddenCount = hiddenCount,
                        onClick = { isExpanded = !isExpanded },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(
                            color = PawKeyTheme.colors.defaultButton,
                        )
                )

                DetailImageHolder(
                    imageUrls = state.postDetail.walkImages,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 20.dp, bottom = 12.dp)
                )

                Text(
                    text = state.postDetail.description,
                    color = PawKeyTheme.colors.contents,
                    style = PawKeyTheme.typography.bodyDefault,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(
                            color = PawKeyTheme.colors.defaultButton,
                        )
                )

                DetailTopReview(
                    reviewData = state.reviewDetail,
                    isShared = state.postDetail.isPublic
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(
                            color = PawKeyTheme.colors.defaultButton,
                        )
                )

                Spacer(
                    modifier = Modifier.height(40.dp)
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (state.postDetail.isMine) {
                        DokiDeleteButton(
                            text = "삭제하기",
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        )
                        DokiButton(
                            text = "수정하기",
                            enabled = true,
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        DokiButton(
                            text = "해당 루트로 산책하기",
                            enabled = true,
                            onClick = {
                                navigateToSharedCourse(state.postDetail.routeDisplay.routeId.toString())
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}



@Preview
@Composable
private fun DetailScreenPreview() {
    PawKeyTheme {
        DetailScreen(
            paddingValues = PaddingValues(),
            state = DetailState()
        )
    }
}