package com.paw.key.presentation.ui.course.walkreview.component

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
    onImageDelete : (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val maxImages = 3
    val totalCardCount = (imageList.size).coerceAtMost(maxImages)

    LazyRow (
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(totalCardCount) { index ->
            val currentImageUri = imageList.getOrNull(index)

            WalkReviewItem(
                image = imageList.getOrNull(index),
                onClickCard = {
                    onClickCard(index, currentImageUri)
                },
                onImageDelete = {
                    onImageDelete(index)
                },
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