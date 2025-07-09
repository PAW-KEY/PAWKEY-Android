package com.paw.key.presentation.ui.course.walkcomplete.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun WalkCompleteHeader(
    bitmap : Bitmap?,
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier
    ){
        AsyncImage(
            model = bitmap,
            contentDescription = "profile",
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = androidx.compose.foundation.shape.CircleShape
                )
                .size(45.dp)
        )

        Column {
            Text(
                text = "포비",
                color = Color.Black
            )

            Text(
                text = "2025.06.26(금) | 오후 11:50",
                color = Color.LightGray
            )
        }
    }
}

@Preview
@Composable
private fun WalkCompleteHeaderPreview() {
    PawKeyTheme {
        WalkCompleteHeader(
            bitmap = null
        )
    }
}