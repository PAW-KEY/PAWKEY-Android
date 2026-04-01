package com.paw.key.presentation.ui.course.walkcourse.walkcomplete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.DokiButton
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.component.UrlImage
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.course.walkcourse.component.WalkRecordItem
import com.paw.key.presentation.ui.course.walkcourse.walkcomplete.model.WalkInfoModel
import com.paw.key.presentation.ui.course.walkcourse.walkcomplete.state.WalkCompleteState

// Todo : 나중에 서버에서 줌
@Composable
fun WalkCompleteRoute(
    paddingValues: PaddingValues,
    navigateReview: () -> Unit = {},
    viewModel: WalkCompleteViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    WalkCompleteScreen(
        paddingValues = paddingValues,
        state = state,
        navigateReview = navigateReview
    )
}

@Composable
private fun WalkCompleteScreen(
    paddingValues: PaddingValues,
    state: WalkCompleteState,
    navigateReview: () -> Unit = {}
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = PawKeyTheme.colors.background)
    ) {
        TopBar(
            title = "산책 완료",
            isBackVisible = false
        )

        Spacer(modifier = Modifier.height(22.dp))

        Column (
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .dropShadow(
                    shape = RoundedCornerShape(16.dp),
                    shadow = Shadow(
                        radius = 4f.dp,
                        alpha = 0.25f,
                        color = Color(0xff000000),
                    )
                )
                .background(Color.White, RoundedCornerShape(16.dp))
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 프로필사진
                UrlImage(
                    url = "",
                    modifier = Modifier
                        .size(36.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(50.dp))
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = "단지",
                        style = PawKeyTheme.typography.subTitle,
                        color = PawKeyTheme.colors.contents
                    )

                    Text(
                        text = "2025.06.26(금) | 오후 11:50",
                        style = PawKeyTheme.typography.subButtonDefault,
                        color = PawKeyTheme.colors.contents
                    )
                }
            }

            // 지도 사진
            UrlImage(
                url = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.87f)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                with(state.walkCompleteFinishInfo) {
                    WalkRecordItem(
                        recordTitle = R.string.course_record_distance,
                        recordContent = distance.toString()
                    )
                    WalkRecordItem(
                        recordTitle = R.string.course_record_time,
                        recordContent = duration.toString()
                    )
                    WalkRecordItem(
                        recordTitle = R.string.course_record_step,
                        recordContent = stepCount.toString()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(43.dp))

        DokiButton(
            text = "후기 작성하기",
            enabled = true,
            onClick = navigateReview,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
private fun WalkCompletePreview() {
    PawKeyTheme {
        WalkCompleteScreen(
            paddingValues = PaddingValues(),
            state = WalkCompleteState(
                walkCompleteFinishInfo = WalkInfoModel(
                    distance = 1000,
                    duration = 1000,
                    stepCount = 1000
                )
            )
        )
    }
}
