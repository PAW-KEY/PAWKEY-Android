package com.paw.key.presentation.ui.course.sharedwalk.review

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.compose.AsyncImage
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.component.SubChip
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.course.sharedwalk.review.state.SharedWalkReviewSideEffect
import com.paw.key.presentation.ui.course.sharedwalk.review.viewmodel.SharedWalkReviewViewModel
import com.paw.key.presentation.ui.course.walkreview.WalkReviewCategoryUiModel
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewDialog
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewFeedbackForm
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewFeedbackHeader
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewImageRow
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewInfoHolder
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewTextField
import com.paw.key.presentation.ui.course.walkreview.state.WalkReviewContract
import com.paw.key.presentation.ui.course.walkreview.viewmodel.WalkReviewViewModel

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun SharedWalkReviewRoute(
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    routeId : Int,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: SharedWalkReviewViewModel = hiltViewModel(),
    isSharedWalk : Boolean = true
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isValid = viewModel.state.collectAsStateWithLifecycle().value.isValidForm

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is SharedWalkReviewSideEffect.ShowSnackBar -> snackBarHostState.showSnackbar(
                        sideEffect.message
                    )

                    SharedWalkReviewSideEffect.NavigateNext -> navigateNext()
                    SharedWalkReviewSideEffect.NavigateUp -> navigateUp()
                }
            }
    }

    SharedWalkReviewScreen(
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        onClickFeedback = { index, content ->
            viewModel.onClickFeedback(index, content)
        },
        isDialogVisible = state.isDialogVisible,
        isFormValid = isValid,
        isSharedWalk = isSharedWalk,
        petName = state.petName,
        feedbackList = state.categoryList,
        onClickSharedReview = {
            viewModel.onClickSharedReview()
        },
        modifier = modifier,
    )
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun SharedWalkReviewScreen(
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    onClickSharedReview : () -> Unit,
    onClickFeedback : (Int, Int) -> Unit,
    isDialogVisible : Boolean,
    isFormValid : Boolean,
    isSharedWalk : Boolean,
    petName : String,
    feedbackList : List<WalkReviewCategoryUiModel>,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .fillMaxSize()
    ) {
        TopBar(
            title = "산책 기록하기",
            onBackClick = navigateUp,
            modifier = Modifier
                .background(PawKeyTheme.colors.white1),
            isBackVisible = true
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = PawKeyTheme.colors.gray50,
            modifier = Modifier
                .fillMaxWidth()
        )

        LazyColumn (
            modifier = modifier
                .background(PawKeyTheme.colors.white1)
                .padding(bottom = 16.dp)
        ){
            item {
                Text(
                    text = "제목",
                    style = PawKeyTheme.typography.head20Sb,
                    color = PawKeyTheme.colors.green500,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    AsyncImage(
                        model = "",
                        contentDescription = "profile",
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = PawKeyTheme.colors.gray50,
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                            .padding(end = 10.dp)
                    )

                    Text(
                        text = "강아지 이름 작성",
                        style = PawKeyTheme.typography.body16Sb,
                        color = PawKeyTheme.colors.gray600
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .padding(bottom = 12.dp, start = 16.dp, end = 16.dp)
                        .background(PawKeyTheme.colors.white1)
                ) {
                    WalkReviewInfoHolder(
                        icon = R.drawable.ic_walk_review_location,
                        content = "강남구 역삼동"
                    )

                    WalkReviewInfoHolder(
                        icon = R.drawable.ic_walk_review_time,
                        content = "2025.06.26(금) | 23:20-23:30"
                    )
                }
            }

            item {
                Row (
                    modifier = Modifier
                        .padding(bottom = 12.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .background(PawKeyTheme.colors.white1)
                ) {
                    val chips = listOf("2.2km", "30분", "3208걸음")

                    chips.forEach {
                        SubChip(
                            text = it,
                            modifier = Modifier
                                .padding(end = 6.dp)
                        )
                    }
                }
            }

            item {
                HorizontalDivider(
                    thickness = 10.dp,
                    color = PawKeyTheme.colors.gray50,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )
            }

            item {
                WalkReviewFeedbackHeader(
                    petName = petName,
                    modifier = Modifier
                        .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                        .background(PawKeyTheme.colors.white1)
                )
            }

            item {
                feedbackList.forEachIndexed { index, category ->
                    WalkReviewFeedbackForm(
                        icon = R.drawable.ic_walk_review_location,
                        title = category.categoryDescription,
                        selectedFeedbackItem = category.options.firstOrNull { it.isSelected }?.optionText,
                        feedbackList = category.options.map { it.optionText },
                        onClickFeedback = { selectedText ->
                            val selectedOption = category.options.find { it.optionText == selectedText }
                            if (selectedOption != null) {
                                onClickFeedback(category.categoryId, selectedOption.optionId)
                            }
                        },
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
                    )
                }
            }

            item {
                HorizontalDivider(
                    thickness = 10.dp,
                    color = PawKeyTheme.colors.gray50,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp)
                )
            }

            item {
                val buttonTextRes = if (isSharedWalk) {
                    // 후기
                    R.string.course_review_shared_button
                } else {
                    R.string.course_review_shared_all_button
                }

                // Todo : 후기 남기고 course의 리스트로 이동 - 애니메이션
                PawkeyButton(
                    text = stringResource(buttonTextRes),
                    onClick = {
                        onClickSharedReview()
                    },
                    enabled = isFormValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }

        if (isDialogVisible && isSharedWalk) {
            WalkReviewDialog(
                onClickOk = {
                    // 리스트로 이동
                    navigateNext()
                }
            )
        }
    }
}