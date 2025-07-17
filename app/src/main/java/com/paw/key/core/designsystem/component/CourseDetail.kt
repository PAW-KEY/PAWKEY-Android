package com.paw.key.core.designsystem.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
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
import com.paw.key.core.util.noRippleClickable
import com.paw.key.domain.model.entity.walklist.CategoryTop3Entity
import kotlinx.serialization.json.JsonNull.content

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CourseDetail(
    title : String,
    petName : String,
    date : String,
    Icon : Int,
    location : String,
    onClickLike: (Boolean) -> Unit,
    petProfileImage : String,
    routeMapImageUrl : String,
    categorySummary : List<String>,
    categoryTop3 : List<CategoryTop3Entity>,
    totalReviewCount : Int,
    walkingImageUrls : List<String>,
    content: String,
    onImageClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isLiked = remember { mutableStateOf(false) }

    LaunchedEffect(routeMapImageUrl) {
        Log.d("LaunchedEffect", "routeMapImageUrl: $routeMapImageUrl")
        Log.d("LaunchedEffect", "walkingImageUrls: $walkingImageUrls")
        Log.d("LaunchedEffect", "petProfileImage: $petProfileImage")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PawKeyTheme.colors.white1)
    ) {
        // 이미지 영역을 별도 Box로 분리
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(244.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(routeMapImageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Gray100)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(
                        color = PawKeyTheme.colors.white1,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .align(Alignment.BottomCenter)
            ){

            }
        }

        // 컨텐츠 영역
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = PawKeyTheme.colors.white1,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(horizontal = 16.dp)
        ) {
            // 제목과 좋아요 버튼
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
                    imageVector = ImageVector.vectorResource(id = Icon),
                    contentDescription = "좋아요",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(24.dp)
                        .noRippleClickable {
                            isLiked.value = !isLiked.value
                            onClickLike(isLiked.value)
                        }
                )
            }

            // 펫 정보
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
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = petName,
                    style = PawKeyTheme.typography.body16Sb,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // 위치와 날짜 정보
            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
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

            // 카테고리 칩들
            FlowRow (
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 13.dp),
                maxItemsInEachRow = 3,
            ) {
                categorySummary.forEach { category ->
                    SubChip(text = category)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 산책 이미지들
            if (walkingImageUrls.isNotEmpty()) {
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
            }

            // 컨텐츠
            Text(
                text = content,
                style = PawKeyTheme.typography.body14R,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            Text(
                text = "본인 위치에서의 거리",
                style = PawKeyTheme.typography.caption12Sb1,
                color = PawKeyTheme.colors.gray200,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .border(
                        width = 8.dp,
                        color = PawKeyTheme.colors.gray100,
                    )
            )

            // 리뷰 섹션
            ReviewSection(
                categoryTop3 = categoryTop3,
                totalReviewCount = totalReviewCount
            )
        }
    }
}

@Composable
private fun ReviewSection(
    categoryTop3: List<CategoryTop3Entity>,
    totalReviewCount: Int
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                modifier = Modifier.align(Alignment.CenterHorizontally)
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


@Preview(showBackground = true)
@Composable
fun CourseDetailPreview() {
    PawKeyTheme {
        CourseDetail(
            title = "홍대 주변 좋은 산책 코스",
            petName = "핑구",
            date = "2025/06/30",
            Icon = R.drawable.ic_eye_linear_gray_valid,
            location = "홍대입구역",
            onClickLike = {},
            petProfileImage = "https://pawkey-server.com/image/profile.png",
            routeMapImageUrl = "https://pawkey-server.com/image/map.png",
            categoryTop3 = listOf(
                CategoryTop3Entity(rank = 1, optionText = "산책로가 어쩌구 저꾸", percentage = 42, categoryName = "", categoryOptionId = 1, categoryId = 2),
                CategoryTop3Entity(rank = 2, optionText = "풍경이 예뻐요", percentage = 37, categoryName = "", categoryOptionId = 1, categoryId = 2),
                CategoryTop3Entity(rank = 3, optionText = "깨끗해요", percentage = 35, categoryName = "", categoryOptionId = 1, categoryId = 2)
            ),
            totalReviewCount = 42,
            walkingImageUrls = listOf(
                "https://pawkey-server.com/image/walk1.jpg",
                "https://pawkey-server.com/image/walk2.jpg"
            ),
            categorySummary = listOf("안전", "편리성"),
            content = "산책로가 깨끗하고 벚꽃이 예뻐요!",
            onImageClick = {}
        )
    }
}
