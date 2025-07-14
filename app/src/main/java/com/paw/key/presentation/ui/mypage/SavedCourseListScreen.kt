package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.core.designsystem.component.CourseCard
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.mypage.state.SavedListContract
import com.paw.key.presentation.ui.mypage.state.SavedListContract.CourseCardData
import com.paw.key.presentation.ui.mypage.viewmodel.SavedListViewModel

@Composable
fun SavedCourseRoute(
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SavedListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    SavedCourseListScreen(
        state = state.value,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        modifier = modifier
    )
}

@Composable
fun SavedCourseListScreen(
    state: SavedListContract.SavedListState,
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
            itemsIndexed(
                items = courseList
            ) { _, item ->
                CourseCard(
                    title = item.title,
                    petName = item.petName,
                    date = item.date,
                    onCLickItem = navigateNext
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedCourseListScreenPreview() {
    PawKeyTheme {
        SavedCourseListScreen(state = SavedListContract.SavedListState(
                courseList = listOf(
                    CourseCardData(
                        title = "예시 산책로",
                        petName = "하루",
                        date = "2025/01/01",
                        location = "강남",
                        distance = "2.3km",
                        time = "45분"
                    )
                )
            ),
            navigateUp = {},
            navigateNext = {}
        )
    }
}