package com.paw.key.presentation.ui.mypage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.core.designsystem.component.CourseDetail
import com.paw.key.core.designsystem.component.ImageModal
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.designsystem.theme.White1
import com.paw.key.domain.model.entity.walklist.CategoryTop3Entity
import com.paw.key.presentation.ui.mypage.viewmodel.ArchivedDetailViewModel

@Composable
fun ArchivedDetailRoute(
    navigateUp: () -> Unit,
    navigateToSharedWalk: () -> Unit,
    routeId : Int,
    pageId : Int,
    modifier: Modifier = Modifier,
    viewModel: ArchivedDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Log.d("LaunchedEffect", "LaunchedEffect: $routeId")
        viewModel.getWalkDetail(2, 20)
        viewModel.getWalkTopPopular(2, 3)
    }

    ArchivedCourseDetailScreen(
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
fun ArchivedCourseDetailScreen(
    title : String,
    petName : String,
    date : String,
    location : String,
    isLike : Boolean,
    content : String,
    petProfileImage : String,
    routeMapImageUrl : String,
    categorySummary : List<String>,
    categoryTop3 : List<CategoryTop3Entity>,
    totalReviewCount : Int,
    walkingImageUrls : List<String>,
    clickImage : String,
    navigateUp: () -> Unit,
    navigateToSharedWalk: () -> Unit,
    onClickImage : (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isImageExpanded by remember { mutableStateOf(false) }

    Column (
        modifier = modifier
            .fillMaxSize()
            .background(color = White1)
    ) {
        TopBar(
            title = "내가 기록한 산책 루트",
            onBackClick = navigateUp
        )

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = White1)
            ) {
                item {
                    CourseDetail(
                        title = title,
                        petName = petName,
                        date = date,
                        location = location,
                        isLike = isLike,
                        content = content,
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

                    PawkeyButton(
                        text = "해당 루트로 산책하기",
                        enabled = true,
                        onClick = navigateToSharedWalk,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    )
                }
            }

            if (isImageExpanded) {
                ImageModal(
                    imageUrl = clickImage,
                    onDismiss = { isImageExpanded = false }
                )
            }
        }
    }
}

@Preview
@Composable
fun ArchivedCourseDetailPreview(){
    PawKeyTheme {
        ArchivedCourseDetailScreen(
            title = "홍대 주변 좋은 산책 코스",
            petName = "핑구",
            date = "2025/06/30",
            location = "홍대입구역",
            isLike = true,
            content = "산책로가 깨끗하고 벚꽃이 예뻐요!",
            petProfileImage = "https://pawkey-server.com/image/profile.png",
            routeMapImageUrl = "https://pawkey-server.com/image/map.png",
            categoryTop3 = listOf(
                CategoryTop3Entity(1, "안전", 2, "차량이 거의 다니지 않아요", 1, 42),
                CategoryTop3Entity(1, "안전", 3, "킥보드가 거의 없어요", 2, 37),
                CategoryTop3Entity(2, "편리성", 7, "조명이 밝아요", 3, 35)
            ),
            totalReviewCount = 42,
            walkingImageUrls = listOf(
                "https://pawkey-server.com/image/walk1.jpg",
                "https://pawkey-server.com/image/walk2.jpg"
            ),
            categorySummary = listOf("안전", "편리성"),
            clickImage = "",
            onClickImage = {},
            navigateUp = {},
            navigateToSharedWalk = {},
        )
    }
}