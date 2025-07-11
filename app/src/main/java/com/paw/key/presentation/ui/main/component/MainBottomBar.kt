package com.paw.key.presentation.ui.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable
import com.paw.key.presentation.ui.main.MainTab
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import com.paw.key.R

@Composable
fun MainBottomBar(
    isVisible: Boolean,
    tabs: ImmutableList<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabPositions = remember {
        mutableStateListOf<Offset>() 
    }
    
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = isVisible,
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                color = PawKeyTheme.colors.gray950,
                shape = RoundedCornerShape(200.dp),
                shadowElevation = 10.dp,
                modifier = Modifier
            ) {
                val selectedIndex = tabs.indexOf(currentTab)

                // 애니메이션 - density로
                val targetOffsetX by animateDpAsState(
                    targetValue = with(density) {
                        tabPositions.getOrNull(selectedIndex)?.x?.toDp() ?: 0.dp
                    },
                    label = stringResource(R.string.main_bottom_bar_animation_label)
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .wrapContentSize()
                ) {
                    // 물방울 모양
                    if (selectedIndex != -1 && tabPositions.size == tabs.size) {
                        Box(
                            modifier = Modifier
                                .offset {
                                    IntOffset(targetOffsetX.roundToPx(), 0)
                                }
                                .size(48.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Color.White)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .selectableGroup(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        tabs.forEachIndexed { index, tab ->
                            MainNavigationBarItem(
                                selected = tab == currentTab,
                                tab = tab,
                                onClick = { onTabSelected(tab) },
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .onGloballyPositioned {
                                        val pos = it.positionInParent()
                                        if (tabPositions.size <= index) {
                                            tabPositions.add(pos)
                                        } else {
                                            tabPositions[index] = pos
                                        }
                                    }
                            )
                        }
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
    val iconRes = if (selected) tab.selectedIcon else tab.unselectedIcon
    
    Box(
        modifier = modifier
            .noRippleClickable(onClick)
            .clip(RoundedCornerShape(24.dp))
            .size(48.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = stringResource(tab.contentDescription),
            modifier = Modifier
                .padding(12.dp)
                .size(24.dp),
            tint = Color.Unspecified
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
            onTabSelected = { }
        )
    }
}
