package com.paw.key.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
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
    }

    val visibleTags = when {
        tags.size <= 3 -> tags //3개이하면 tags 전부 보여줌
        isExpanded -> tags
        else -> tags.take(3) //아니라면 3개만 뽑아서 보여줌
    }

    val hiddenCount = tags.size - 3 //가려진 칩 개수

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 초록칩 보여주기
        visibleTags.forEach { tag ->
            SubChip(text = tag)
        }

        // 회색칩 조건 분기
        if (!isExpanded && tags.size > 3) {
            SubChip(
                text = "+$hiddenCount",
                isActionChip = true,
                onClick = {
                    isExpanded = true // 클릭되면 전체 보여줌
                }
            )
        }
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
                "물그릇 비치"
            )
        )
    }
}