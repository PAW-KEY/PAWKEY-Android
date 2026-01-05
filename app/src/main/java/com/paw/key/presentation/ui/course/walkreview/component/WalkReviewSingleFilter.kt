package com.paw.key.presentation.ui.course.walkreview.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun WalkReviewSingleFilter(
    title : String,
    filterList: ImmutableList<String>,
    modifier: Modifier = Modifier,
    selectedItem: String = "",
    onItemSelected: (String) -> Unit = {},
) {
    Column (
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row (
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
                text = "(단일 선택 가능)",
                style = PawKeyTheme.typography.subButtonDefault,
                color = PawKeyTheme.colors.defaultMiddle
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = PawKeyTheme.colors.defaultBright,
                    shape = RoundedCornerShape(8.dp)
                ),
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            filterList.forEach { item ->
                FilterItem(
                    text = item,
                    isSelected = item == selectedItem,
                    onClickFilter = { onItemSelected(item) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun FilterItem(
    text: String,
    isSelected: Boolean,
    onClickFilter: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) PawKeyTheme.colors.primary else Color.Transparent
    val textColor = if (isSelected) PawKeyTheme.colors.background else PawKeyTheme.colors.defaultMiddle

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color = backgroundColor)
            .noRippleClickable(
                onClick = onClickFilter
            )
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = PawKeyTheme.typography.subButtonActive,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WalkReviewSingleFilterPreview() {
    PawKeyTheme {
        WalkReviewSingleFilter(
            title = "필터",
            filterList = persistentListOf("적음", "평법", "많음"),
            selectedItem = "적음"
        )
    }
}