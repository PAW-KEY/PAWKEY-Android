package com.paw.key.presentation.ui.course.entire.component

import androidx.compose.foundation.layout.height
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.course.entire.state.EntireCourseContract.CourseTab

@Composable
fun EntireCourseTabRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    tabs : List<CourseTab>,
    modifier: Modifier = Modifier,
) {
    ScrollableTabRow (
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        edgePadding = 16.dp,
        contentColor = PawKeyTheme.colors.gray950,
        containerColor = PawKeyTheme.colors.gray0,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .height(6.dp),
                color = PawKeyTheme.colors.gray950
            )
        },
        divider = {}
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                text = {
                    Text(
                        text = stringResource(id = tab.titleResId),
                        color = PawKeyTheme.colors.gray950,
                        //style = PawKeyTheme.typography.body2M15,
                    )
                },
                selected = index == selectedTabIndex,
                onClick = {
                    onTabSelected(index)
                },
            )
        }
    }
}

@Preview
@Composable
private fun EntireCourseTabRowPreview() {
    PawKeyTheme {
        EntireCourseTabRow(
            selectedTabIndex = 0,
            onTabSelected = {},
            tabs = listOf(CourseTab.MapTab, CourseTab.ListTab),
        )
    }
}