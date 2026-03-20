package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun HomeEmptyRoute(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.img_home_empty),
            contentDescription = null
        )
        Text(
            text = "곧 추천 루트가 채워질 예정이에요\n추후에 인기루트를 확인하실 수 있어요!",
            color = PawKeyTheme.colors.defaultDark,
            style = PawKeyTheme.typography.subTitle,
            textAlign = TextAlign.Center
        )
    }
}