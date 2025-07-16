package com.paw.key.core.designsystem.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.designsystem.theme.Gray100
import com.paw.key.domain.model.entity.walklist.CategoryTop3Entity

@Composable
fun CourseDetail(
    title : String,
    petName : String,
    date : String,
    location : String,
    isLike : Boolean,
    content : String,
    petProfileImage : String,
    routeMapImageUrl : String,
    categorySummary : List<String>,
    categoryTop3 : List<CategoryTop3Entity>,
    totalReviewCount : Int,
    walkingImageUrls : List<String>,

    onImageClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isLiked = remember { mutableStateOf(false) }
    LaunchedEffect(routeMapImageUrl) {
        Log.d("LaunchedEffect", "LaunchedEffect: $routeMapImageUrl")
        Log.d("LaunchedEffect", "LaunchedEffect: $walkingImageUrls")
        Log.d("LaunchedEffect", "LaunchedEffect: $petProfileImage")
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(routeMapImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(244.dp)
                .background(color = Gray100)
                .background(Color.Gray)
        )

        Column(
            modifier = Modifier
                .background(
                    color = PawKeyTheme.colors.white1,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(horizontal = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            ) {
                Text(
                    text = title,
                    style = PawKeyTheme.typography.head20Sb,
                    color = PawKeyTheme.colors.black
                )

                Icon(
                    imageVector = if (isLiked.value) {
                        ImageVector.vectorResource(id = R.drawable.ic_eye_linear_gray_valid)
                    } else {
                        ImageVector.vectorResource(id = R.drawable.ic_eye_linear_gray_invalid)
                    },
                    contentDescription = "좋아요",
                    tint = Color.Unspecified,
                    modifier = Modifier.clickable {
                        isLiked.value = !isLiked.value
                    }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(petProfileImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                )

                Text(
                    text = petName,
                    style = PawKeyTheme.typography.body16Sb,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_walk_review_location),
                        contentDescription = "장소",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = location,
                        style = PawKeyTheme.typography.body14M,
                        color = PawKeyTheme.colors.gray400
                    )
                }
                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_walk_review_time),
                        contentDescription = "시간",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = date,
                        style = PawKeyTheme.typography.body14M,
                        color = PawKeyTheme.colors.gray400
                    )
                }
            }


            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(vertical = 13.dp)
            ) {
                categorySummary.forEach {
                    SubChip(
                        text = it
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(walkingImageUrls.size) { index ->
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(walkingImageUrls[index])
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onImageClick(walkingImageUrls[index]) },
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Text(
                text = content,
                style = PawKeyTheme.typography.body14R
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "본인 위치에서의 거리",
                style = PawKeyTheme.typography.caption12Sb1,
                color = PawKeyTheme.colors.gray200
            )
            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .zIndex(1F)
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "이런 점이 좋았어요",
                    style = PawKeyTheme.typography.head18Sb,
                    color = PawKeyTheme.colors.black,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                    contentDescription = "편집",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = totalReviewCount.toString(),
                    style = PawKeyTheme.typography.caption12M,
                    color = PawKeyTheme.colors.gray200,
                )
            }

            if (categoryTop3.isEmpty()) {
                Text(
                    text = "아직은 후기가 없어요.",
                    style = PawKeyTheme.typography.body16Sb,
                    color = PawKeyTheme.colors.gray400,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                categoryTop3.forEach { tag ->
                    val fillRatio = (tag.percentage.coerceIn(0, 100)) / 100f
                    val backgroundColor = when (tag.rank) {
                        1 -> PawKeyTheme.colors.green300
                        2 -> PawKeyTheme.colors.green200
                        3 -> PawKeyTheme.colors.green100
                        else -> PawKeyTheme.colors.green500
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .height(37.dp)
                            .background(PawKeyTheme.colors.white2, RoundedCornerShape(6.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(fillRatio)
                                .fillMaxHeight()
                                .background(backgroundColor, RoundedCornerShape(6.dp))
                        )

                        Text(
                            text = tag.optionText,
                            color = PawKeyTheme.colors.black,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(horizontal = 16.dp)
                                .zIndex(1f),
                            style = PawKeyTheme.typography.caption12Sb2,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Visible
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CourseDetailPreview() {
    PawKeyTheme {
        CourseDetail(
            title = "홍대 주변 좋은 산책 코스",
            petName = "핑구",
            date = "2025/06/30",
            location = "홍대입구역",
            isLike = true,
            content = "산책로가 깨끗하고 벚꽃이 예뻐요!",
            petProfileImage = "https://pawkey-server.com/image/profile.png",
            routeMapImageUrl = "https://pawkey-server.com/image/map.png",
            categoryTop3 = listOf(
                CategoryTop3Entity(
                    rank = 1,
                    optionText = "산책로가 어쩌구 저꾸",
                    percentage = 42,
                    categoryName = "산책로가 어쩌구 저꾸",
                    categoryOptionId = 1,
                    categoryId = 2
                ),
                CategoryTop3Entity(
                    rank = 2,
                    optionText = "산책로가 어쩌구 저꾸",
                    percentage = 37,
                    categoryName = "산책로가 어쩌구 저꾸",
                    categoryOptionId = 1,
                    categoryId = 2
                ),
                CategoryTop3Entity(
                    rank = 3,
                    optionText = "산책로가 어쩌구 저꾸",
                    percentage = 35,
                    categoryName = "산책로가 어쩌구 저꾸",
                    categoryOptionId = 1,
                    categoryId = 2
                )
            ),
            totalReviewCount = 42,
            walkingImageUrls = listOf(
                "https://pawkey-server.com/image/walk1.jpg",
                "https://pawkey-server.com/image/walk2.jpg"
            ),
            categorySummary = listOf("안전", "편리성"),
            onImageClick = {},
        )
    }
}
