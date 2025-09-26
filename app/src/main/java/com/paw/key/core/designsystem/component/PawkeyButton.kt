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
import com.paw.key.core.extension.noRippleClickable


@Preview(showBackground = true)
@Composable
private fun PreviewPawkeyButton() {
    PawKeyTheme {
        Column (
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ){
            // 기본 초록색 버튼 (활성화)
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = true,
                onClick = {}
            )

            // 기본 회색 버튼 (비활성화)
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = false,
                onClick = {}
            )

            // 활성화 - 흰 배경, 초록 테두리
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = true,
                onClick = {},
                isBackGround = true
            )

            // 비활성화 - 흰 배경, 회색 테두리
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = false,
                onClick = {},
                isBackGround = true
            )

            // 활성화 - 흰 배경, 테두리 없음
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = true,
                onClick = {},
                isBackGround = true,
                isBorder = false
            )

            // 비활성화 - 흰 배경, 테두리 없음
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = false,
                onClick = {},
                isBackGround = true,
                isBorder = false
            )

            // 초록색 버튼 - 테두리 없음
            PawkeyButton(
                text = "신규 계정으로 회원가입",
                enabled = true,
                onClick = {},
                isBorder = false
            )
        }
    }
}

// Todo : 이 더러운 분기의 버튼 제거예정
@Composable
fun PawkeyButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isBackGround: Boolean = false,
    isBorder: Boolean = true
) {

    val actualEnabled = if (!isBackGround) {
        enabled
    } else {
        enabled
    }

    val backgroundColor = when {
        actualEnabled && !isBackGround -> PawKeyTheme.colors.primary
        actualEnabled && isBackGround -> PawKeyTheme.colors.white1
        !actualEnabled && isBackGround -> PawKeyTheme.colors.white1
        else -> PawKeyTheme.colors.gray200
    }

    val contentColor = when {
        actualEnabled && !isBackGround -> PawKeyTheme.colors.white1
        actualEnabled && isBackGround -> PawKeyTheme.colors.green500
        !actualEnabled && isBackGround -> PawKeyTheme.colors.gray100
        else -> PawKeyTheme.colors.white1
    }

    val borderColor = when {
        !isBorder -> Color.Transparent
        actualEnabled && isBackGround -> PawKeyTheme.colors.green500
        !actualEnabled && isBackGround -> PawKeyTheme.colors.gray200
        else -> Color.Transparent
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable {
                if (actualEnabled) onClick()
            }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = PawKeyTheme.typography.mainButtonActive,
            color = contentColor
        )
    }
}