package com.paw.key.presentation.ui.mypage.route.userinfo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun UserEditTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    suffix: @Composable (() -> Unit)? = null,
) {
    val isFocused = remember { mutableStateOf(false) }

    val borderColor = when {
        !enabled -> PawKeyTheme.colors.defaultMiddle
        isFocused.value -> PawKeyTheme.colors.primary
        else -> PawKeyTheme.colors.defaultMiddle
    }

    // Todo : Gra로 변경
    val customTextSelectionColors = TextSelectionColors(
        handleColor = PawKeyTheme.colors.primary,
        backgroundColor = PawKeyTheme.colors.primary.copy(alpha = 0.4f)
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            visualTransformation = visualTransformation,
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                }
                .background(color = PawKeyTheme.colors.background)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(8.dp)
                ),
            textStyle = PawKeyTheme.typography.bodyActive,
            maxLines = if (singleLine) 1 else Int.MAX_VALUE,
            singleLine = singleLine,
            enabled = enabled,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            cursorBrush = SolidColor(PawKeyTheme.colors.primary),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = PawKeyTheme.typography.bodyDefault,
                                color = PawKeyTheme.colors.defaultMiddle
                            )
                        }
                        innerTextField()
                    }
                    suffix?.invoke()
                }
            }
        )
    }
}

@Preview
@Composable
private fun UserEditTextFieldPreview() {
    PawKeyTheme {
        UserEditTextField(
            value = "",
            onValueChange = {},
            placeholder = "이름을 입력해주세요."
        )
    }
}