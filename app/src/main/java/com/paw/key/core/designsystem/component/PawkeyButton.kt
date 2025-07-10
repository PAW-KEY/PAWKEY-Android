package com.paw.key.core.designsystem.component

import androidx.compose.foundation.BorderStroke
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
            // 초록색 버튼
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = true,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 회색 버튼
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = false,
                onClick = {}
            )

            // 활성화 - 빈 상자
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = true,
                onClick = {},
                isBackGround = true
            )

            // 비활성화 - 빈 상자
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = false,
                onClick = {},
                isBackGround = true
            )
        }
    }
}

@Composable
fun PawkeyButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isBackGround: Boolean = false,
) {
    val backgroundColor = when {
        enabled && !isBackGround -> PawKeyTheme.colors.green500
        enabled && isBackGround -> PawKeyTheme.colors.white1
        !enabled && isBackGround -> PawKeyTheme.colors.white1
        else -> PawKeyTheme.colors.gray200
    }

    val contentColor = when {
        enabled && !isBackGround -> PawKeyTheme.colors.white1
        enabled && isBackGround -> PawKeyTheme.colors.green500
        !enabled && isBackGround -> PawKeyTheme.colors.gray100
        else -> PawKeyTheme.colors.white1
    }

    val borderColor = when {
        enabled && isBackGround -> PawKeyTheme.colors.green500
        !enabled && isBackGround -> PawKeyTheme.colors.gray200
        else -> PawKeyTheme.colors.white1
    }

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = backgroundColor,
            disabledContentColor = contentColor
        ),
        border = if (isBackGround) BorderStroke(3.dp, borderColor) else null
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(vertical = 14.dp),
            style = PawKeyTheme.typography.body16Sb
        )
    }
}
