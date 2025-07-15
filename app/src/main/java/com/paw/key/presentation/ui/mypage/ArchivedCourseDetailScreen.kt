package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
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
import com.paw.key.presentation.ui.mypage.state.ArchivedDetailContract
import com.paw.key.presentation.ui.mypage.viewmodel.ArchivedDetailViewModel

@Composable
fun ArchivedDetailRoute(
    navigateUp: () -> Unit,
    navigateToSharedWalk: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArchivedDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    ArchivedCourseDetailScreen(
        state = state.value,
        navigateUp = navigateUp,
        navigateToSharedWalk = navigateToSharedWalk,
        modifier = modifier
    )
}

@Composable
fun ArchivedCourseDetailScreen(
    state: ArchivedDetailContract.ArchivedDetailState,
    navigateUp: () -> Unit,
    navigateToSharedWalk: () -> Unit,
    modifier: Modifier = Modifier
){
    var isImageExpanded by remember { mutableStateOf(false) }

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
                    title = "한강 산책로",
                    petName = "후추",
                    date = "2025/06/02",
                    location = "뚝섬유원지",
                    distance = "4.5km",
                    option = listOf("풍경이 좋아요", "조용해요", "길이 깨끗해요"),
                    time = "1시간 30분 소요",
                    onImageClick = { isImageExpanded = true } // ← 콜백 전달
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
                imageUrl = state.imageUrl,
                onDismiss = { isImageExpanded = false }
            )
        }
    }
}

@Preview
@Composable
fun ArchivedCourseDetailPreview(){
    PawKeyTheme {
        ArchivedCourseDetailScreen(state = ArchivedDetailContract.ArchivedDetailState(
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
            navigateToSharedWalk = {}
        )
    }
}