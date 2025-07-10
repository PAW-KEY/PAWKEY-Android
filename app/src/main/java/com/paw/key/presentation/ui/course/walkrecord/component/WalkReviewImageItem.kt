package com.paw.key.presentation.ui.course.walkrecord.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable

@Composable
fun WalkReviewItem(
    image : Uri?,
    onClickCard: () -> Unit,
    onImageDelete : (Uri?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .width(LocalConfiguration.current.screenHeightDp.dp * 0.2f)
            .height(LocalConfiguration.current.screenHeightDp.dp * 0.3f)
            .noRippleClickable {
                if (image == null) {
                    onClickCard()
                }
            }
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(if (image == null) PawKeyTheme.colors.gray50 else Color.Transparent)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        ) {
            when {
                image != null -> {
                    AsyncImage(
                        model = image,
                        contentDescription = stringResource(R.string.course_review_image_description),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                else -> {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_walk_review_add_image),
                        contentDescription = stringResource(R.string.course_review_image_description),
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(48.dp)
                    )
                }
            }

            if (image != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(color = Color.Transparent)
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_walk_review_cancel),
                        tint = Color.Unspecified,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .noRippleClickable {
                                onImageDelete(image)
                            }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun WalkReviewItemPreview() {
    PawKeyTheme {
        WalkReviewItem(
            image = null,
            onClickCard = {},
            onImageDelete = {}
        )
    }
}