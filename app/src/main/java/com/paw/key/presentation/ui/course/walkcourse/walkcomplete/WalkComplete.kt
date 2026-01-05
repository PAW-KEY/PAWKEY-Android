package com.paw.key.presentation.ui.course.walkcourse.walkcomplete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme

// Todo : 나중에 서버에서 줌
@Composable
fun WalkCompleteRoute(
    paddingValues: PaddingValues
) {
    WalkCompleteScreen(
        paddingValues = paddingValues
    )
}

@Composable
private fun WalkCompleteScreen(
    paddingValues: PaddingValues
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = PawKeyTheme.colors.background)
            .padding(paddingValues)
    ) {
        TopBar(
            title = "산책 완료",
            isBackVisible = false
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column (
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .shadow(
                    elevation = 10.dp,
                    spotColor = PawKeyTheme.colors.defaultMiddle,
                )
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = "",
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            color = PawKeyTheme.colors.defaultMiddle
                        )
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = "단지",
                        style = PawKeyTheme.typography.subTitle,
                        color = PawKeyTheme.colors.contents
                    )

                    Text(
                        text = "2025.06.26(금) | 오후 11:50",
                        style = PawKeyTheme.typography.subButtonDefault,
                        color = PawKeyTheme.colors.contents
                    )
                }
            }

            

        }
    }
}

@Preview
@Composable
private fun WalkCompletePreview() {
    PawKeyTheme {
        WalkCompleteScreen(
            paddingValues = PaddingValues()
        )
    }
}