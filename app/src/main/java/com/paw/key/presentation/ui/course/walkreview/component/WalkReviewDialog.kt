package com.paw.key.presentation.ui.course.walkreview.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable

@Composable
fun WalkReviewDialog(
    onClickOk: () -> Unit,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("dialog_animation.json"))
    
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = Int.MAX_VALUE,
    )

    Dialog (
        onDismissRequest = {
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
    ) {
        Card (
            shape = RoundedCornerShape(8.dp),
            modifier = modifier
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = PawKeyTheme.colors.white1
            )
        ) {
            Column (
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = {
                        progress },
                    modifier = Modifier
                        .size(90.dp)
                        .padding(bottom = 8.dp)
                )

                Text(
                    text = "후기가 등록되었어요!",
                    style = PawKeyTheme.typography.head18Sb,
                    color = PawKeyTheme.colors.black,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                Text(
                    text = "  덕분에 PAWKEY가 보호자님을 더 잘 알게 됐어요. \n이 정보로 다음엔 더 완벽한 경로를 추천해 드릴게요.",
                    style = PawKeyTheme.typography.caption12R,
                    color = PawKeyTheme.colors.gray300,
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                )

                Text(
                    text = "확인",
                    style = PawKeyTheme.typography.body14Sb,
                    color = PawKeyTheme.colors.white1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                        .background(
                            color = PawKeyTheme.colors.green500,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp)
                        .noRippleClickable {
                            onClickOk()
                        },
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
private fun WalkReviewDialogPreview() {
    PawKeyTheme {
        WalkReviewDialog(
            onClickOk = {}
        )
    }
}