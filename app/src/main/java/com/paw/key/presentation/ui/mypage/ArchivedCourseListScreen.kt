package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.component.CourseCard
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.designsystem.theme.White1

@Composable
fun ArchivedCourseRoute(
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ArchivedCourseListScreen(
        navigateUp = navigateUp,
        navigateNext = navigateNext,
    modifier = modifier
    )
}

@Composable
fun ArchivedCourseListScreen(
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
            title = "내가 기록한 산책 루트",
            onBackClick = navigateUp
        )

        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(color = White1)
        ) {
            item {
                courseList.forEach { course ->
                    CourseCard(
                        title = course.title,
                        petName = course.petName,
                        date = course.date,
                        onCLickItem = navigateNext
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ArchivedCourseListScreenPreview() {
    PawKeyTheme {
        ArchivedCourseListScreen(navigateUp = {}, navigateNext = {})
    }
}