package com.paw.key.core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme


@Preview(showBackground = true)
@Composable
private fun PreviewPawkeyButton() {
    PawKeyTheme {
        Column {
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = true,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(12.dp))
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = false,
                onClick = {}
            )
        }
    }
}

@Composable
fun PawkeyButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PawKeyTheme.colors.beige500,
            contentColor = PawKeyTheme.colors.white1,
            disabledContainerColor = PawKeyTheme.colors.gray200,
            disabledContentColor = PawKeyTheme.colors.white1
        )
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(vertical = 18.dp),
            style = PawKeyTheme.typography.body14M
        )
    }
}

