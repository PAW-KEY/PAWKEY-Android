package com.paw.key.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable

@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onClickTitle : () -> Unit = {},
    isBackVisible: Boolean = true,
) {
    Column (
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = PawKeyTheme.colors.background)
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            if (isBackVisible) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_black),
                    contentDescription = "뒤로가기",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .noRippleClickable(onClick = onBackClick)
                )
            }

            Text(
                text = title,
                style = PawKeyTheme.typography.subTitle,
                modifier = Modifier
                    .align(Alignment.Center)
                    .noRippleClickable(onClickTitle)
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = PawKeyTheme.colors.defaultButton
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TopBarPreview() {
    PawKeyTheme {
        TopBar(
            title = "산책 완료",
            onBackClick = {},
            isBackVisible = true,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}