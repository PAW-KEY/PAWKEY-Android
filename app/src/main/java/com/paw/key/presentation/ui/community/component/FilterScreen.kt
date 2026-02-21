package com.paw.key.presentation.ui.community.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.component.DokiButton
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.community.CommunityState
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewMultipleFilter
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewSingleFilter

@Composable
fun FilterScreen(
    paddingValues: PaddingValues,
    state: CommunityState,
    onFilterClick: (String, List<String>, Boolean) -> Unit,
    onCompleted: () -> Unit,
    onBackClick: () -> Unit,
    onClickSuffix: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Todo : 서버 내용으로 변경
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(PawKeyTheme.colors.background)
            .padding(paddingValues)
    ) {
        TopBar(
            title = "필터",
            isBackVisible = true,
            thickness = 2,
            onBackClick = onBackClick,
            onClickSuffix = onClickSuffix,
            suffix = R.drawable.ic_course_list_refresh
        )

        Column (
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(26.dp))

            WalkReviewSingleFilter(
                title = "혼잡도",
                filterList = state.communityFilterModel.confusionSingleFilterList,
                selectedItem = state.getSingleFilterSelection(state.communityFilterModel.confusionSingleFilterList),
                onItemSelected = {
                    onFilterClick(
                        it,
                        state.communityFilterModel.confusionSingleFilterList,
                        true
                    )
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            WalkReviewSingleFilter(
                title = "강아지 교류 빈도",
                filterList = state.communityFilterModel.frequencySingleFilterList,
                selectedItem = state.getSingleFilterSelection(state.communityFilterModel.frequencySingleFilterList),
                onItemSelected = {
                    onFilterClick(
                        it,
                        state.communityFilterModel.frequencySingleFilterList,
                        true
                    )
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Todo : 어떻게 필터값을 받을 지 몰라서 보류
            WalkReviewMultipleFilter(
                title = "안전",
                filterList = state.communityFilterModel.safetyMultipleFilterList,
                selectedItems = state.communitySelectedFilterData,
                onItemClick = {
                    onFilterClick(
                        it,
                        state.communityFilterModel.safetyMultipleFilterList,
                        false
                    )
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            WalkReviewMultipleFilter(
                title = "편의성",
                filterList = state.communityFilterModel.comfortMultipleFilterList,
                selectedItems = state.communitySelectedFilterData,
                onItemClick = {
                    onFilterClick(
                        it,
                        state.communityFilterModel.comfortMultipleFilterList,
                        false
                    )
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            WalkReviewMultipleFilter(
                title = "환경",
                filterList = state.communityFilterModel.environmentMultipleFilterList,
                selectedItems = state.communitySelectedFilterData,
                onItemClick = {
                    onFilterClick(
                        it,
                        state.communityFilterModel.environmentMultipleFilterList,
                        false
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }


        DokiButton(
            text = "적용하기",
            enabled = true,
            onClick = onCompleted,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        )
    }


}

@Preview
@Composable
private fun FilterScreenPreview() {
    PawKeyTheme {
        FilterScreen(
            state = CommunityState(),
            onFilterClick = { _, _, _ -> },
            onCompleted = {},
            onBackClick = {},
            onClickSuffix = {},
            paddingValues = PaddingValues()
        )
    }
}