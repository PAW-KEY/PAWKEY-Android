package com.paw.key.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    title: String,
    petName: String,
    date: String,
    representativeImageUrl: String? = null, // 추가
    petProfileImageUrl: String? = null,     // 추가
    descriptionTags: List<String> = emptyList(), // 추가
    onCLickItem: () -> Unit,
    modifier: Modifier = Modifier,
    isShared: Boolean = false,
    isRecord: Boolean = false
) {
    // 날짜 포맷 변환 함수
    fun formatDate(dateString: String): String {
        return try {
            // "2025-07-15T21:27:03.54498" -> "2025/07/15"
            val datePart = dateString.split("T")[0] // "2025-07-15"
            datePart.replace("-", "/") // "2025/07/15"
        } catch (e: Exception) {
            dateString // 실패하면 원본 반환
        }
    }

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .size(width = 328.dp, height = 240.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .noRippleClickable {
                onCLickItem()
            }
    ) {
        // 지도 썸네일
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(343f / 172f)
                .clip(RoundedCornerShape(10.dp))
        ) {
            // 서버 이미지 또는 기본 이미지
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
                // 기본 이미지
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

            // 하단 그라데이션 오버레이
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

            // 프로필 + 제목
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                // 반려견 프로필 이미지
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
                } else {
                    // 기본 프로필 이미지
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(PawKeyTheme.colors.gray200),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_heart_default),
                            contentDescription = null,
                            tint = PawKeyTheme.colors.gray400,
                            modifier = Modifier.size(20.dp)
                        )
                    }
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
                            text = formatDate(date), // 포맷된 날짜 사용
                            style = PawKeyTheme.typography.caption12R,
                            color = PawKeyTheme.colors.gray100
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))

                when {
                    isShared -> {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_eye_linear_valid),
                            contentDescription = "공유됨",
                            tint = PawKeyTheme.colors.gray200,
                        )
                    }
                    isRecord -> {
                        val isLiked = remember { mutableStateOf(false) }

                        Icon(
                            imageVector = if (isLiked.value) {
                                ImageVector.vectorResource(id = R.drawable.ic_heart_filled)
                            } else {
                                ImageVector.vectorResource(id = R.drawable.ic_heart_default)
                            },
                            contentDescription = "좋아요",
                            tint = Color.Unspecified,
                            modifier = Modifier.clickable {
                                isLiked.value = !isLiked.value
                            }
                        )
                    }
                    else -> {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_eye_linear_invalid),
                            contentDescription = "공유 안됨",
                            tint = PawKeyTheme.colors.gray200,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 서버에서 받은 태그들 사용
        ChipRow(
            tags = descriptionTags,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))
    }

    HorizontalDivider(
        color = PawKeyTheme.colors.gray50,
        thickness = 1.dp,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
}
@Preview(showBackground = true)
@Composable
fun CourseCardPreview() {
    PawKeyTheme {
        CourseCard(
            title = "홍대 주변 좋은 산책 코스",
            petName = "반려견 이름",
            date = "2025/05/17",
            onCLickItem = {}
        )
    }
}