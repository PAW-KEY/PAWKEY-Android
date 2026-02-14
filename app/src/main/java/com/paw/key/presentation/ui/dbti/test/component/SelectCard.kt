package com.paw.key.presentation.ui.dbti.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable

@Composable
fun SelectionCard(
    imageUrl: String?,
    topText: String,
    bottomText: String,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor = if (isSelected) {
        PawKeyTheme.colors.primary
    } else {
        PawKeyTheme.colors.defaultMiddle
    }

    val textColor = if (isSelected) {
        PawKeyTheme.colors.primary
    } else {
        PawKeyTheme.colors.contents
    }

    val textStyle = if (isSelected) {
        PawKeyTheme.typography.bodyActive
    } else {
        PawKeyTheme.typography.bodyDefault
    }

    Column(
        modifier = modifier
            .width(160.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 34.75.dp, vertical = 38.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 이미지
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        // 텍스트들
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = topText,
                color = textColor,
                style = textStyle,
                textAlign = TextAlign.Center
            )

            Text(
                text = bottomText,
                color = textColor,
                style = textStyle,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun SelectionCardPreview() {
    PawKeyTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Default
            SelectionCard(
                imageUrl = null,
                topText = "text",
                bottomText = "text",
                isSelected = false,
                onClick = {}
            )

            // Selected
            SelectionCard(
                imageUrl = null,
                topText = "text",
                bottomText = "text",
                isSelected = true,
                onClick = {}
            )
        }
    }
}