package com.paw.key.presentation.ui.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable


@Composable
fun SignUpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    enabled: Boolean = true,
) {
    val isClicked = remember { mutableStateOf(false) }

    val borderColor = when {
        !enabled -> PawKeyTheme.colors.gray100
        isClicked.value -> PawKeyTheme.colors.green500
        else -> PawKeyTheme.colors.gray200
    }


    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(8.dp))
            .noRippleClickable {
                isClicked.value = true
            }
            .background(color = PawKeyTheme.colors.white1)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            ),
        textStyle = PawKeyTheme.typography.body14R,
        maxLines = 1,
        enabled = enabled,
        decorationBox = { innerTextField ->
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(horizontal = 16.dp),
                contentAlignment = androidx.compose.ui.Alignment.CenterStart
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = PawKeyTheme.typography.body14R,
                        color = PawKeyTheme.colors.gray200
                    )
                }
                innerTextField()
            }
        }
    )
}

