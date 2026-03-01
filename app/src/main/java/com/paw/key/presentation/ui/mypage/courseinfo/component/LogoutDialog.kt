package com.paw.key.presentation.ui.mypage.courseinfo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun LogoutDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = onDismissRequest) {

        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = PawKeyTheme.colors.white1,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "로그아웃",
                    color = PawKeyTheme.colors.contents,
                    style = PawKeyTheme.typography.mainButtonActive,
                )
                Text(
                    text = "진짜로 로그아웃 하시게요?",
                    color = PawKeyTheme.colors.defaultMiddle,
                    style = PawKeyTheme.typography.bodyDefault
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PawkeyButton(
                        text = "취소",
                        enabled = true,
                        onClick = onDismissRequest,
                        isBackGround = true,
                        modifier = Modifier.weight(1f)
                    )

                    PawkeyButton(
                        text = "로그아웃",
                        enabled = true,
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun ReviewLogoutDialog() {
    PawKeyTheme {
        LogoutDialog(
            onDismissRequest = {},
            onConfirm = {}
        )
    }
}