package com.paw.key.presentation.ui.detail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.DokiButton
import com.paw.key.core.designsystem.component.SubChip
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.component.UrlImage
import com.paw.key.core.designsystem.component.walk.WalkReviewInfoHolder
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.detail.component.DetailImageHolder
import com.paw.key.presentation.ui.detail.component.DetailTopReview
import com.paw.key.presentation.ui.detail.component.DokiDeleteButton
import com.paw.key.presentation.ui.detail.component.FilterChipDivider
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DetailRoute(
    paddingValues: PaddingValues,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DetailScreen(
        paddingValues = paddingValues,
        state = state
    )
}
@Composable
private fun DetailScreen(
    paddingValues: PaddingValues,
    state: DetailState
) {
    var isExpanded by remember { mutableStateOf(false) }

    // Todo : 서버 내용으로 변경
    val allItems = listOf(
        "중요도 낮음", "교육행정", "보도/자료 분석", "보도 일정",
        "키보드/자판자 공지", "의전 방문", "행사", "비밀 방문 쓰여기둥",
        "현미경", "번역본 용어 번역", "간단한", "공연", "프로젝트",
        "농이티/관리"
    )

    val maxVisibleItems = 5
    val visibleItems = if (isExpanded) allItems else allItems.take(maxVisibleItems)
    val hiddenCount = allItems.size - maxVisibleItems

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        TopBar(
            title = "루트 상세정보",
            thickness = 2
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            UrlImage(
                url = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .align(Alignment.TopCenter)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                    .verticalScroll(rememberScrollState())
                    .dropShadow(
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                        shadow = Shadow(
                            radius = 19f.dp,
                            alpha = 0.1f,
                            color = Color(0xff000000),
                        )
                    )
                    .background(
                        color = PawKeyTheme.colors.background,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            ) {
                Text(
                    text = "단지와 룰루랄라 룰루랄라 룰루랄라",
                    style = PawKeyTheme.typography.header3,
                    color = PawKeyTheme.colors.contents,
                    modifier = Modifier.padding(16.dp)
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = PawKeyTheme.colors.defaultButton,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    UrlImage(
                        url = "",
                        modifier = Modifier
                            .size(43.dp)
                            .clip(RoundedCornerShape(50.dp))
                    )

                    Text(
                        text = "단지",
                        style = PawKeyTheme.typography.subTitle,
                        color = PawKeyTheme.colors.defaultDark
                    )
                }

                HorizontalDivider(
                    thickness = 1.dp,
                    color = PawKeyTheme.colors.defaultButton,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                )

                WalkReviewInfoHolder(
                    icon = R.drawable.ic_walk_review_location,
                    content = "서울시 강남구 역삼동",
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp)
                )

                WalkReviewInfoHolder(
                    icon = R.drawable.ic_walk_review_time,
                    content = "2025.10.11 | 오후 11:30",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )

                WalkReviewInfoHolder(
                    icon = R.drawable.ic_walk_review_course_info,
                    content = "2025.10.11 | 오후 11:30 | 걸음수",
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .animateContentSize(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    visibleItems.forEach { item ->
                        SubChip(
                            text = item,
                            isActionChip = true,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(11.dp))


                if (!isExpanded) {
                    FilterChipDivider(
                        hiddenCount = hiddenCount,
                        onClick = { isExpanded = !isExpanded },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(
                            color = PawKeyTheme.colors.defaultButton,
                        )
                )

                DetailImageHolder(
                    imageUrls = persistentListOf("", "", "", ""),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 20.dp, bottom = 12.dp)
                )

                Text(
                    text = "후기 글 본문 후기 글 본문 후기 글 본문ㅇ\n" +
                            "후기 글 본문 후기 글 본문 후기 글 본문ㅇ후기 글 본문 후기 글 본문 후기 글 본문ㅇ후기 글 본문 후기 글 본문 후기 글 본문ㅇ후기 글 본문 후기 글 본문 후기 글 본문ㅇ후기 글 본문 후기 글 본문 후기 글 본문ㅇ후기 글 본문 후기 글 본문 후기 글 본문ㅇ후기 글 본문 후기 글 본문 후기 글 본문ㅇ후기 글 본문 후기 글 본문 후기 글 본문ㅇ후기 글 본문 후기 글 본문 후기 글 본문ㅇ",
                    color = PawKeyTheme.colors.contents,
                    style = PawKeyTheme.typography.bodyDefault,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(
                            color = PawKeyTheme.colors.defaultButton,
                        )
                )

                DetailTopReview(
                    reviewCount = 30,
                    isShared = true
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(
                            color = PawKeyTheme.colors.defaultButton,
                        )
                )

                Spacer(
                    modifier = Modifier.height(40.dp)
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Todo : State의 isMine으로 설정하기
                    if (state.isMine) {
                        DokiDeleteButton(
                            text = "삭제하기",
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        )
                        DokiButton(
                            text = "수정하기",
                            enabled = true,
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        DokiButton(
                            text = "해당 루트로 산책하기",
                            enabled = true,
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}



@Preview
@Composable
private fun DetailScreenPreview() {
    PawKeyTheme {
        DetailScreen(
            paddingValues = PaddingValues(),
            state = DetailState()
        )
    }
}