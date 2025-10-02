package com.paw.key.presentation.ui.signup.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun SignUpSubHeader() {
    Text(
        text = "산책하기 전 \n간단한 정보를 입력해주세요",
        color = PawKeyTheme.colors.contents,
        style = PawKeyTheme.typography.header2,
        modifier = Modifier
            .padding(
                top = 20.dp,
                start = 16.dp,
                end = 16.dp
            )
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        text = "서비스 시작을 위해 간단한 정보를 입력해주세요!",
        color = PawKeyTheme.colors.defaultMiddle,
        style = PawKeyTheme.typography.bodyDefault,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    )
}

@Preview
@Composable
private fun SignUpSubHeaderPreview() {
    PawKeyTheme {
        SignUpSubHeader()
    }
}