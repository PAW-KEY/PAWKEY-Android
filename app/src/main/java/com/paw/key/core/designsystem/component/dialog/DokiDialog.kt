package com.paw.key.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.paw.key.core.designsystem.component.DokiBorderButton
import com.paw.key.core.designsystem.component.DokiButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable

@Composable
fun DokiDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    subDescription: String? = null,
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false,
        decorFitsSystemWindows = false
    ),
    confirmText: String? = null,
    dismissText: String? = null,
    content: (@Composable () -> Unit)? = null
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = properties,
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = PawKeyTheme.colors.contents.copy(alpha = 0.75f))
                .noRippleClickable(onClick = onDismiss)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable(onClick = {})
                    .background(
                        color = PawKeyTheme.colors.background,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                if (title != null) {
                    Text(
                        text = title,
                        color = PawKeyTheme.colors.contents,
                        style = PawKeyTheme.typography.mainButtonActive,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                if (content != null) {
                    content()
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = subDescription.orEmpty(),
                    color = PawKeyTheme.colors.defaultMiddle,
                    style = PawKeyTheme.typography.bodyDefault,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DokiBorderButton(
                        text = dismissText.orEmpty(),
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        enabled = true,
                        isDialog = true
                    )

                    DokiButton(
                        text = confirmText.orEmpty(),
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f),
                        isDialog = true,
                        enabled = true
                    )
                }
            }
        }
    }
}