package com.paw.key.presentation.ui.course.entire.tab.map.List

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.CourseCard
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable
import com.paw.key.presentation.ui.course.entire.tab.map.List.viewmodel.TapListViewModel

@Preview(showBackground = true)
@Composable
private fun PreviewTabListScreen() {
    PawKeyTheme {
    }
}

@Composable
fun TapListRoute(
    navigateToDetail: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TapListViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.loadInitialPosts()
        viewModel.loadFilterOptions()
    }

    TabListScreen(
        modifier = modifier,
        navigateToDetail = { postId, routeId ->
            navigateToDetail(postId, routeId)
        },
        viewModel = viewModel
    )
}

@Composable
fun TabListScreen(
    navigateToDetail: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TapListViewModel = hiltViewModel(),
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val listState by viewModel.state.collectAsStateWithLifecycle()
    val filterValid = listState.isValid

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = PawKeyTheme.colors.white1)
                .padding(horizontal = 16.dp, vertical = 11.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_course_optin_filter),
                contentDescription = "filter",
                tint = Color.Unspecified,
                modifier = Modifier
                    .noRippleClickable {
                        showBottomSheet = true
                    }
            )

            if (filterValid) {
                if (listState.selectedSortTime.isNotEmpty()) {
                    OptionChip(text = listState.selectedSortTime)
                }
                if (listState.selectedMood.isNotEmpty()) {
                    OptionChip(text = listState.selectedMood)
                }
                if (listState.selectedDogFriend.isNotEmpty()) {
                    OptionChip(text = listState.selectedDogFriend)
                }
                listState.selectedSafety.forEach {
                    OptionChip(text = it)
                }
                listState.selectedConvenience.forEach {
                    OptionChip(text = it)
                }
                listState.selectedEnvironment.forEach {
                    OptionChip(text = it)
                }
            } else {
                OptionChip(
                    text = "선택한 옵션이 없어요",
                    isActionChip = true
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(PawKeyTheme.colors.white2)
                .padding(bottom = 36.dp)
        ) {
            if (listState.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = PawKeyTheme.colors.green500
                        )
                    }
                }
            } else {
                listState.postsResult?.let { postsResult ->
                    val posts = postsResult.posts
                    if (posts.isNotEmpty()) {
                        items(
                            items = posts,
                            key = { post -> post.postId }
                        ) { post ->
                            CourseCard(
                                title = post.title,
                                petName = post.writer.petName,
                                representativeImageUrl = post.representativeImageUrl,
                                petProfileImageUrl = post.writer.petProfileImageUrl,
                                descriptionTags = post.descriptionTags,
                                postId = post.postId,
                                createdAt = post.createdAt,
                                isLiked = post.isLike,
                                onClickItem = {
                                    navigateToDetail(post.postId, post.routeId)
                                },
                                onClickLike = { newLikeState ->
                                    viewModel.toggleLike(post.postId, newLikeState)
                                }
                            )
                        }
                    } else {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "조건에 맞는 게시물이 없습니다",
                                    style = PawKeyTheme.typography.body14R,
                                    color = PawKeyTheme.colors.gray500
                                )
                            }
                        }
                    }
                } ?: run {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "데이터를 불러오는 중...",
                                style = PawKeyTheme.typography.body14R,
                                color = PawKeyTheme.colors.gray500
                            )
                        }
                    }
                }
            }
        }

        if (showBottomSheet) {
            CourseOptionBottomSheet(
                viewModel = viewModel,
                onDismissRequest = { showBottomSheet = false }
            )
        }
    }
}

@Composable
private fun OptionChip(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isActionChip: Boolean = false,
) {
    Box(
        modifier = modifier
            .background(
                color = PawKeyTheme.colors.white1,
                shape = RoundedCornerShape(60.dp)
            )
            .border(
                width = 1.dp,
                color = if (isActionChip) PawKeyTheme.colors.gray200 else PawKeyTheme.colors.gray50,
                shape = RoundedCornerShape(60.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 7.dp)
    ) {
        Text(
            text = text,
            color = if (isActionChip) PawKeyTheme.colors.black else PawKeyTheme.colors.gray200,
            style = PawKeyTheme.typography.caption12R
        )
    }
}