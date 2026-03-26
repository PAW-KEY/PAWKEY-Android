package com.paw.key.presentation.ui.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.paw.key.core.designsystem.component.DokiButton
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun ImageTypeSelectDialog(
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onDefaultClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false,
        decorFitsSystemWindows = false
    ),
) {
    val drawLineColor = PawKeyTheme.colors.defaultButton

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = PawKeyTheme.colors.black.copy(alpha = 0.75f))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = PawKeyTheme.colors.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    // 카메라
                    TextButton(
                        onClick = {
                            onCameraClick()
                            onDismissRequest()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                drawLine(
                                    color = drawLineColor,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = strokeWidth
                                )
                            }
                    ) {
                        Text(
                            text = "카메라",
                            style = PawKeyTheme.typography.mainButtonDefault,
                            color = PawKeyTheme.colors.primary,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }

                    // 갤러리
                    TextButton(
                        onClick = {
                            onGalleryClick()
                            onDismissRequest()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                drawLine(
                                    color = drawLineColor,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = strokeWidth
                                )
                            }
                    ) {
                        Text(
                            text = "갤러리",
                            style = PawKeyTheme.typography.mainButtonDefault,
                            color = PawKeyTheme.colors.primary,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }

                    // 기본 이미지
                    TextButton(
                        onClick = {
                            onDefaultClick()
                            onDismissRequest()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "기본 이미지",
                            style = PawKeyTheme.typography.mainButtonDefault,
                            color = PawKeyTheme.colors.primary,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                DokiButton(
                    text = "취소하기",
                    enabled = true,
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        }
    }
}

@Preview
@Composable
private fun ImageTypeSelectPreview() {
    PawKeyTheme {
        val drawLineColor = PawKeyTheme.colors.defaultButton

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PawKeyTheme.colors.black.copy(alpha = 0.75f))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = PawKeyTheme.colors.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    // 카메라
                    TextButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                drawLine(
                                    color = drawLineColor,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = strokeWidth
                                )
                            }
                    ) {
                        Text(
                            text = "카메라",
                            style = PawKeyTheme.typography.mainButtonDefault,
                            color = PawKeyTheme.colors.primary,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }

                    // 갤러리
                    TextButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                drawLine(
                                    color = drawLineColor,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = strokeWidth
                                )
                            }
                    ) {
                        Text(
                            text = "갤러리",
                            style = PawKeyTheme.typography.mainButtonDefault,
                            color = PawKeyTheme.colors.primary,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }

                    // 기본 이미지
                    TextButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "기본 이미지",
                            style = PawKeyTheme.typography.mainButtonDefault,
                            color = PawKeyTheme.colors.primary,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                DokiButton(
                    text = "취소",
                    enabled = true,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        }
    }

}
