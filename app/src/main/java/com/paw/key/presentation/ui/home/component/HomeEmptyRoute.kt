package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun HomeEmptyRoute(
    modifier: Modifier = Modifier
) {
    val configuration = LocalWindowInfo.current.containerSize
    val screenWidthDp = configuration.width

    val emptyImageSize = if (screenWidthDp < 600) {
        150.dp
    } else {
        250.dp
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.img_home_empty),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(emptyImageSize)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "곧 추천 루트가 채워질 예정이에요\n추후에 인기루트를 확인하실 수 있어요!",
            color = PawKeyTheme.colors.defaultDark,
            style = PawKeyTheme.typography.subTitle,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun HomeEmptyRoutePreview() {
    PawKeyTheme {
        HomeEmptyRoute()
    }
}
