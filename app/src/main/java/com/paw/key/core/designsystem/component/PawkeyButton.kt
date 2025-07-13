package com.paw.key.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable


@Preview(showBackground = true)
@Composable
private fun PreviewPawkeyButton() {
    PawKeyTheme {
        Column (
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ){
            // 초록색 버튼
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = true,
                onClick = {}
            )

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
                isBorder = false,
                isBackGround = true
            )

            // 비활성화 - 빈 상자
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = false,
                onClick = {},
                isBackGround = true
            )

            // 투명 border
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = true,
                onClick = {},
                isBorder = true,
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
    isBorder: Boolean = false
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
        else -> Color.Transparent
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .then(if (isBorder || isBackGround) Modifier.border(1.dp, borderColor, RoundedCornerShape(8.dp)) else Modifier)
            .noRippleClickable{ onClick() }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = PawKeyTheme.typography.body16Sb,
            color = contentColor
        )
    }
}

