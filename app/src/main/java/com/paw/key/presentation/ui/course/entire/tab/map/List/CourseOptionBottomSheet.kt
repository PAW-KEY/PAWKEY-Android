package com.paw.key.presentation.ui.course.entire.tab.map.List

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable
import com.paw.key.presentation.ui.course.entire.tab.map.state.TapMapContract
import com.paw.key.presentation.ui.course.entire.tab.map.viewmodel.TapMapViewModel

@Preview(showBackground = true)
@Composable
fun PreviewCourseOptionBottomSheet() {
    PawKeyTheme {
        Column {
            CourseOptionBottomSheet(
                viewModel = viewModel(),
                onDismissRequest = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseOptionBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: TapMapViewModel,
    onDismissRequest: () -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val listState by viewModel.state_list.collectAsStateWithLifecycle()

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet = false
            onDismissRequest()
        },
        sheetState = sheetState,
        containerColor = PawKeyTheme.colors.white1,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        contentColor = PawKeyTheme.colors.white1
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
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

                Spacer(modifier = Modifier.height(6.dp))
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                items(TapMapContract.Options.sortOptions) { option ->
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

                item {
                    CourseOptionToggle(
                        title = stringResource(id = R.string.course_list_option_sort),
                        selecttitle = if (listState.selectedWalkTime.isNotEmpty()) listState.selectedWalkTime else "",
                        isExpanded = listState.isWalkTimeExpanded,
                        onClick = { viewModel.toggleWalkTimeExpanded() }
                    )
                }

                if (listState.isWalkTimeExpanded) {
                    items(TapMapContract.Options.walkTimeOptions) { option ->
                        SingleOptionItem(
                            title = option,
                            isSelected = listState.selectedWalkTime == option,
                            onSelect = { viewModel.updateWalkTime(option) }
                        )
                    }
                }

                item {
                    HorizontalDivider(
                        color = PawKeyTheme.colors.gray50,
                        thickness = 1.dp
                    )
                }

                item {
                    CourseOptionToggle(
                        title = stringResource(id = R.string.course_list_option_mood),
                        selecttitle = if (listState.selectedMood.isNotEmpty()) listState.selectedMood else "",
                        isExpanded = listState.isMoodExpanded,
                        onClick = { viewModel.toggleMoodExpanded() }
                    )
                }

                if (listState.isMoodExpanded) {
                    items(TapMapContract.Options.moodOptions) { option ->
                        SingleOptionItem(
                            title = option,
                            isSelected = listState.selectedMood == option,
                            onSelect = { viewModel.updateMood(option) }
                        )
                    }
                }

                item {
                    HorizontalDivider(
                        color = PawKeyTheme.colors.gray50,
                        thickness = 1.dp
                    )
                }

                item {
                    CourseOptionToggle(
                        title = stringResource(id = R.string.course_list_option_dog_friend),
                        selecttitle = if (listState.selectedDogFriend.isNotEmpty()) listState.selectedDogFriend else "",
                        isExpanded = listState.isDogFriendExpanded,
                        onClick = { viewModel.toggleDogFriendExpanded() }
                    )
                }

                if (listState.isDogFriendExpanded) {
                    items(TapMapContract.Options.dogFriendOptions) { option ->
                        SingleOptionItem(
                            title = option,
                            isSelected = listState.selectedDogFriend == option,
                            onSelect = { viewModel.updateDogFriend(option) }
                        )
                    }
                }

                item {
                    HorizontalDivider(
                        color = PawKeyTheme.colors.gray50,
                        thickness = 1.dp
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(id = R.string.course_list_option_multiple),
                        color = PawKeyTheme.colors.green500,
                        style = PawKeyTheme.typography.caption12Sb1
                    )
                }

                item {
                    CourseOptionToggle(
                        title = stringResource(id = R.string.course_list_option_safety),
                        selecttitle = if (listState.selectedSafety.isNotEmpty()) "${listState.selectedSafety.size}개 선택" else "",
                        isExpanded = listState.isSafetyExpanded,
                        onClick = { viewModel.toggleSafetyExpanded() }
                    )
                }

                if (listState.isSafetyExpanded) {
                    items(TapMapContract.Options.safetyOptions) { option ->
                        MultipleOptionItem(
                            title = option,
                            isSelected = listState.selectedSafety.contains(option),
                            onSelect = { viewModel.updateSafety(option) }
                        )
                    }
                }

                item {
                    HorizontalDivider(
                        color = PawKeyTheme.colors.gray50,
                        thickness = 1.dp
                    )
                }

                item {
                    CourseOptionToggle(
                        title = stringResource(id = R.string.course_list_option_convenience),
                        selecttitle = if (listState.selectedConvenience.isNotEmpty()) "${listState.selectedConvenience.size}개 선택" else "",
                        isExpanded = listState.isConvenienceExpanded,
                        onClick = { viewModel.toggleConvenienceExpanded() }
                    )
                }

                if (listState.isConvenienceExpanded) {
                    items(TapMapContract.Options.convenienceOptions) { option ->
                        MultipleOptionItem(
                            title = option,
                            isSelected = listState.selectedConvenience.contains(option),
                            onSelect = { viewModel.updateConvenience(option) }
                        )
                    }
                }

                item {
                    HorizontalDivider(
                        color = PawKeyTheme.colors.gray50,
                        thickness = 1.dp
                    )
                }

                item {
                    CourseOptionToggle(
                        title = stringResource(id = R.string.course_list_option_environment),
                        selecttitle = if (listState.selectedEnvironment.isNotEmpty()) "${listState.selectedEnvironment.size}개 항목 선택" else "",
                        isExpanded = listState.isEnvironmentExpanded,
                        onClick = { viewModel.toggleEnvironmentExpanded() }
                    )
                }

                if (listState.isEnvironmentExpanded) {
                    items(TapMapContract.Options.environmentOptions) { option ->
                        MultipleOptionItem(
                            title = option,
                            isSelected = listState.selectedEnvironment.contains(option),
                            onSelect = { viewModel.updateEnvironment(option) }
                        )
                    }
                }

                item {
                    HorizontalDivider(
                        color = PawKeyTheme.colors.gray50,
                        thickness = 1.dp
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PawkeyButton(
                    text = stringResource(id = R.string.course_list_option_apply),
                    enabled = viewModel.isAllOptionsSelected(),
                    onClick = {
                        viewModel.applyOptions()
                        onDismissRequest()
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .width(260.dp)
                )

                IconButton(
                    onClick = { viewModel.resetAllOptions() },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_course_list_refresh),
                        contentDescription = stringResource(id = R.string.course_list_option_reset),
                        tint = Color.Unspecified,
                        modifier = Modifier.size(56.dp)
                    )
                }
            }
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
            modifier = Modifier.noRippleClickable { onClick() }
        )
    }
}