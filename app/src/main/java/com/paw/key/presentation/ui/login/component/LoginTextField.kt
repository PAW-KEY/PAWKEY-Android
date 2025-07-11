package com.paw.key.presentation.ui.login.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun LoginTextField(
    textValue: String,
    placeHolder: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    suffix: @Composable (() -> Unit)? = null
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = PawKeyTheme.colors.green500,
        backgroundColor = PawKeyTheme.colors.green500,
    )

    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors,
    ) {
        BasicTextField(
            value = textValue,
            onValueChange = onTextChanged,
            cursorBrush = SolidColor(PawKeyTheme.colors.green500),
            singleLine = true,
            textStyle = PawKeyTheme.typography.body14R,
            visualTransformation = if (!isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            decorationBox = { innerTextField ->
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(
                            color = PawKeyTheme.colors.white1,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = PawKeyTheme.colors.gray200,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            if (textValue.isEmpty()) {
                                Text(
                                    text = placeHolder,
                                    color = PawKeyTheme.colors.gray200,
                                    style = PawKeyTheme.typography.body14R
                                )
                            }
                            innerTextField()
                        }

                        suffix?.invoke()
                    }
                }
            },
        )
    }
}



@Preview
@Composable
private fun LoginTextFieldPreview() {
    PawKeyTheme {
        LoginTextField(
            textValue = "",
            placeHolder = "제목을 입력해주세요.",
            onTextChanged = {}
        )
    }
}