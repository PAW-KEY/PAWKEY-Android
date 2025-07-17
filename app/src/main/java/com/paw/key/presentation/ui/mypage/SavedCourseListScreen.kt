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
import com.paw.key.presentation.ui.mypage.state.SavedListState
import com.paw.key.presentation.ui.mypage.viewmodel.SavedListViewModel
import kotlinx.coroutines.flow.first

@Composable
fun SavedCourseRoute(
    navigateUp: () -> Unit,
    navigateNext: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SavedListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val userId = PreferenceDataStore.getUserId()
//    LaunchedEffect(Unit) {
//        viewModel.getSavedList(userId = userId.first())
//    }


    SavedCourseListScreen(
        state = state.value,
        navigateUp = navigateUp,
        navigateNext = { routeId, pageId ->
            navigateNext(routeId, pageId)
        },
        /*onClickLike = {
            //viewModel.toggleLike(postId = , isLiked = false)
        },*/
        onClickItem = {
            //navigateNext(state.)
        },
        modifier = modifier
    )
}

@Composable
fun SavedCourseListScreen(
    state: SavedListState,
    navigateUp: () -> Unit,
    navigateNext: (Int, Int) -> Unit,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PawKeyTheme.colors.white2)
    ) {

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
            itemsIndexed(state.courseList) { _, item ->
                CourseCard(
                    postId = item.postId.toInt(),
                    title = item.title,
                    createdAt = item.createdAt,
                    representativeImageUrl = item.representativeImageUrl,
                    petName = item.writer.petName,
                    petProfileImageUrl = item.writer.petProfileImageUrl,
                    descriptionTags = item.descriptionTags,
                    isLiked = item.isLiked,
                    isPublic = item.isPublic,
                    isMine = item.isMine,
                    onClickItem = {
                        navigateNext(item.routeId, item.postId)
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedCourseListScreenPreview() {
    PawKeyTheme {
        SavedCourseListScreen(
            state = SavedListState(),
            navigateUp = {},
            navigateNext = { _, _ ->
            },
            onClickItem = {}

            //onClickLike = {}
        )
    }
}