package com.paw.key.presentation.ui.course.walkrecord.component

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun WalkReviewImageRow (
    imageList : List<Uri?>,
    onClickCard: (Int, Uri?) -> Unit,
    onImageDelete : (Uri?) -> Unit,
    modifier: Modifier = Modifier
) {
    val maxImages = 5
    val totalCardCount = (imageList.size).coerceAtMost(maxImages)

    LazyRow (
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(totalCardCount) { index ->
            val currentImageUri = imageList.getOrNull(index)

            WalkReviewItem(
                image = imageList.getOrNull(index),
                onClickCard = {
                    onClickCard(index, currentImageUri)
                },
                onImageDelete = {
                    onImageDelete(currentImageUri)
                },
                modifier = Modifier
            )
        }

        if (imageList.size < maxImages) {
            item {
                WalkReviewItem(
                    image = null,
                    onClickCard = {
                        onClickCard(6, null)
                    },
                    onImageDelete = {

                    },
                    modifier = Modifier
                )
            }
        }
    }
}

@Preview
@Composable
private fun WalkReviewImageRowPreview() {
    val imageList = listOf<Uri?>(null, null, null, null)
    PawKeyTheme {
        WalkReviewImageRow(
            imageList = imageList,
            onClickCard = { _, _ -> },
            onImageDelete = {

            }
        )
    }
}