package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Preview
@Composable
private fun PreviewTopBar() {
    PawKeyTheme {
        TopBar(
            location = "강남구 역삼동",
            onLocationClick = {},
        )

    }
}

@Composable
fun TopBar(
    location: String,
    onLocationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(97.dp)
            .background(color = PawKeyTheme.colors.white1),
        shape = RoundedCornerShape(
            bottomStart = 15.dp,
            bottomEnd = 15.dp,
        ),
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = PawKeyTheme.colors.black)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            DateChip(
                text = "D+36",
            )

            Text(
                text = stringResource(R.string.ic_home_topbar_text),
                modifier = Modifier.padding(start = 4.dp),
                color = PawKeyTheme.colors.white1,
                style = PawKeyTheme.typography.body14M,
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onLocationClick() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home_location),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )

                Text(
                    text = location,
                    modifier = Modifier
                        .padding(horizontal = 2.dp),
                    color = PawKeyTheme.colors.white1,
                    style = PawKeyTheme.typography.body14Sb,
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_home_under_arrow),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }
        }
    }
}

@Composable
private fun DateChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .background(
                color = PawKeyTheme.colors.green400,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(horizontal = 12.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            color = PawKeyTheme.colors.white1,
            style = PawKeyTheme.typography.caption12Sb1
        )
    }
}