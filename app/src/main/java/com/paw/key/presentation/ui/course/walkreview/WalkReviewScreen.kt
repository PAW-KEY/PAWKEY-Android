package com.paw.key.presentation.ui.course.walkreview

import android.Manifest
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.compose.AsyncImage
import com.paw.key.R
import com.paw.key.core.designsystem.component.DataLoadingScreen
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.component.SubChip
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewFeedbackForm
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewFeedbackHeader
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewImageRow
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewInfoHolder
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewTextField
import com.paw.key.presentation.ui.course.walkreview.state.WalkReviewContract
import com.paw.key.presentation.ui.course.walkreview.viewmodel.WalkReviewViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun WalkReviewRoute(
    navigateUp: () -> Unit,
    navigateNext: (routeId : Int) -> Unit,
    navigateShared : (routeId : Int, pageId : Int) -> Unit,
    routeId : Int,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: WalkReviewViewModel = hiltViewModel(),
    isSharedWalk : Boolean = false
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isValid = state.isValidForm
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val lifecycleOwner = LocalLifecycleOwner.current

    val imagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val pickMultipleMediaLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(5)
    ) { uris ->
        if (uris.isNotEmpty()) {
            viewModel.onImagesSelected(uris)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            val limitedUris = uris.take(5)
            viewModel.onImagesSelected(limitedUris)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        }
    }

    LaunchedEffect(routeId) {
        viewModel.getWalkReviewCategory()
        viewModel.getWalkReviewInfo(routeId)
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is WalkReviewContract.WalkReviewSideEffect.ShowSnackBar -> snackBarHostState.showSnackbar(
                        sideEffect.message
                    )

                    is WalkReviewContract.WalkReviewSideEffect.SHowToastMessage -> {
                        Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                    }

                    is WalkReviewContract.WalkReviewSideEffect.NavigateNext -> {
                        Log.d("WalkReviewRoute", "navigateNext")
                        navigateShared(sideEffect.routeId, sideEffect.pageId)
                    }
                    WalkReviewContract.WalkReviewSideEffect.NavigateUp -> navigateUp()
                }
            }
    }

    WalkReviewScreen(
        navigateUp = navigateUp,
        onClickFeedback = { categoryId, optionId ->
            viewModel.onOptionSelected(categoryId, optionId)
        },
        locationDescription = state.location,
        timeDescription = state.time,
        tags = state.tags,
        isFormValid = isValid,
        isSharedWalk = isSharedWalk,
        imageList = state.images,
        petName = state.petName,
        titleText = state.title,
        contentText = state.content,
        feedbackList = state.categoryList,
        onTitleTextChanged = {
            viewModel.onTitleTextChanged(it)
        },
        onContentTextChanged = {
            viewModel.onContentTextChanged(it)
        },
        onClickImage = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pickMultipleMediaLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                )
            } else {
                permissionLauncher.launch(imagePermission)
            }
        },
        onImageDelete = {
            viewModel.onImageDelete(it)
        },
        onClickPublic = { isShare ->
            coroutineScope.launch {
                isLoading = false
                delay(3000L)
                isLoading = true
                viewModel.postWalkReview(
                    routeId = routeId,
                    isShare = isShare
                )
            }
        },
        /*navigateShared = {
            navigateShared(routeId)
        },*/
        modifier = modifier,
    )

    if (isLoading) {
        DataLoadingScreen()
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun WalkReviewScreen(
    navigateUp: () -> Unit,
    onClickFeedback : (Int, Int) -> Unit, // 카테고리, 옵션
    onTitleTextChanged : (String) -> Unit,
    onContentTextChanged : (String) -> Unit,
    onClickImage : () -> Unit,
    onImageDelete : (Uri?) -> Unit,
    onClickPublic : (Boolean) -> Unit, // true = 공개 / false = 비공개
    imageList: List<Uri>,
    locationDescription : String,
    timeDescription : String,
    tags : List<String>,
    isFormValid : Boolean,
    isSharedWalk : Boolean,
    petName : String,
    titleText : String,
    contentText : String,
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
            if (!isSharedWalk) {
                item {
                    WalkReviewImageRow(
                        imageList = imageList,
                        onClickCard = { index, _ ->
                            if (index != 0) {
                                onClickImage()
                            }
                        },
                        onImageDelete = {
                            onImageDelete(it)
                        },
                        modifier = Modifier
                            .background(PawKeyTheme.colors.white1),
                    )
                }
            } else {
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
            }

            item {
                Column(
                    modifier = Modifier
                        .padding(bottom = 12.dp, start = 16.dp, end = 16.dp)
                        .background(PawKeyTheme.colors.white1)
                ) {
                    WalkReviewInfoHolder(
                        icon = R.drawable.ic_walk_review_location,
                        content = locationDescription
                    )

                    WalkReviewInfoHolder(
                        icon = R.drawable.ic_walk_review_time,
                        content = timeDescription
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
                    tags.forEach {
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
                val emoji = listOf(
                    "\uD83D\uDE0C",
                    "\uD83D\uDC36",
                    "\uD83D\uDEB8",
                    "\uD83E\uDDFA",
                    "\uD83C\uDF3F"
                )

                feedbackList.forEachIndexed { index, category ->
                    WalkReviewFeedbackForm(
                        icon = R.drawable.ic_walk_review_location,
                        title = "${emoji[index]} ${category.categoryDescription}",
                        selectedFeedbackItems = category.options.filter { it.isSelected }.map { it.optionText },
                        feedbackList = category.options.map { it.optionText },
                        onClickFeedback = { selectedText ->
                            val selectedOption = category.options.find { it.optionText == selectedText }
                            if (selectedOption != null) {
                                onClickFeedback(category.categoryId, selectedOption.optionId)
                            }
                        },
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
                        selectedFeedbackItem = category.options.find { it.isSelected }?.optionText
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

            if (!isSharedWalk) {
                item {
                    Text(
                        text = "산책에 대한 감상을 들려주시겠어요?",
                        style = PawKeyTheme.typography.body16M,
                        color = PawKeyTheme.colors.black,
                        modifier = Modifier
                            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                    )

                    WalkReviewTextField(
                        textValue = titleText,
                        placeHolder = "후기 제목을 입력해주세요.",
                        onTextChanged = {
                            onTitleTextChanged(it)
                        },
                        modifier = Modifier
                            .padding(top = 10.dp, start = 16.dp, end = 16.dp)
                    )

                    WalkReviewTextField(
                        textValue = contentText,
                        placeHolder = "산책 후기를 간단하게 적어주세요!",
                        onTextChanged = {
                            onContentTextChanged(it)
                        },
                        modifier = Modifier
                            .heightIn(min = 200.dp, max = 400.dp)
                            .padding(top = 10.dp, bottom = 24.dp, start = 16.dp, end = 16.dp)
                    )
                }
            }

            item {
                HorizontalDivider(
                    thickness = 10.dp,
                    color = PawKeyTheme.colors.gray50,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                )
            }

            item {
                val buttonTextRes = if (isSharedWalk) {
                    // 공유됨
                    R.string.course_review_shared_button
                } else {
                    R.string.course_review_shared_all_button
                }

                // 공유된 거면 산책후기남기기 / 공유 안된거면 산책 기록 공개하기
                PawkeyButton(
                    text = stringResource(buttonTextRes),
                    onClick = {
                        // 공유뷰 아님 / 현재 그냥 리뷰
                        if (!isSharedWalk) {
                            onClickPublic(true)
                            Log.d("TAG", "WalkReviewScreen: 공유 안됨")
                        } else {
                            onClickPublic(false)
                            Log.d("TAG", "WalkReviewScreen: 공유 됨")
                        }
                    },
                    enabled = isFormValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                if (!isSharedWalk) {
                    Spacer(modifier = Modifier.height(10.dp))

                    PawkeyButton(
                        text = stringResource(R.string.course_review_saved_button),
                        onClick = {
                            onClickPublic(false)
                        },
                        enabled = isFormValid,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        isBackGround = true,
                        isBorder = true,
                    )
                }
            }
        }
    }
}