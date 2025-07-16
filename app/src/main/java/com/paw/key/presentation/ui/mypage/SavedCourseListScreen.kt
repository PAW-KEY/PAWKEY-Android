package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.background
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
import com.paw.key.presentation.ui.mypage.state.SavedListState
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
    state: SavedListState,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                items = state.courseList
            ) { _, item ->
                CourseCard(
                    postId = item.postId.toInt(),
                    title = item.title,
                    createdAt = item.createdAt,
                    representativeImageUrl = item.representativeImageUrl,
                    petName = item.writer.first().petName,
                    petProfileImageUrl = item.writer.first().petProfileImageUrl,
                    descriptionTags = item.descriptionTags,
                    isLiked = item.isLiked,
                    onClickItem = navigateNext,
                    onCLickItem = {},
                    isRecord = true,
                    date = "년도/월/일"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedCourseListScreenPreview() {
    PawKeyTheme {
        SavedCourseListScreen(state = SavedListState(),
            navigateUp = {},
            navigateNext = {}
        )
    }
}