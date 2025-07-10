package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.component.CourseCard
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun ArchivedCourseRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ArchivedCourseListScreen(
        navigateUp = navigateUp,
        modifier = modifier
    )
}

@Composable
fun ArchivedCourseListScreen(
    navigateUp: () -> Unit,
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

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(Color(0xFFF7F7F7))
    ) {
        //상단의 헤더바
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_black),
                contentDescription = "뒤로가기"
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "내가 기록한 산책 루트",
                    style = PawKeyTheme.typography.head22B
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
        }

        courseList.forEach { course ->
            CourseCard(
                title = course.title,
                petName = course.petName,
                date = course.date,
                location = course.location,
                distance = course.distance,
                time = course.time
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArchivedCourseListScreenPreview() {
    PawKeyTheme {
        ArchivedCourseListScreen(navigateUp = {})
    }
}