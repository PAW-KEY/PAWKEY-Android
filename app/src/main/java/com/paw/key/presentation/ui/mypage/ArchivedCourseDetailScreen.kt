package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.component.CourseDetail
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.mypage.ArchivedCourseDetailScreen

@Composable
fun ArchivedDetailRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ArchivedCourseDetailScreen(
        navigateUp = navigateUp,
        modifier = modifier
    )
}
@Composable
fun ArchivedCourseDetailScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CourseDetail(
            title = "한강 산책로",
            petName = "후추",
            date = "2025/06/02",
            location = "뚝섬유원지",
            distance = "4.5km",
            option = listOf("풍경이 좋아요", "조용해요", "길이 깨끗해요"),
            time = "1시간 30분 소요"
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

@Preview
@Composable
fun ArchivedCourseDetailPreview(){
    PawKeyTheme {
        ArchivedCourseDetailScreen(navigateUp = {})
    }
}
