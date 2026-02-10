package com.paw.key.presentation.ui.mypage.main.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable

@Composable
fun PetCard(
    name: String,
    age: String,
    gender: String,
    image: Uri?,
    onPetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = PawKeyTheme.colors.primary,
                shape = RoundedCornerShape(12.dp)
            )
            .noRippleClickable(onClick = onPetClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PawKeyTheme.colors.primary)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "반려견 프로필",
                style = PawKeyTheme.typography.body16Sb,
                color = Color.White
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = PawKeyTheme.colors.opacity5Primary,
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoChip(
                    chipText = name,
                )

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        style = PawKeyTheme.typography.subTitle,
                        color = PawKeyTheme.colors.contents
                    )

                    Text(
                        "$age · $gender",
                        style = PawKeyTheme.typography.caption12R,
                        color = PawKeyTheme.colors.gray300
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewPetCard() {
    PawKeyTheme {
        PetCard(
            name = "멍멍이",
            age = "5살",
            gender = "남아",
            image = null,
            onPetClick = {}
        )
    }
}