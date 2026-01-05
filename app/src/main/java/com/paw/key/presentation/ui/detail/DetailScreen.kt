package com.paw.key.presentation.ui.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun DetailRoute(
    paddingValues: PaddingValues,
) {
    DetailScreen(
        paddingValues = paddingValues
    )
}

@Composable
private fun DetailScreen(
    paddingValues: PaddingValues,
) {

}

@Preview
@Composable
private fun DetailScreenPreview() {
    PawKeyTheme {
        DetailScreen(
            paddingValues = PaddingValues()
        )
    }
}