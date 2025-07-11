package com.paw.key.core.designsystem.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipRow(
    tags: List<String>,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { //토글?이 열려있는지
        mutableStateOf(false)
    }

    LaunchedEffect(isExpanded) {
        Log.e("lauaua", "$isExpanded")
    }

    val visibleTags = when {
        tags.size <= 3 -> tags //3개이하면 tags 전부 보여줌
        isExpanded -> tags
        else -> tags.take(3) //아니라면 3개만 뽑아서 보여줌
    }

    val hiddenCount = tags.size - visibleTags.size //가려진 칩 개수

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SubChip(
            text = "+$hiddenCount",
            isActionChip = isExpanded, // 회색칩으로 표시!
            modifier = modifier,
            onClick = {
                Log.e("clcicc", "$isExpanded")
                isExpanded = !isExpanded
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChipRowPreview() {
    PawKeyTheme {
        ChipRow(
            tags = listOf(
                "이륜차 거의 없음",
                "배변 쓰레기통",
                "쉼터",
                "CCTV 있음",
                "물그릇 비치","이륜차 거의 없음",
                "배변 쓰레기통",
                "쉼터",
            )
        )
    }
}