package com.paw.key.presentation.ui.mypage.petinfo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable


@Composable
fun PetInfoCard(
    petName: String,
    petType: String,
    petImage: String,
    onPetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PawKeyTheme.colors.background,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(petImage)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(

            ) {
                Text(
                    text = petName,
                    style = PawKeyTheme.typography.subTitle,
                    color = PawKeyTheme.colors.contents
                )

                Text(
                    text = petType,
                    style = PawKeyTheme.typography.subButtonDefault,
                    color = PawKeyTheme.colors.defaultMiddle
                )
            }

        }

        PetInfoButton(
            onPetClick = onPetClick,
        )
    }
}

@Composable
private fun PetInfoButton(
    onPetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PawKeyTheme.colors.defaultButton,
                shape = RoundedCornerShape(4.dp)
            )
            .noRippleClickable(onPetClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "프로필 수정",
            style = PawKeyTheme.typography.subButtonActive,
            color = PawKeyTheme.colors.contents
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewPetInfoCard() {
    PawKeyTheme {
        PetInfoCard(
            petName = "멍멍이",
            petType = "강아지",
            petImage = "https://example.com/dog.jpg",
            modifier = Modifier.fillMaxWidth(),
            onPetClick = {}
        )
    }
}
