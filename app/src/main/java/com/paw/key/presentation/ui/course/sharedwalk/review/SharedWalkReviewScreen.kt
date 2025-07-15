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
            val feedItem = WalkReviewContract.WalkReviewFeedbackData(
                id = index.toString(),
                label = content,
                isSelected = true
            )

            when (index) {
                0 -> viewModel.onSelectSafetyFeedback(feedItem)
                1 -> viewModel.onSelectFacilityFeedback(feedItem)
                2 -> viewModel.onSelectRoadFeedback(feedItem)
                3 -> viewModel.onSelectNoiseFeedback(feedItem)
                4 -> viewModel.onSelectFrequencyFeedback(feedItem)
            }
        },
        isDialogVisible = state.isDialogVisible,
        isFormValid = isValid,
        isSharedWalk = isSharedWalk,
        petName = state.petName,
        feedbackState = state.feedbackState,
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
    onClickFeedback : (Int, String) -> Unit,
    isDialogVisible : Boolean,
    isFormValid : Boolean,
    isSharedWalk : Boolean,
    petName : String,
    feedbackState : WalkReviewContract.WalkReviewFeedbackState,
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
                val feedbackTitle = listOf(
                    "\uD83D\uDEB8 산책 중 안전 요소는 어땠나요?",
                    "\uD83E\uDDFA 산책 중 어떤 편의 시설이 있었나요?",
                    "\uD83C\uDF3F 산책 주변의 길 상태는 어땠나요?",
                    "\uD83D\uDE0C 산책로의 분위기는 어땠나요 ?",
                    "\uD83D\uDC36 산책 중 다른 강아지들과 얼마나 마주쳤나요?"
                )

                // Todo : 서버에서 주는 값으로 변경 예정
                val eachFeedbackList = listOf(
                    listOf(
                        "킥보드나 자전거가 거의 없어요",
                        "차량이 거의 다니지 않아요",
                        "야간 조명이 잘 되어 있어요",
                        "보도와 차도가 구분되어 있어요",
                        "보도가 넓어서 산책하기 편했어요"
                    ),
                    listOf(
                        "배변 봉투 쓰레기통이 있어요",
                        "애견 산책로가 있어요",
                        "쉴 곳이 있어요",
                        "편의점이 있어요",
                        "반려견 동반 가능한 카페가 있어요"
                    ),
                    listOf(
                        "풀이 많아요",
                        "주로 흙길이에요",
                        "주로 아스팔트, 벽돌이에요",
                        "뛰어놀 수 있는 공간이 있어요"
                    ),
                    listOf(
                        "조용하고 한적했어요",
                        "사람이 적당히 있어요",
                        "사람이 많았어요"
                    ),
                    listOf(
                        "많이 마주쳤어요",
                        "가끔 마주쳤어요",
                        "거의 없었어요"
                    )
                )

                feedbackTitle.forEachIndexed { index, title ->
                    val currentSelectedFeedback = when (index) {
                        0 -> feedbackState.selectedSafetyFeedback
                        1 -> feedbackState.selectedFacilityFeedback
                        2 -> feedbackState.selectedRoadFeedback
                        3 -> feedbackState.selectedNoiseFeedback
                        4 -> feedbackState.selectedFrequencyFeedback
                        else -> null
                    }

                    WalkReviewFeedbackForm(
                        icon = R.drawable.ic_walk_review_location,
                        title = title,
                        selectedFeedbackItem = currentSelectedFeedback,
                        feedbackList = eachFeedbackList[index],
                        onClickFeedback = { selectedFeedback ->
                            onClickFeedback(index, selectedFeedback)
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