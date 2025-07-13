package com.paw.key.presentation.ui.course.walkcomplete.component

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
                .size(48.dp)
                .padding(end = 10.dp)
                .background(
                    color = Color.LightGray,
                    shape = CircleShape
                )
                .clip(CircleShape)
        )

        Column {
            Text(
                text = "포비",
                color = PawKeyTheme.colors.black,
                style = PawKeyTheme.typography.head20B1
            )

            Text(
                text = "2025.06.26(금) | 오후 11:50",
                color = PawKeyTheme.colors.gray300,
                style = PawKeyTheme.typography.caption12Sb1,
                modifier = Modifier
                    .padding(top = 6.dp)
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