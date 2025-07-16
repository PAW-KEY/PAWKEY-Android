package com.paw.key.presentation.ui.course.entire.tab.map.List

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
        TabListScreen(
            navigateToDetail = {},
            onClickLike = { _, _ -> }
        )
    }
}

@Composable
fun TapListRoute(
    navigateToDetail: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TapListViewModel = hiltViewModel(),
) {
    TabListScreen(
        modifier = modifier,
        navigateToDetail = navigateToDetail,
        viewModel = viewModel,
        onClickLike = { postId, isLiked ->
            viewModel.toggleLike(postId = postId, isLiked = isLiked)
        }
    )
}

@Composable
fun TabListScreen(
    navigateToDetail: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TapListViewModel = hiltViewModel(),
    onClickLike: (postId: Int, isLiked: Boolean) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val listState by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize()
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
                modifier = Modifier.noRippleClickable {
                    showBottomSheet = true
                }
            )
            OptionChip(
                text = if (viewModel.isFilterApplied()) "필터 적용됨" else "선택한 옵션이 없어요",
                isActionChip = true
            )
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
                        items(posts) { post ->
                            CourseCard(
                                postId = post.postId,
                                title = post.title,
                                petName = post.writer.petName,
                                createdAt = post.createdAt,
                                representativeImageUrl = post.representativeImageUrl,
                                petProfileImageUrl = post.writer.petProfileImageUrl,
                                descriptionTags = post.descriptionTags,
                                isLiked = post.isLike,
                                onClickLike = { isLiked ->
                                    onClickLike(post.postId, isLiked)
                                },
                                onClickItem = { navigateToDetail() }
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