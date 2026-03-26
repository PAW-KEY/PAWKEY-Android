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
import com.paw.key.presentation.ui.community.model.FilterCategoryUiModel
import com.paw.key.presentation.ui.community.model.SelectionType
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewMultipleFilter
import com.paw.key.presentation.ui.course.walkreview.component.WalkReviewSingleFilter
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun FilterScreen(
    paddingValues: PaddingValues,
    state: CommunityState,
    onFilterClick: (Int, FilterCategoryUiModel) -> Unit,
    onCompleted: () -> Unit,
    onBackClick: () -> Unit,
    onClickSuffix: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
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

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(26.dp))

            // durationList
            state.filterUiModel.durationList.forEach { category ->
                FilterCategorySection(
                    category = category,
                    selectedOptionIds = state.getSelectedOptionIds(category),
                    onFilterClick = onFilterClick
                )
                Spacer(modifier = Modifier.height(40.dp))
            }

            // categoryList
            state.filterUiModel.categoryList.forEach { category ->
                FilterCategorySection(
                    category = category,
                    selectedOptionIds = state.getSelectedOptionIds(category),
                    onFilterClick = onFilterClick
                )
                Spacer(modifier = Modifier.height(40.dp))
            }

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

@Composable
private fun FilterCategorySection(
    category: FilterCategoryUiModel,
    selectedOptionIds: PersistentList<Int>,
    onFilterClick: (Int, FilterCategoryUiModel) -> Unit
) {
    if (category.selectionType == SelectionType.SINGLE) {
        WalkReviewSingleFilter(
            title = category.name,
            filterList = category.options.map { it.text }.toImmutableList(),
            selectedItem = category.options
                .firstOrNull { selectedOptionIds.contains(it.id) }?.text.orEmpty(),
            onItemSelected = { selectedText ->
                val optionId = category.options.first { it.text == selectedText }.id
                onFilterClick(optionId, category)
            }
        )
    } else {
        WalkReviewMultipleFilter(
            title = category.name,
            filterList = category.options.map { it.text }.toImmutableList(),
            selectedItems = selectedOptionIds
                .mapNotNull { id -> category.options.firstOrNull { it.id == id }?.text }
                .toPersistentList(),
            onItemClick = { selectedText ->
                val optionId = category.options.first { it.text == selectedText }.id
                onFilterClick(optionId, category)
            }
        )
    }
}

@Preview
@Composable
private fun FilterScreenPreview() {
    PawKeyTheme {
        FilterScreen(
            state = CommunityState(),
            onFilterClick = { _, _ -> },
            onCompleted = {},
            onBackClick = {},
            onClickSuffix = {},
            paddingValues = PaddingValues()
        )
    }
}
