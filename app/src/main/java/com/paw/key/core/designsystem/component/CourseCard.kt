package com.paw.key.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable

@Composable
fun CourseCard(
    postId: Int,
    title: String,
    createdAt: String,
    isLiked: Boolean? = null,   // 저장한 루트에서만 사용
    isPublic: Boolean? = null, // 기록한 루트에서만 사용
    onClickItem: () -> Unit,
    onClickLike: ((Boolean) -> Unit)? = null, // null이면 클릭 불가
    petName: String,
    modifier: Modifier = Modifier,
    representativeImageUrl: String? = null,
    petProfileImageUrl: String? = null,
    descriptionTags: List<String> = emptyList()
) {
    fun formatDate(dateString: String): String {
        return try {
            val datePart = dateString.split("T")[0]
            datePart.replace("-", "/")
        } catch (e: Exception) {
            dateString
        }
    }

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .size(width = 328.dp, height = 240.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .noRippleClickable { onClickItem() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(343f / 172f)
                .clip(RoundedCornerShape(10.dp))
        ) {
            if (representativeImageUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(representativeImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.dummy_map),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp * 0.6f)
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                PawKeyTheme.colors.black.copy(0.05f),
                                PawKeyTheme.colors.black.copy(0.55f)
                            )
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                if (petProfileImageUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(petProfileImageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = title,
                        style = PawKeyTheme.typography.body14M,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        Text(
                            text = petName,
                            style = PawKeyTheme.typography.caption12Sb2,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = formatDate(createdAt),
                            style = PawKeyTheme.typography.caption12R,
                            color = PawKeyTheme.colors.gray100
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                when {
                    isLiked != null -> {
                        Icon(
                            imageVector = if (isLiked)
                                ImageVector.vectorResource(id = R.drawable.ic_heart_filled)
                            else
                                ImageVector.vectorResource(id = R.drawable.ic_heart_default),
                            contentDescription = "좋아요",
                            tint = Color.Unspecified,
                            modifier = Modifier.clickable {
                                onClickLike?.invoke(!isLiked)
                            }
                        )
                    }

                    isPublic != null -> {
                        Icon(
                            imageVector = if (isPublic)
                                ImageVector.vectorResource(id = R.drawable.ic_eye_filled_valid)
                            else
                                ImageVector.vectorResource(id = R.drawable.ic_eye_filled_invalid),
                            contentDescription = "공개여부",
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ChipRow(
            tags = descriptionTags,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalDivider(
            color = PawKeyTheme.colors.gray50,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CourseCardPreview() {
    PawKeyTheme {
        CourseCard(
            postId = 1,
            title = "홍대 주변 좋은 산책 코스",
            createdAt = "2025-07-16T21:27:03.54498",
            isLiked = true,
            onClickItem = {},
            onClickLike = {},
            petName = "후추",
            representativeImageUrl = "https://pawkey-server.com/image.jpg",
            petProfileImageUrl = "https://pawkey-server.com/profile.jpg",
            descriptionTags = listOf("이륜차 거의 없음", "물그릇 비치", "쉴 곳 있음")
        )
    }
}