package com.paw.key.presentation.ui.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.disableNestedScroll
import com.paw.key.core.extension.noRippleClickable
import com.paw.key.presentation.ui.signup.model.DistrictModel
import com.paw.key.presentation.ui.signup.model.DongModel
import com.paw.key.presentation.ui.signup.model.GuModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun RegionSearchContent(
    regionList: ImmutableList<DistrictModel>,
    selectedGu: GuModel,
    selectedDong: DongModel,
    onRegionSelected: (GuModel, DongModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    var searchText by remember { mutableStateOf("") }

    var currentGu by remember(selectedGu) {
        mutableStateOf(if (selectedGu.id != 0) selectedGu else regionList.firstOrNull()?.gu ?: GuModel(0, ""))
    }

    val filteredRegionList = remember(searchText, regionList) {
        if (searchText.isBlank()) {
            regionList
        } else {
            regionList.filter {
                it.gu.name.contains(searchText, ignoreCase = true)
            }.toImmutableList()
        }
    }

    val currentDongList = remember(currentGu, filteredRegionList) {
        // 전체 리스트에서 찾아야 동 정보가 유실되지 않음
        regionList.find { it.gu.id == currentGu.id }?.dongs ?: persistentListOf()
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .background(
                color = PawKeyTheme.colors.background,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "산책 지역",
            style = PawKeyTheme.typography.subTitle,
            color = PawKeyTheme.colors.contents,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 24.dp,
                    bottom = 24.dp
                ),
            textAlign = TextAlign.Center
        )

        SignUpTextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            placeholder = "지역을 검색해보세요",
            suffix = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_signup_search),
                    contentDescription = "region search",
                    tint = PawKeyTheme.colors.contents
                )
            }
        )

        RegionSearchList(
            regionList = filteredRegionList,
            selectedGu = selectedGu,
            selectedDong = selectedDong,
            onRegionSelected = onRegionSelected,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
private fun RegionSearchList(
    regionList: ImmutableList<DistrictModel>,
    selectedGu: GuModel,
    selectedDong: DongModel,
    onRegionSelected: (GuModel, DongModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentGu by remember(selectedGu) {
        mutableStateOf(if (selectedGu.id != 0) selectedGu else regionList.firstOrNull()?.gu ?: GuModel(0,""))
    }

    val currentDongList = remember(currentGu, regionList) {
        regionList.find { it.gu.id == currentGu.id }?.dongs ?: persistentListOf()
    }

    Row(
        modifier = modifier
            .padding(vertical = 8.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .disableNestedScroll()
                .weight(0.45f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(
                items = regionList,
                key = { it.gu.id }
            ) { item ->
                RegionItem(
                    name = item.gu.name,
                    isSelected = item.gu.id == currentGu.id,
                    onClick = { currentGu = item.gu },
                    textAlign = TextAlign.Center
                )
            }
        }

        VerticalDivider(
            thickness = 1.dp,
            color = PawKeyTheme.colors.defaultMiddle,
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    color = PawKeyTheme.colors.defaultMiddle,
                    shape = RoundedCornerShape(8.dp)
                )
        )

        LazyColumn(
            modifier = Modifier
                .disableNestedScroll()
                .weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            items(
                items = currentDongList,
                key = { it.id }
            ) { dong ->
                RegionItem(
                    name = dong.name,
                    isSelected = (dong.id == selectedDong.id) && (currentGu.id == selectedGu.id),
                    onClick = {
                        onRegionSelected(currentGu, dong)
                    },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun RegionItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center
) {
    val textColor = if (isSelected) PawKeyTheme.colors.background else PawKeyTheme.colors.contents

    val backgroundColor =
        if (isSelected) PawKeyTheme.colors.primary else PawKeyTheme.colors.background

    Text(
        text = name,
        style = PawKeyTheme.typography.bodyActive,
        color = textColor,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 11.dp
            )
            .noRippleClickable {
                onClick()
            },
        textAlign = textAlign
    )
}


@Preview
@Composable
private fun RegionSearchContentPreview() {
    PawKeyTheme {
        RegionSearchContent(
            regionList = persistentListOf(),
            selectedGu = GuModel(0, ""),
            selectedDong = DongModel(0, ""),
            onRegionSelected = { _, _ -> }
        )
    }
}