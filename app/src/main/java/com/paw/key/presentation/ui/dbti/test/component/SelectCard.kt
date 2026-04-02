package com.paw.key.presentation.ui.dbti.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun SelectCard(
    text: String,
    imageUrl: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    val backgroundColor = if (isSelected) {
        PawKeyTheme.colors.opacity5Primary
    } else {
        Color.Transparent
    }

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
            .aspectRatio(8f / 11f)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
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

        Text(
            text = text,
            color = textColor,
            style = textStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun SelectionCardPreview() {
    PawKeyTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SelectCard(
                text = "잠깐 눈치만 보고\n거리를 유지해요",
                imageUrl = null,
                onClick = {},
                modifier = Modifier.weight(1f),
                isSelected = false
            )

            SelectCard(
                text = "먼저 다가가서\n인사하고 놀자고 해요",
                imageUrl = null,
                onClick = {},
                modifier = Modifier.weight(1f),
                isSelected = true
            )
        }
    }
}