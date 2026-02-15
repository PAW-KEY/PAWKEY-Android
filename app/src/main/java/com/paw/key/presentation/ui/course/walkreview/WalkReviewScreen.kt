package com.paw.key.presentation.ui.course.walkreview

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.DokiBorderButton
import com.paw.key.core.designsystem.component.DokiButton
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewDialog
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewImageRow
import com.paw.key.core.designsystem.component.walk.WalkReviewInfoHolder
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewMultipleFilter
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewSingleFilter
import com.paw.key.presentation.ui.course.walkreview.state.WalkReviewState
import com.paw.key.presentation.ui.course.walkreview.viewmodel.WalkReviewViewModel

@Composable
fun WalkReviewRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit = {},
    navigateHome: () -> Unit = {},
    navigateWalkDetail: () -> Unit = {},
    viewModel: WalkReviewViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val imagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val pickMultipleMediaLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(3)
    ) { uris ->
        if (uris.isNotEmpty()) {
            uris.forEach {
                viewModel.updateImageList(it)
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            val limitedUris = uris.take(3)

            limitedUris.forEach {
                viewModel.updateImageList(it)
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        }
    }


    WalkReviewScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateHome = navigateHome,
        navigateWalkDetail = navigateWalkDetail,
        state = state,
        onClickImage = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pickMultipleMediaLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                )
            } else {
                permissionLauncher.launch(imagePermission)
            }
        },
        onImageDelete = viewModel::deleteImage,
        onFilterClick = viewModel::onFilterClick,
        onTitleValueChange = viewModel::updateReviewTitle,
        onContentValueChange = viewModel::updateReviewContent,
        onClickComplete = viewModel::completeWalkReview
    )
}

@Composable
private fun WalkReviewScreen(
    paddingValues: PaddingValues,
    state: WalkReviewState,
    navigateUp: () -> Unit = {},
    navigateHome: () -> Unit = {},
    navigateWalkDetail: () -> Unit = {},
    onClickImage: () -> Unit = {},
    onImageDelete: (Int) -> Unit = {},
    onFilterClick: (String, List<String>, Boolean) -> Unit = { _, _, _ -> },
    onTitleValueChange: (String) -> Unit = {},
    onContentValueChange: (String) -> Unit = {},
    onClickComplete: (Boolean) -> Unit = {}
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = PawKeyTheme.colors.background
            )
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
    ) {
        TopBar(
            title = "산책 기록하기",
            isBackVisible = true,
            thickness = 2,
            onBackClick = navigateUp
        )

        Spacer(modifier = Modifier.height(14.dp))

        WalkReviewImageRow(
            imageList = state.walkReviewImageList,
            onClickCard = { index, _ ->
                if (index != 0) {
                    onClickImage()
                }
            },
            onImageDelete = onImageDelete,
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Todo : 서버 내용으로 변경
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            WalkReviewInfoHolder(
                icon = R.drawable.ic_walk_review_location,
                content = "서울시 강남구 역삼동"
            )

            WalkReviewInfoHolder(
                icon = R.drawable.ic_walk_review_time,
                content = "2025.10.11 | 오후 11:30"
            )

            WalkReviewInfoHolder(
                icon = R.drawable.ic_walk_review_course_info,
                content = "2025.10.11 | 오후 11:30 | 걸음수"
            )

            Spacer(modifier = Modifier.height(40.dp))

            WalkReviewSingleFilter(
                title = "혼잡도",
                filterList = state.walkReviewFilterModel.confusionSingleFilterList,
                selectedItem = state.getSingleFilterSelection(state.walkReviewFilterModel.confusionSingleFilterList),
                onItemSelected = {
                    onFilterClick(
                        it,
                        state.walkReviewFilterModel.confusionSingleFilterList,
                        true
                    )
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            WalkReviewSingleFilter(
                title = "강아지 교류 빈도",
                filterList = state.walkReviewFilterModel.frequencySingleFilterList,
                selectedItem = state.getSingleFilterSelection(state.walkReviewFilterModel.frequencySingleFilterList),
                onItemSelected = {
                    onFilterClick(
                        it,
                        state.walkReviewFilterModel.frequencySingleFilterList,
                        true
                    )
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Todo : 어떻게 필터값을 받을 지 몰라서 보류
            WalkReviewMultipleFilter(
                title = "안전",
                filterList = state.walkReviewFilterModel.safetyMultipleFilterList,
                selectedItems = state.walkReviewSelectedFilterData,
                onItemClick = {
                    onFilterClick(
                        it,
                        state.walkReviewFilterModel.safetyMultipleFilterList,
                        false
                    )
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            WalkReviewMultipleFilter(
                title = "편의성",
                filterList = state.walkReviewFilterModel.comfortMultipleFilterList,
                selectedItems = state.walkReviewSelectedFilterData,
                onItemClick = {
                    onFilterClick(
                        it,
                        state.walkReviewFilterModel.comfortMultipleFilterList,
                        false
                    )
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            WalkReviewMultipleFilter(
                title = "환경",
                filterList = state.walkReviewFilterModel.environmentMultipleFilterList,
                selectedItems = state.walkReviewSelectedFilterData,
                onItemClick = {
                    onFilterClick(
                        it,
                        state.walkReviewFilterModel.environmentMultipleFilterList,
                        false
                    )
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "산책에 대한 후기를 작성해주세요",
                style = PawKeyTheme.typography.subTitle,
                color = PawKeyTheme.colors.contents
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 텍필 넣기
            BasicTextField(
                value = state.walkReviewTitle,
                onValueChange = onTitleValueChange,
                textStyle = PawKeyTheme.typography.bodyActive.copy(color = PawKeyTheme.colors.contents),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = PawKeyTheme.colors.defaultBright,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (state.walkReviewTitle.isEmpty()) {
                            Text(
                                text = "후기 제목을 14글자 이내로 입력해주세요",
                                style = PawKeyTheme.typography.bodyDefault,
                                color = PawKeyTheme.colors.defaultMiddle
                            )
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                value = state.walkReviewContent,
                onValueChange = onContentValueChange,
                textStyle = PawKeyTheme.typography.bodyActive.copy(color = PawKeyTheme.colors.contents),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 216.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = PawKeyTheme.colors.defaultBright,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        if (state.walkReviewContent.isEmpty()) {
                            Text(
                                text = "산책에 대한 내용을 250자 이내로 작성해주세요",
                                style = PawKeyTheme.typography.bodyDefault,
                                color = PawKeyTheme.colors.defaultMiddle
                            )
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            DokiBorderButton(
                text = "산책 기록 나만보기",
                enabled = true,
                onClick = {
                    onClickComplete(false)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            DokiButton(
                text = "산책 기록 공유하기",
                enabled = state.walkReviewTitle.isNotEmpty() && state.walkReviewContent.isNotEmpty(),
                onClick = {
                    onClickComplete(true)
                }
            )
        }
    }

    if (state.isComplete) {
        WalkReviewDialog(
            navigateHome = navigateHome,
            navigateWalkDetail = navigateWalkDetail
        )
    }
}

@Preview
@Composable
private fun WalkReviewPreview() {
    PawKeyTheme {
        WalkReviewScreen(
            paddingValues = PaddingValues(),
            state = WalkReviewState(),
        )
    }
}
