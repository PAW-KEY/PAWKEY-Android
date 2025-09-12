package com.paw.key.presentation.ui.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpUserSelectButton() {
    PawKeyTheme {
        SignUpUserSelectButton(
            user = "남성",
            isSelect = true,
            onClick = {}
        )
    }
}

@Composable
fun SignUpUserSelectButton(
    user: String,
    isSelect: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(48.dp)
            .width(158.dp)
            .background(color = PawKeyTheme.colors.white1)
            .noRippleClickable { onClick() }
            .border(
                width = if (isSelect){
                    2.dp
                } else {
                    1.dp
                },
                color = if (isSelect) {
                    PawKeyTheme.colors.green500
                } else {
                    PawKeyTheme.colors.gray200
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = user,
            color = if (isSelect) {
                PawKeyTheme.colors.green500
            } else {
                PawKeyTheme.colors.gray200
            },
            style = if (isSelect) {
                PawKeyTheme.typography.body14Sb
            } else {
                PawKeyTheme.typography.body14R
            },
            modifier = Modifier
                .padding(horizontal = 21.dp, vertical = 13.dp)
        )
    }
}
