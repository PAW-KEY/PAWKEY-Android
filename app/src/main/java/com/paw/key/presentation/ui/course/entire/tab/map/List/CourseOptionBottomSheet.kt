package com.paw.key.presentation.ui.course.entire.tab.map.List

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable
import com.paw.key.presentation.ui.course.entire.tab.map.List.state.TapListContract
import com.paw.key.presentation.ui.course.entire.tab.map.List.viewmodel.TapListViewModel

@Preview(showBackground = true)
@Composable
fun PreviewCourseOptionBottomSheet() {
    PawKeyTheme {
        Column {
            Text("Bottom Sheet Preview")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseOptionBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: TapListViewModel,
    onDismissRequest: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val listState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (listState.filterOptions == null && !listState.isLoading) {
            viewModel.loadFilterOptions()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = PawKeyTheme.colors.white1,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 34.dp)
        ) {
            BottomSheetHeader()

            when {
                listState.isLoading -> {
                    LoadingContent(
                        listState = listState,
                        viewModel = viewModel,
                        modifier = Modifier.weight(1f)
                    )
                }

                listState.filterOptions != null -> {
                    SuccessContent(
                        listState = listState,
                        viewModel = viewModel,
                        modifier = Modifier.weight(1f)
                    )
                }

                else -> {
                    ErrorContent(
                        listState = listState,
                        viewModel = viewModel,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            BottomButtons(
                viewModel = viewModel,
                onDismissRequest = onDismissRequest
            )
        }
    }
}

@Composable
private fun BottomSheetHeader() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.course_list_option_title),
                color = PawKeyTheme.colors.black,
                style = PawKeyTheme.typography.head20B1
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.course_list_option_sort),
            color = PawKeyTheme.colors.green500,
            style = PawKeyTheme.typography.caption12Sb1
        )
    }
}

@Composable
private fun LoadingContent(
    listState: TapListContract.TapListState,
    viewModel: TapListViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(TapListContract.Options.sortOptions) { option ->
            SortOptionItem(
                title = option,
                isSelected = listState.selectedSortOption == option,
                onSelect = { viewModel.updateSortOption(option) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = PawKeyTheme.colors.green500,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "필터 옵션을 불러오는 중...",
                        color = PawKeyTheme.colors.gray500,
                        style = PawKeyTheme.typography.body14R,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun ErrorContent(
    listState: TapListContract.TapListState,
    viewModel: TapListViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(TapListContract.Options.sortOptions) { option ->
            SortOptionItem(
                title = option,
                isSelected = listState.selectedSortOption == option,
                onSelect = { viewModel.updateSortOption(option) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "필터 옵션을 불러올 수 없습니다",
                        color = PawKeyTheme.colors.gray500,
                        style = PawKeyTheme.typography.body14R,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "다시 시도해보세요",
                        color = PawKeyTheme.colors.gray400,
                        style = PawKeyTheme.typography.caption12R,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.noRippleClickable {
                            viewModel.loadFilterOptions()
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun SuccessContent(
    listState: TapListContract.TapListState,
    viewModel: TapListViewModel,
    modifier: Modifier = Modifier
) {
    val filterOptions = listState.filterOptions!!

    val timeOptions = filterOptions.selectList?.find { it.selectName == "산책 소요 시간" }
    Log.e("timeoptions", timeOptions.toString())
    val moodOptions = filterOptions.categoryList?.find { it.categoryName == "분위기" }
    val dogFriendOptions = filterOptions.categoryList?.find { it.categoryName == "강아지 친구" }
    val safetyOptions = filterOptions.categoryList?.find { it.categoryName == "안전" }
    val convenienceOptions = filterOptions.categoryList?.find { it.categoryName == "편의성" }
    val environmentOptions = filterOptions.categoryList?.find { it.categoryName == "환경" }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(TapListContract.Options.sortOptions) { option ->
            SortOptionItem(
                title = option,
                isSelected = listState.selectedSortOption == option,
                onSelect = { viewModel.updateSortOption(option) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.course_list_option_single),
                color = PawKeyTheme.colors.green500,
                style = PawKeyTheme.typography.caption12Sb1
            )
        }

        timeOptions?.let { selectList ->
            item {
                CourseOptionToggle(
                    title = selectList.selectName,
                    selecttitle = listState.selectedSortTime,
                    isExpanded = listState.isTimeExpanded,
                    onClick = { viewModel.toggleTimeExpanded() }
                )
            }

            if (listState.isTimeExpanded) {
                val options = filterOptions.selectList.flatMap {
                    it.options ?: emptyList()
                }

                items(options) { option ->
                    SingleOptionItem(
                        title = option.selectText,
                        isSelected = listState.selectedSortTime == option.selectText,
                        onSelect = { viewModel.updateSortTime(option.selectText) }
                    )
                }
            }

            item {
                HorizontalDivider(color = PawKeyTheme.colors.gray50, thickness = 1.dp)
            }
        }

        moodOptions?.let { mood ->
            item {
                CourseOptionToggle(
                    title = mood.categoryName,
                    selecttitle = listState.selectedMood,
                    isExpanded = listState.isMoodExpanded,
                    onClick = { viewModel.toggleMoodExpanded() }
                )
            }

            if (listState.isMoodExpanded) {
                val options = mood.categoryOptions ?: emptyList()

                items(options) { option ->
                    SingleOptionItem(
                        title = option.categoryOptionText,
                        isSelected = listState.selectedMood == option.categoryOptionText,
                        onSelect = { viewModel.updateMood(option.categoryOptionText) }
                    )
                }
            }

            item {
                HorizontalDivider(color = PawKeyTheme.colors.gray50, thickness = 1.dp)
            }
        }

        dogFriendOptions?.let { dogFriend ->
            item {
                CourseOptionToggle(
                    title = dogFriend.categoryName,
                    selecttitle = listState.selectedDogFriend,
                    isExpanded = listState.isDogFriendExpanded,
                    onClick = { viewModel.toggleDogFriendExpanded() }
                )
            }

            if (listState.isDogFriendExpanded) {
                val options = dogFriend.categoryOptions ?: emptyList()
                items(options) { option ->
                    SingleOptionItem(
                        title = option.categoryOptionText,
                        isSelected = listState.selectedDogFriend == option.categoryOptionText,
                        onSelect = { viewModel.updateDogFriend(option.categoryOptionText) }
                    )
                }
            }

            item {
                HorizontalDivider(color = PawKeyTheme.colors.gray50, thickness = 1.dp)
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.course_list_option_multiple),
                color = PawKeyTheme.colors.green500,
                style = PawKeyTheme.typography.caption12Sb1
            )
        }

        safetyOptions?.let { safety ->
            item {
                CourseOptionToggle(
                    title = safety.categoryName,
                    selecttitle = if (listState.selectedSafety.isNotEmpty()) "${listState.selectedSafety.size}개 선택" else "",
                    isExpanded = listState.isSafetyExpanded,
                    onClick = { viewModel.toggleSafetyExpanded() }
                )
            }

            if (listState.isSafetyExpanded) {
                val options = safety.categoryOptions ?: emptyList()
                items(options) { option ->
                    MultipleOptionItem(
                        title = option.categoryOptionText,
                        isSelected = listState.selectedSafety.contains(option.categoryOptionText),
                        onSelect = { viewModel.updateSafety(option.categoryOptionText) }
                    )
                }
            }

            item {
                HorizontalDivider(color = PawKeyTheme.colors.gray50, thickness = 1.dp)
            }
        }

        convenienceOptions?.let { convenience ->
            item {
                CourseOptionToggle(
                    title = convenience.categoryName,
                    selecttitle = if (listState.selectedConvenience.isNotEmpty()) "${listState.selectedConvenience.size}개 선택" else "",
                    isExpanded = listState.isConvenienceExpanded,
                    onClick = { viewModel.toggleConvenienceExpanded() }
                )
            }

            if (listState.isConvenienceExpanded) {
                val options = convenience.categoryOptions ?: emptyList()
                items(options) { option ->
                    MultipleOptionItem(
                        title = option.categoryOptionText,
                        isSelected = listState.selectedConvenience.contains(option.categoryOptionText),
                        onSelect = { viewModel.updateConvenience(option.categoryOptionText) }
                    )
                }
            }

            item {
                HorizontalDivider(color = PawKeyTheme.colors.gray50, thickness = 1.dp)
            }
        }

        environmentOptions?.let { environment ->
            item {
                CourseOptionToggle(
                    title = environment.categoryName,
                    selecttitle = if (listState.selectedEnvironment.isNotEmpty()) "${listState.selectedEnvironment.size}개 선택" else "",
                    isExpanded = listState.isEnvironmentExpanded,
                    onClick = { viewModel.toggleEnvironmentExpanded() }
                )
            }

            if (listState.isEnvironmentExpanded) {
                val options = environment.categoryOptions ?: emptyList()
                items(options) { option ->
                    MultipleOptionItem(
                        title = option.categoryOptionText,
                        isSelected = listState.selectedEnvironment.contains(option.categoryOptionText),
                        onSelect = { viewModel.updateEnvironment(option.categoryOptionText) }
                    )
                }
            }

            item {
                HorizontalDivider(color = PawKeyTheme.colors.gray50, thickness = 1.dp)
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun BottomButtons(
    viewModel: TapListViewModel,
    onDismissRequest: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        PawkeyButton(
            text = stringResource(id = R.string.course_list_option_apply),
            enabled = viewModel.isAllOptionsSelected(),
            onClick = {
                viewModel.applyOptions()
                onDismissRequest()
            },
            modifier = Modifier
                .weight(0.7f)
        )

        IconButton(
            onClick = { viewModel.resetAllOptions() },
            modifier = Modifier.size(56.dp).weight(0.3f)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_course_list_refresh),
                contentDescription = "초기화",
                tint = Color.Unspecified,
                modifier = Modifier.size(56.dp)
            )
        }
    }
}

@Composable
private fun SortOptionItem(
    title: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .noRippleClickable { onSelect() }
    ) {
        Text(
            text = title,
            color = if (isSelected) PawKeyTheme.colors.green500 else PawKeyTheme.colors.black,
            style = PawKeyTheme.typography.body14R
        )

        Spacer(modifier = Modifier.weight(1F))

        Checkbox(
            checked = isSelected,
            onCheckedChange = { onSelect() },
            colors = CheckboxDefaults.colors(
                checkedColor = PawKeyTheme.colors.green500,
                checkmarkColor = PawKeyTheme.colors.white1,
                uncheckedColor = PawKeyTheme.colors.gray100,
            ),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun SingleOptionItem(
    title: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .noRippleClickable { onSelect() }
    ) {
        Text(
            text = title,
            color = if (isSelected) PawKeyTheme.colors.green500 else PawKeyTheme.colors.black,
            style = PawKeyTheme.typography.body14R
        )

        Spacer(modifier = Modifier.weight(1F))

        Checkbox(
            checked = isSelected,
            onCheckedChange = { onSelect() },
            colors = CheckboxDefaults.colors(
                checkedColor = PawKeyTheme.colors.green500,
                checkmarkColor = PawKeyTheme.colors.white1,
                uncheckedColor = PawKeyTheme.colors.gray100,
            ),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun MultipleOptionItem(
    title: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .noRippleClickable { onSelect() }
    ) {
        Text(
            text = title,
            color = if (isSelected) PawKeyTheme.colors.green500 else PawKeyTheme.colors.black,
            style = PawKeyTheme.typography.body14R
        )

        Spacer(modifier = Modifier.weight(1F))

        Checkbox(
            checked = isSelected,
            onCheckedChange = { onSelect() },
            colors = CheckboxDefaults.colors(
                checkedColor = PawKeyTheme.colors.green500,
                checkmarkColor = PawKeyTheme.colors.white1,
                uncheckedColor = PawKeyTheme.colors.gray100,
            ),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun CourseOptionToggle(
    title: String,
    selecttitle: String,
    isExpanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .noRippleClickable { onClick() }
    ) {
        Text(
            text = title,
            color = PawKeyTheme.colors.black,
            style = PawKeyTheme.typography.body16Sb
        )

        Spacer(modifier = Modifier.weight(1F))

        if (selecttitle.isNotEmpty()) {
            Text(
                text = selecttitle,
                color = PawKeyTheme.colors.green500,
                style = PawKeyTheme.typography.caption12Sb1,
                modifier = Modifier.padding(end = 12.dp)
            )
        }

        Icon(
            imageVector = ImageVector.vectorResource(
                if (isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
            ),
            contentDescription = "toggle",
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )
    }
}