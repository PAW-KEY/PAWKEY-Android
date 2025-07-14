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
import com.paw.key.presentation.ui.mypage.state.SavedDetailContract
import com.paw.key.presentation.ui.mypage.viewmodel.SavedDetailViewModel

@Composable
fun SavedDetailRoute(
    navigateUp: () -> Unit,
    navigateToWalk: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SavedDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    SavedCourseDetailScreen(
        state = state.value,
        navigateUp = navigateUp,
        navigateToWalk = navigateToWalk,
        modifier = modifier
    )
}

@Composable
fun SavedCourseDetailScreen(
    state: SavedDetailContract.SavedDetailState,
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
                        title = "한강 산책로",
                        petName = "후추",
                        date = "2025/06/02",
                        location = "뚝섬유원지",
                        distance = "4.5km",
                        option = listOf("풍경이 좋아요", "조용해요", "길이 깨끗해요"),
                        time = "1시간 30분 소요",
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
                imageUrl = state.imageUrl,
                onDismiss = { isImageExpanded = false }
            )
        }
    }
}


@Preview
@Composable
fun SavedCourseDetailPreview(){
    PawKeyTheme {
        SavedCourseDetailScreen(state = SavedDetailContract.SavedDetailState(
            title = "한강 산책로",
            petName = "후추",
            date = "2025/06/02",
            location = "뚝섬유원지",
            distance = "4.5km",
            time = "1시간 30분 소요",
            option = listOf("풍경이 좋아요", "조용해요", "길이 깨끗해요"),
            imageUrl = "https://pawkey-server.com/image.jpg"
        ),
            navigateUp = {},
            navigateToWalk = {}
        )
    }
}