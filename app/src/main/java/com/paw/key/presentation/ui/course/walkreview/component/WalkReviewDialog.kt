package com.paw.key.presentation.ui.course.walkreview.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.paw.key.R
import com.paw.key.core.designsystem.component.DokiButton
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun WalkReviewDialog(
    navigateHome: () -> Unit,
    navigateWalkDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog (
        onDismissRequest = {
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
    ) {
        Card (
            modifier = modifier,
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = PawKeyTheme.colors.white1
            )
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(35.dp))

                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_walk_review_dialog_paw),
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                )

                Spacer(modifier = Modifier.height(35.dp))

                Text(
                    text = "후기가 등록이 완료되었어요!",
                    style = PawKeyTheme.typography.mainButtonActive,
                    color = PawKeyTheme.colors.contents,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "    덕분에 DOKI가 보호자님을 더 잘 알게 됐어요.\n" +
                            "이 정보로 다음엔 더 완벽한 경로를 추천해 드릴게요.",
                    style = PawKeyTheme.typography.bodySmall,
                    color = PawKeyTheme.colors.defaultMiddle,
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DokiButton(
                        text = "홈으로 돌아가기",
                        enabled = false,
                        onClick = navigateHome,
                        modifier = Modifier.weight(1f),
                        isDialog = true
                    )

                    DokiButton(
                        text = "자세히 보러가기",
                        enabled = true,
                        onClick = navigateWalkDetail,
                        modifier = Modifier.weight(1f),
                        isDialog = true
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun WalkReviewDialogPreview() {
    PawKeyTheme {
        WalkReviewDialog(
            navigateHome = {},
            navigateWalkDetail = {}
        )
    }
}