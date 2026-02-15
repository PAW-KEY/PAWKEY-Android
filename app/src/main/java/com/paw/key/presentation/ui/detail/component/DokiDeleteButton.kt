package com.paw.key.presentation.ui.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable

@Composable
fun DokiDeleteButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = PawKeyTheme.colors.dokiRed,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = PawKeyTheme.colors.background,
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable(onClick = onClick)
            .padding(vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = PawKeyTheme.typography.subTitle,
            color = PawKeyTheme.colors.dokiRed,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun DokiBorderButtonPreview() {
    PawKeyTheme {
        DokiDeleteButton(
            text = "삭제하기",
            onClick = {}
        )
    }
}
