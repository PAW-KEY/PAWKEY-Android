package com.paw.key.presentation.ui.dbti.result.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun CharacterChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = PawKeyTheme.colors.primaryGra1,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = PawKeyTheme.colors.primary,
            style = PawKeyTheme.typography.caption12M,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterChipPreview() {
    PawKeyTheme {
        CharacterChip(text = "# Character")
    }
}