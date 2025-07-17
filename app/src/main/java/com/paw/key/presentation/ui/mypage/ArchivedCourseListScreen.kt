package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.core.designsystem.component.CourseCard
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.presentation.ui.mypage.state.ArchivedListState
import com.paw.key.presentation.ui.mypage.viewmodel.ArchivedListViewModel
import kotlinx.coroutines.flow.first

@Composable
fun ArchivedCourseRoute(
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArchivedListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val userId = PreferenceDataStore.getUserId()
    LaunchedEffect(Unit) {
        // Todo : userId 네비게이션 연결
        viewModel.getArchivedList(userId = userId.first())
    }

    ArchivedCourseListScreen(
        state = state.value,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        onClickLike = { postId, isLiked ->
            viewModel.toggleLike(postId = postId, isLiked = isLiked)
        },
        modifier = modifier
    )
}

@Composable
fun ArchivedCourseListScreen(
    state: ArchivedListState,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    onClickLike: (postId: Int, isLiked: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        TopBar(
            title = "내가 기록한 산책 루트",
            onBackClick = navigateUp
        )

        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(PawKeyTheme.colors.white1)
        ) {
            itemsIndexed(state.courseList) { _, item ->
                CourseCard(
                    postId = item.postId.toInt(),
                    title = item.title,
                    createdAt = item.createdAt,
                    representativeImageUrl = item.representativeImageUrl,
                    petName = item.writer.first().petName,
                    petProfileImageUrl = item.writer.first().petProfileImageUrl,
                    descriptionTags = item.descriptionTags,
                    isLiked = null,
                    isPublic = item.isPublic, // 눈아이콘만 표시
                    onClickItem = navigateNext,
                    onClickLike = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArchivedCourseListScreenPreview() {
    PawKeyTheme {
        ArchivedCourseListScreen(
            state = ArchivedListState(),
            navigateUp = {},
            navigateNext = {},
            onClickLike = { _, _ ->
            }
        )
    }
}