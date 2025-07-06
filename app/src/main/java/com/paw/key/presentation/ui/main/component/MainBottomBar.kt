package com.paw.key.presentation.ui.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable
import com.paw.key.presentation.ui.main.MainTab
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MainBottomBar(
    isVisible: Boolean,
    tabs: ImmutableList<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility (
        visible = true,
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        modifier = modifier
    ) {
        Box (
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                color = Color.Black,
                shape = RoundedCornerShape(200.dp),
                shadowElevation = 10.dp,
                modifier = Modifier
            ) {
                Row(
                    modifier = Modifier
                        .selectableGroup(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    tabs.forEach { tab ->
                        MainNavigationBarItem(
                            selected = tab == currentTab,
                            tab = tab,
                            onClick = {
                                onTabSelected(tab)
                            },
                            modifier = modifier
                                .padding(6.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MainNavigationBarItem(
    selected: Boolean,
    tab: MainTab,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    //Todo : 색상 교환예정
    val iconRes = if (selected) tab.selectedIcon else tab.unselectedIcon

    val iconColor = if (selected) Color.Green else Color.White

    val backGroundColor = if (selected) Color.White else Color.Transparent

    Box (
        modifier = modifier
            .noRippleClickable(onClick)
            .clip(RoundedCornerShape(24.dp))
            .background(backGroundColor)
            .size(48.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = stringResource(tab.contentDescription),
            tint = iconColor,
            modifier = Modifier
                .padding(12.dp)
                .size(24.dp),
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun MainBottomBarPreview() {
    PawKeyTheme {
        val dummyTabs = persistentListOf(
            MainTab.HOME,
            MainTab.COURSE,
            MainTab.COMMUNITY,
            MainTab.MYPAGE
        )

        val currentTab by remember { mutableStateOf(MainTab.HOME) }

        MainBottomBar(
            isVisible = true,
            tabs = dummyTabs.toImmutableList(),
            currentTab = currentTab,
            onTabSelected = {  }
        )
    }
}