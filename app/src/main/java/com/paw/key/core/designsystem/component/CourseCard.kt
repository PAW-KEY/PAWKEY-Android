package com.paw.key.core.designsystem.component

import android.util.Log
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
import com.paw.key.R
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
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable
import kotlin.String

@Composable
fun CourseCard(
    title: String,
    petName:String,
    date: String,
    onCLickItem : () -> Unit,
    modifier: Modifier = Modifier,
    isShared : Boolean = false, // true면 떠진거 false면 닫은거
    isRecord : Boolean = false // 기록한 아이템 - true면 하트, false면 공유 아이콘
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .size(width = 328.dp , height = 240.dp)
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
            // 지도 이미지 Todo : 테스트용
            Image(
                painter = painterResource(id = R.drawable.dummy_map),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            /*AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://pawkey-server.com/image.jpg") // ← 서버에서 받은 이미지 URL 넣깅
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )*/

            // 하단 그라데이션 오버레이
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp * 0.6f) // 높이 조절 가능
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
                    .padding(start = 16.dp, end = 16.dp,bottom = 16.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://pawkey-server.com/image.jpg") // ← 서버에서 받은 이미지 URL 넣깅
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape), // 원형 크롭
                    contentScale = ContentScale.Crop
                )
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
                            text = date,
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

        
        ChipRow(
            tags = listOf(
            "이륜차 거의 없음",
            "배변 쓰레기통",
            "쉼터",
            "CCTV 있음",
            "물그릇 비치","이륜차 거의 없음",
            "배변 쓰레기통",
            "쉼터",),
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