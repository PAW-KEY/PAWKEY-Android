package com.paw.key.presentation.ui.mypage.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.paw.key.core.designsystem.component.DokiButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun WithdrawalReasonDialog(
    onDismiss: () -> Unit,
    onNextStep: (String) -> Unit,
    onKeepUsing: () -> Unit
) {
    val reasons = persistentListOf(
        "요즘 산책을 잘 안해요",
        "사용을 잘 안하게 돼요",
        "기능이 복잡해 사용이 어려워요",
        "원하는 기능이 없어요",
        "다른 서비스를 사용 중이에요",
        "기타"
    )

    var selectedReason by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PawKeyTheme.colors.background),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 32.dp, bottom = 16.dp)
            ) {
                // Title
                Text(
                    text = "어떤 이유로 탈퇴하시나요?",
                    style = PawKeyTheme.typography.mainButtonActive,
                    color = PawKeyTheme.colors.contents,
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Subtitle
                Text(
                    text = "도키가 더 좋아질 수 있도록 이유를 알려주세요.",
                    style = PawKeyTheme.typography.bodyDefault,
                    color = PawKeyTheme.colors.defaultMiddle,
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Radio options
                reasons.forEach { reason ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 11.dp)
                    ) {
                        CustomRadioButton(
                            isSelected = selectedReason == reason,
                            onClick = { selectedReason = reason },
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = reason,
                            style = if (selectedReason == reason) PawKeyTheme.typography.bodyActive else PawKeyTheme.typography.bodyDefault,
                            color = if (selectedReason == reason) PawKeyTheme.colors.contents else PawKeyTheme.colors.defaultDark,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DokiButton(
                        text = "다음 단계로",
                        enabled = false,
                        onClick = { if (selectedReason.isNotEmpty()) onNextStep(selectedReason) },
                        modifier = Modifier
                            .weight(1f),
                        isDialog = true
                    )

                    DokiButton(
                        text = "더 써볼래요",
                        enabled = true,
                        onClick = onKeepUsing,
                        modifier = Modifier
                            .weight(1f),
                        isDialog = true
                    )
                }
            }
        }
    }
}

@Composable
private fun CustomRadioButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
            .border(1.dp,
                if (isSelected) PawKeyTheme.colors.primary
                else PawKeyTheme.colors.defaultButton,
                CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(if (isSelected) PawKeyTheme.colors.primary else PawKeyTheme.colors.defaultButton)
        )
    }
}