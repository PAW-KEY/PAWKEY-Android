package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.component.CourseDetail
import com.paw.key.core.designsystem.component.ImageModal
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.designsystem.theme.White1

@Composable
fun SavedDetailRoute(
    navigateUp: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SavedCourseDetailScreen(
        navigateUp = navigateUp,
        modifier = modifier
    )
}

@Composable
fun SavedCourseDetailScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    var isImageExpanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        TopBar(title = "내가 저장한 산책 루트",
            onBackClick = { navigateUp() }
        )

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
                    onImageClick = { isImageExpanded = true } // ← 콜백 전달
                )
                PawkeyButton(
                    text = "해당 루트로 산책하기",
                    enabled = true,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }
        }

        if (isImageExpanded) {
            ImageModal(
                imageUrl = "https://pawkey-server.com/image.jpg",
                onDismiss = { isImageExpanded = false }
            )
        }
    }
}

@Preview
@Composable
fun SavedCourseDetailPreview(){
    PawKeyTheme {
        SavedCourseDetailScreen(navigateUp = {})
    }
}