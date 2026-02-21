package com.paw.key.presentation.ui.course.walkreview.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun WalkReviewMultipleFilter(
    title: String,
    filterList: ImmutableList<String>,
    selectedItems: ImmutableList<String>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = PawKeyTheme.typography.subTitle,
                color = PawKeyTheme.colors.contents
            )

            Text(
                text = "(복수 선택 가능)",
                style = PawKeyTheme.typography.subButtonDefault,
                color = PawKeyTheme.colors.defaultMiddle
            )
        }

        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val rows = filterList.chunked(2)

            rows.forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { item ->
                        Box(modifier = Modifier.weight(1f)) {
                            MultipleFilterItem(
                                text = item,
                                isSelected = selectedItems.contains(item),
                                onClick = { onItemClick(item) }
                            )
                        }
                    }

                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun MultipleFilterItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) PawKeyTheme.colors.primary else PawKeyTheme.colors.defaultMiddle
    val backgroundColor = if (isSelected) PawKeyTheme.colors.opacity5Primary else Color.White
    val textColor = if (isSelected) PawKeyTheme.colors.primary else PawKeyTheme.colors.defaultMiddle
    val fontStyle = if (isSelected) PawKeyTheme.typography.subButtonActive else PawKeyTheme.typography.subButtonDefault

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = fontStyle,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WalkReviewMultipleFilterPreview() {
    val dummyFilters = listOf("차량 적음", "보도 넓음", "야간 밝음", "보도/차도 분리", "킥보드/자전거 적음").toPersistentList()
    var selectedItems by remember { mutableStateOf(listOf("차량 적음", "킥보드/자전거 적음").toPersistentList()) }

    PawKeyTheme {
        Box(modifier = Modifier.padding(16.dp).background(Color.White)) {
            WalkReviewMultipleFilter(
                title = "산책로 특징",
                filterList = dummyFilters,
                selectedItems = selectedItems,
                onItemClick = { clickedItem ->
                    selectedItems = if (selectedItems.contains(clickedItem)) {
                        selectedItems.remove(clickedItem)
                    } else {
                        selectedItems.add(clickedItem)
                    }
                }
            )
        }
    }
}