package com.paw.key.presentation.ui.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.component.SubChip
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun FilterChipDivider(
    hiddenCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 2.dp,
            color = PawKeyTheme.colors.defaultButton,
        )

        SubChip(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            text = "+ $hiddenCount",
            isActionChip = false,
            isDividerChip = true,
            onClick = onClick
        )

        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 2.dp,
            color = PawKeyTheme.colors.defaultButton,
        )
    }
}

@Preview
@Composable
private fun FilterChipDividerPreview() {
    PawKeyTheme {
        FilterChipDivider(
            hiddenCount = 10,
            onClick = {}
        )
    }
}