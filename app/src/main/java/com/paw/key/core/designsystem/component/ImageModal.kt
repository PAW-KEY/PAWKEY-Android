package com.paw.key.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import androidx.compose.ui.window.Dialog
import coil.request.ImageRequest
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun ImageModal(
    imageUrl: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .size(width = 328.dp, height = 228.dp)
                .wrapContentSize(),
            horizontalAlignment = Alignment.End
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data("https://pawkey-server.com/image.jpg") // ← 서버에서 받은 이미지 URL 넣깅
//                    .crossfade(true)
//                    .build(),
//                contentDescription = null,
//                modifier = Modifier
//            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageModalPreview() {
    PawKeyTheme {
        ImageModal(
            imageUrl = "https://pawkey-server.com/image.jpg",
            onDismiss = {}
            )
    }
}