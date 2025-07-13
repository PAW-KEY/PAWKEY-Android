package com.paw.key.presentation.ui.signup.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Preview(showBackground = true)
@Composable
private fun PreviewLocationList() {
    PawKeyTheme {
        LocationList(
            selected = "서울",
            locations = listOf("서울", "경기", "인천", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"),
            onLocationSelected = {}
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LocationList(
    locations: List<String>,
    selected: String,
    onLocationSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        maxItemsInEachRow = 3
    ) {
        locations.forEach { location ->
            LocationButton(
                location = location,
                isEnable = location == selected,
                onClick = {
                    onLocationSelected(location)
                }
            )
        }
    }
}

