package com.paw.key.presentation.ui.mypage

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.paw.key.presentation.ui.mypage.viewmodel.SavedDetailViewModel

@Composable
fun SavedDetailRoute(
    navigateUp: () -> Unit,
    navigateToWalk: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SavedDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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

        navigateUp = navigateUp,
        navigateToWalk = navigateToWalk,
        modifier = modifier
    )
}

@Composable
fun SavedCourseDetailScreen(
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

    navigateUp: () -> Unit,
    navigateToWalk: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isImageExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "내가 저장한 산책 루트",
            onBackClick = navigateUp
        )

        Box(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
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
                            isImageExpanded = true
                        }
                    )

                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider(
                        thickness = 8.dp,
                        color = PawKeyTheme.colors.gray50,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(120.dp))
                }
            }



            PawkeyButton(
                text = "해당 루트로 산책하기",
                enabled = true,
                onClick = {
                    navigateToWalk()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 60.dp)
            )
        }

        if (isImageExpanded) {
            ImageModal(
                imageUrl = routeMapImageUrl,
                onDismiss = { isImageExpanded = false }
            )
        }
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
            navigateToWalk = {}
        )
    }
}
