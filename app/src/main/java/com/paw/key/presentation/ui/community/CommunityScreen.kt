package com.paw.key.presentation.ui.community

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.component.routeitem.RouteItem
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable
import com.paw.key.presentation.ui.community.component.CommunityTopImageHolder
import com.paw.key.presentation.ui.community.component.FilterScreen
import com.paw.key.presentation.ui.community.model.SortedType
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

/**
 * [루트 추천] 화면
 */
@Composable
fun CommunityRoute( // 루트 추천
    paddingValues: PaddingValues,
    viewModel: CommunityViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isFilterSheetVisible by remember { mutableStateOf(false) }

    BackHandler {
        if (isFilterSheetVisible) {
            isFilterSheetVisible = false
            viewModel.onRefreshFilter()
        }
    }

    if (isFilterSheetVisible) {
        FilterScreen(
            paddingValues = paddingValues,
            state = state,
            onFilterClick = viewModel::onFilterClick,
            onCompleted = {
                isFilterSheetVisible = false
                viewModel.fetchPosts()
            },
            onBackClick = { isFilterSheetVisible = false },
            onClickSuffix = {
                viewModel.onRefreshFilter()
            }
        )
    } else {
        CommunityScreen(
            paddingValues = paddingValues,
            state = state,
            onShowFilterSheet = { isFilterSheetVisible = true }
        )
    }
}

@Composable
fun CommunityScreen(
    paddingValues: PaddingValues,
    state: CommunityState,
    onShowFilterSheet: () -> Unit = {},
) {
    val filterList = state.filterUiModel.allCategories.map { it.name }.toImmutableList()

    var selectedFilters by remember { mutableStateOf(setOf<String>()) }
    var isSortMenuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PawKeyTheme.colors.background)
            .padding(paddingValues),
    ) {
        TopBar(
            title = "루트 추천",
            onBackClick = {},
            isBackVisible = false,
            thickness = 0
        )

        CommunityTopImageHolder(
            imageList = persistentListOf()
        )

        Spacer(modifier = Modifier.height(17.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 19.dp, bottom = 8.dp)
        ) {
            Icon(
                imageVector = if (selectedFilters.isNotEmpty()) {
                    ImageVector.vectorResource(R.drawable.ic_course_option_selected_filter)
                } else {
                    ImageVector.vectorResource(R.drawable.ic_course_optin_filter)
                },
                contentDescription = "filter",
                tint = Color.Unspecified,
                modifier = Modifier
                    .noRippleClickable(onClick = onShowFilterSheet)
            )

            LazyRow (
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(filterList.size) {
                    val filter = filterList[it]
                    val isSelected = selectedFilters.contains(filter)

                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = if (isSelected) {
                                    PawKeyTheme.colors.primary
                                } else {
                                    PawKeyTheme.colors.defaultButton
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(
                                color = if (isSelected) {
                                    PawKeyTheme.colors.opacity5Primary
                                } else {
                                    PawKeyTheme.colors.background
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 9.dp)
                    ) {
                        Text(
                            text = filterList[it],
                            style = PawKeyTheme.typography.subButtonDefault,
                            color = if (isSelected) {
                                PawKeyTheme.colors.primary
                            } else {
                                PawKeyTheme.colors.defaultMiddle
                            }
                        )
                    }
                }
            }
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "${state.communityRouteList.size}개의 루트",
                style = PawKeyTheme.typography.subButtonDefault,
                color = PawKeyTheme.colors.defaultDark
            )

            Spacer(modifier = Modifier.weight(1f))

            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.noRippleClickable {
                        isSortMenuExpanded = true
                    }
                ) {
                    Text(
                        text = state.selectedSortedType.label,
                        style = PawKeyTheme.typography.subButtonDefault,
                        color = PawKeyTheme.colors.defaultDark
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_down),
                        contentDescription = "sort",
                        tint = Color.Unspecified
                    )
                }

                DropdownMenu(
                    expanded = isSortMenuExpanded,
                    onDismissRequest = { isSortMenuExpanded = false },
                    modifier = Modifier
                        .background(PawKeyTheme.colors.background)
                ) {
                    SortedType.entries.forEach { option ->
                        DropdownMenuItem(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (option == state.selectedSortedType) {
                                    PawKeyTheme.colors.opacity5Primary
                                } else {
                                    PawKeyTheme.colors.background
                                }),
                            text = {
                                Text(
                                    text = option.label,
                                    style = PawKeyTheme.typography.subButtonDefault,
                                    color = if (option == state.selectedSortedType) {
                                        PawKeyTheme.colors.primary
                                    } else {
                                        PawKeyTheme.colors.defaultMiddle
                                    }
                                )
                            },
                            onClick = {
                                // Todo: 정렬 타입 변경 로직
                                isSortMenuExpanded = false
                            }
                        )
                    }
                }
            }
        }

        LazyVerticalGrid (
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.communityRouteList.size) {
                RouteItem(
                    routeTitle = state.communityRouteList[it].title,
                    routeTime = state.communityRouteList[it].duration.toString(),
                    routeDate = state.communityRouteList[it].date,
                    location = state.communityRouteList[it].regionName,
                    routeImage = state.communityRouteList[it].imageUrl!!,
                    onClickHeart = {},
                    onClick = {}
                )
            }
        }
    }
}



@Preview
@Composable
private fun CommunityScreenPreview() {
    PawKeyTheme {
        CommunityScreen(
            paddingValues = PaddingValues(),
            state = CommunityState()
        )
    }
}
