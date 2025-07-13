package com.paw.key.presentation.ui.signup.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpLoationGrid() {
    PawKeyTheme {
        SignUpLoationGrid()
    }
}

@Composable
fun SignUpLoationGrid(
    modifier: Modifier = Modifier,
) {
    LocationButton(
        location = "개포동",
        isEnable = true,
        onClick = {}
    )
}