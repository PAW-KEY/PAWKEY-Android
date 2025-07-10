package com.paw.key.presentation.ui.course.walkrecord.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun WalkReviewTextField(
    textValue: String,
    placeHolder : String,
    onTextChanged: (String) -> Unit,

    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = textValue,
        onValueChange = {
            onTextChanged(it)
        },
        singleLine = true,
        textStyle = PawKeyTheme.typography.body14R,
        decorationBox = { innerTextField ->
             Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = PawKeyTheme.colors.white2,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = PawKeyTheme.colors.gray50,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                contentAlignment = Alignment.TopStart
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
        }
    )
}


@Preview
@Composable
private fun WalkReviewTextFieldPreview() {
    PawKeyTheme {
        WalkReviewTextField(
            textValue = "",
            placeHolder = "제목을 입력해주세요.",
            onTextChanged = {}
        )
    }
}