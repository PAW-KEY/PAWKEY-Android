package com.paw.key.presentation.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.component.UrlImage
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun DetailRoute(
    paddingValues: PaddingValues,
) {
    DetailScreen(
        paddingValues = paddingValues
    )
}
@Composable
private fun DetailScreen(
    paddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        TopBar(
            title = "루트 상세정보",
            thickness = 2
        )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            UrlImage(
                url = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .align(Alignment.TopCenter)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                    .dropShadow(
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                        shadow = Shadow(
                            radius = 19f.dp,
                            alpha = 0.1f,
                            color = Color(0xff000000),
                        )
                    )
                    .background(
                        color = PawKeyTheme.colors.background,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "단지와 룰루랄라 룰루랄라 룰루랄라",
                    style = PawKeyTheme.typography.header3,
                    color = PawKeyTheme.colors.contents,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = PawKeyTheme.colors.defaultButton,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    UrlImage(
                        url = "",
                        modifier = Modifier
                            .size(43.dp)
                            .clip(RoundedCornerShape(50.dp))
                    )

                    Text(
                        text = "단지",
                        style = PawKeyTheme.typography.subTitle,
                        color = PawKeyTheme.colors.defaultDark
                    )
                }

                HorizontalDivider(
                    thickness = 1.dp,
                    color = PawKeyTheme.colors.defaultButton,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    PawKeyTheme {
        DetailScreen(
            paddingValues = PaddingValues()
        )
    }
}