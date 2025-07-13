package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.component.CourseCard
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme

// 코스 카드에서 사용할 데이터 모델
data class CourseCardData(
    val title: String,
    val petName: String,
    val date: String,
    val location: String,
    val distance: String,
    val time: String
)

@Composable
fun SavedCourseRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SavedCourseListScreen(
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        modifier = modifier
    )
}

@Composable
fun SavedCourseListScreen(
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val courseList = listOf(
        CourseCardData(
            title = "홍대 주변 좋은 산책 코스",
            petName = "초코",
            date = "2025/05/17",
            location = "홍대입구역",
            distance = "3km",
            time = "1시간 소요"
        ),
        CourseCardData(
            title = "한강 산책로",
            petName = "몽이",
            date = "2025/06/02",
            location = "뚝섬유원지",
            distance = "4.5km",
            time = "1시간 30분 소요"
        )
    )

    Column {
        TopBar(
            title = "저장한 산책 루트",
            onBackClick = navigateUp
        )

        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(PawKeyTheme.colors.white1)
        ) {
            item {
                courseList.forEach { course ->
                    CourseCard(
                        title = course.title,
                        petName = course.petName,
                        date = course.date,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedCourseListScreenPreview() {
    PawKeyTheme {
        SavedCourseListScreen(navigateUp = {}, navigateNext = {})
    }
}