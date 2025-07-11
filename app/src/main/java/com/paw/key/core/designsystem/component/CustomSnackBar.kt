package com.paw.key.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun CustomSnackBar(
    data: SnackbarData,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(PawKeyTheme.colors.black.copy(alpha = 0.6f))
            .padding(16.dp)
    ) {
        Text(
            text = data.visuals.message,
            style = PawKeyTheme.typography.body14M,
            color = PawKeyTheme.colors.white1,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}
