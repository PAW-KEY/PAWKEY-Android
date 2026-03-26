package com.paw.key.presentation.ui.signup.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Preview(showBackground = true)
@Composable
private fun PreviewFormField() {
    PawKeyTheme {
        FormField(
            label = "이름",
            content = {
                SignUpTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = "이름을 입력해주세요."
                )
            }
        )
    }
}

@Composable
fun FormField(
    label: String,
    modifier: Modifier = Modifier,
    isDuplicate: Boolean = false,
    content: @Composable () -> Unit,
) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = PawKeyTheme.colors.black,
            style = PawKeyTheme.typography.body16Sb
        )

        Spacer(modifier = Modifier.weight(1f))

        if (isDuplicate) {
            Text(
                text = "*이미 존재하는 닉네임입니다",
                style = PawKeyTheme.typography.buttonSmall,
                color = PawKeyTheme.colors.dokiRed
            )
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    content()
}
