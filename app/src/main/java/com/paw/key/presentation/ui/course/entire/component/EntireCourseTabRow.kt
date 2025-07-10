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
        containerColor = PawKeyTheme.colors.white1,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .height(4.dp),
                color = PawKeyTheme.colors.black
            )
            /*val currentTabPosition = tabPositions[selectedTabIndex]
            val indicatorWidth = currentTabPosition.contentWidth
            val indicatorOffset = currentTabPosition.left

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.BottomStart)
                    .offset(x = indicatorOffset)
                    .width(indicatorWidth)
                    .height(4.dp)
                    .background(
                        color = PawKeyTheme.colors.black,
                        shape = RoundedCornerShape(2.dp)
                    )
            )*/
        },
        divider = {}
    ) {
        tabs.forEachIndexed { index, tab ->
            val isSelected = index == selectedTabIndex

            Tab(
                text = {
                    Text(
                        text = stringResource(id = tab.titleResId),
                        color = if (isSelected) PawKeyTheme.colors.black else PawKeyTheme.colors.gray200,
                        style = PawKeyTheme.typography.head22B,
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