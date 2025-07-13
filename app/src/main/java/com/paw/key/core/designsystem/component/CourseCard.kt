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
import com.paw.key.R
import androidx.compose.foundation.shape.RoundedCornerShape
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
import kotlin.String

@Composable
fun CourseCard(
    title: String,
    petName:String,
    date: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .size(width = 328.dp , height = 240.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
    ) {
        // 지도 썸네일
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(343f / 172f)
                .clip(RoundedCornerShape(20.dp))
        ) {
            // 지도 이미지
            Image(
                painter = painterResource(id = R.drawable.dummy_map),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // 하단 그라데이션 오버레이
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp * 0.6f) // 높이 조절 가능
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                PawKeyTheme.colors.black.copy(0.05f),
                                PawKeyTheme.colors.black.copy(0.55f)
                            )
                        )
                    )
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
                Spacer(modifier = Modifier.weight(1f)) // 아이콘을 Row 끝으로 밀기 위한 Spacer

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
        }

        Spacer(modifier = Modifier.height(12.dp))

        
        ChipRow(tags = listOf(
            "이륜차 거의 없음",
            "배변 쓰레기통",
            "쉼터",
            "CCTV 있음",
            "물그릇 비치","이륜차 거의 없음",
            "배변 쓰레기통",
            "쉼터",
        ))

        Spacer(modifier = Modifier.height(12.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun CourseCardPreview() {
    PawKeyTheme {
        CourseCard(
            title = "홍대 주변 좋은 산책 코스",
            petName = "반려견 이름",
            date = "2025/05/17",
        )
    }
}