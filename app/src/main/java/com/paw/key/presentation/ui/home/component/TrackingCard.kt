package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme


@Preview
@Composable
private fun PreviewTrackingCard() {
    PawKeyTheme {
        TrackingCard(
        )
    }

}


@Composable
fun TrackingCard(

){
    Box(
        modifier = Modifier
            .width(235.dp)
            .height(110.dp)
            .background(
                color= PawKeyTheme.colors.black)
    ) {

    }
}