package com.paw.key.presentation.ui.mypage

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.CourseDetail
import com.paw.key.core.designsystem.component.ImageModal
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.designsystem.theme.White1
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.domain.model.entity.walklist.CategoryTop3Entity
import com.paw.key.presentation.ui.mypage.viewmodel.SavedDetailViewModel
import kotlinx.coroutines.flow.first

@Composable
fun SavedDetailRoute(
    navigateUp: () -> Unit,
    navigateToSharedWalk: () -> Unit,
    routeId : Int,
    pageId : Int,
    modifier: Modifier = Modifier,
    viewModel: SavedDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val userId = PreferenceDataStore.getUserId()

    LaunchedEffect(Unit) {
        Log.e("SavedDetailRoute", "userId: ${userId.first()}, pageId: $pageId")
        viewModel.getWalkDetail(userId.first(), pageId)
        viewModel.getWalkTopPopular(userId.first(), routeId)
    }

    SavedCourseDetailScreen(
        title = state.postTitle,
        petName = state.petName,
        date = state.createdAt,
        location = state.regionName,
        isLike = state.isLiked,
        content = state.postContent,
        petProfileImage = state.petProfileImage,
        routeMapImageUrl = state.routeMapImageUrl,
        categorySummary = state.categorySummary,
        categoryTop3 = state.categoryTop3,
        totalReviewCount = state.totalReviewCount,
        walkingImageUrls = state.walkingImageUrls,
        clickImage = state.clickImage,

        onClickImage = {
            viewModel.onClickImage(it)
        },

        navigateUp = navigateUp,
        navigateToSharedWalk = navigateToSharedWalk,
        modifier = modifier
    )
}

@Composable
fun SavedCourseDetailScreen(
    title: String,
    petName: String,
    date: String,
    location: String,
    isLike: Boolean,
    content: String,
    petProfileImage: String,
    routeMapImageUrl: String,
    categorySummary: List<String>,
    categoryTop3: List<CategoryTop3Entity>,
    totalReviewCount: Int,
    walkingImageUrls: List<String>,
    clickImage: String,
    navigateUp: () -> Unit,
    navigateToSharedWalk: () -> Unit,
    onClickImage: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isImageExpanded by remember { mutableStateOf(false) }
    val isLiked by remember { mutableStateOf(isLike) }
    val view = LocalView.current
    val statusBarColor = PawKeyTheme.colors.green500

    val context = LocalContext.current
    val window = (context as? Activity)?.window
    val previousNavBarColor = remember { window?.navigationBarColor }

    DisposableEffect(Unit) {
        window?.navigationBarColor = statusBarColor.toArgb()

        onDispose {
            // 화면에서 벗어날 때 원래 색으로 복원
            previousNavBarColor?.let {
                window?.navigationBarColor = it
            }
        }
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = White1)
    ) {
        TopBar(
            title = "내가 기록한 산책 루트",
            onBackClick = navigateUp
        )

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
                .zIndex(2F)
        ) {
            item {
                CourseDetail(
                    title = title,
                    petName = petName,
                    date = date,
                    Icon = if(isLiked) R.drawable.ic_heart_default else R.drawable.ic_heart_filled,
                    location = location,
                    content = content,
                    onClickLike = {},
                    petProfileImage = petProfileImage,
                    routeMapImageUrl = routeMapImageUrl,
                    categorySummary = categorySummary,
                    categoryTop3 = categoryTop3,
                    totalReviewCount = totalReviewCount,
                    walkingImageUrls = walkingImageUrls,
                    onImageClick = {
                        onClickImage(it)
                        isImageExpanded = true
                    }
                )

                Spacer(modifier = Modifier.height(36.dp))

                PawkeyButton(
                    text = "해당 루트로 산책하기",
                    enabled = true,
                    onClick = navigateToSharedWalk,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .background(color = PawKeyTheme.colors.green500)
                )
            }
        }
    }
    if (isImageExpanded) {
        ImageModal(
            imageUrl = clickImage,
            onDismiss = { isImageExpanded = false }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SavedCourseDetailPreview() {
    PawKeyTheme {
        SavedCourseDetailScreen(
            title = "한강 산책로",
            petName = "후추",
            date = "2025/06/02",
            location = "뚝섬유원지",
            isLike = true,
            content = "봄에 꽃이 만개한 산책로예요. 조용하고 평탄해서 걷기 좋아요.",
            petProfileImage = "https://pawkey-server.com/profile.jpg",
            routeMapImageUrl = "https://pawkey-server.com/map.jpg",
            categorySummary = listOf("풍경이 좋아요", "조용해요", "길이 깨끗해요"),
            categoryTop3 = listOf(
                CategoryTop3Entity(
                    categoryId = 1,
                    categoryName = "안전",
                    categoryOptionId = 2,
                    optionText = "차량이 거의 다니지 않아요",
                    rank = 1,
                    percentage = 42
                ),
                CategoryTop3Entity(
                    categoryId = 1,
                    categoryName = "안전",
                    categoryOptionId = 3,
                    optionText = "킥보드가 거의 없어요",
                    rank = 2,
                    percentage = 37
                ),
                CategoryTop3Entity(
                    categoryId = 2,
                    categoryName = "편리성",
                    categoryOptionId = 7,
                    optionText = "조명이 밝아요",
                    rank = 3,
                    percentage = 35
                )
            ),
            totalReviewCount = 42,
            walkingImageUrls = listOf(
                "https://pawkey-server.com/etc1.jpg",
                "https://pawkey-server.com/etc2.jpg"
            ),
            navigateUp = {},
            navigateToSharedWalk = {},
            onClickImage = { _ -> },
            modifier = Modifier,
            clickImage = ""
        )
    }
}
