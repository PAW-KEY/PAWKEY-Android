package com.paw.key.core.designsystem.component

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun CourseDetail(
    title: String,
    petName:String,
    date: String,
    location: String,
    distance: String,
    time: String,
    option: List<String>,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        item {
            //상단의 헤더바
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_black),
                    contentDescription = "뒤로가기"
                )
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "저장한 산책 루트",
                        style = PawKeyTheme.typography.body16Sb
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))
            }
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(Color.LightGray)
                )

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = title,
                            style = PawKeyTheme.typography.head20Sb,
                            color = PawKeyTheme.colors.green500
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_heart_filled),
                            contentDescription = "좋아요"
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        //프로필 사진
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.Gray, RoundedCornerShape(20.dp))
                        )
                        Text(
                            text = petName,
                            style = PawKeyTheme.typography.body16Sb,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Text(text = location, style = PawKeyTheme.typography.body14M,
                            color = PawKeyTheme.colors.gray400)
                        Text(text = "$date | $time", style = PawKeyTheme.typography.body14M,
                            color = PawKeyTheme.colors.gray400)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SubChip(text = distance)
                        SubChip(text = time)
                        SubChip(text = location)
                    }

                    //산책 사진
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp)
                                    .background(Color.LightGray) //어차피 사진 들어갈거임
                            )
                        }
                    }

                    Text(
                        text = "후기 글 본문 후기 글 본문 후기 글 본문",
                        style = PawKeyTheme.typography.body14R
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "본인 위치에서의 거리",
                        style = PawKeyTheme.typography.caption12Sb1,
                        color = PawKeyTheme.colors.gray200
                    )
                    Divider(modifier = Modifier.padding(10.dp))

                    Text(
                        text = "이런 점이 좋았어요",
                        style = PawKeyTheme.typography.head18Sb,
                        color = PawKeyTheme.colors.black,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    // 서버에서 전달받은 옵션이 없을 경우
                    if (option.isEmpty()) {
                        Text(
                            text = "아직은 후기가 없어요.",
                            style = PawKeyTheme.typography.body16Sb,
                            color = PawKeyTheme.colors.gray400
                        )
                    } else {
                        // 각 항목에 대해 퍼센트 시각화
                        option.forEachIndexed { index, tag ->
                            val percentage = when (index) {
                                0 -> 1f
                                1 -> 0.8f
                                2 -> 0.6f
                                else -> 1f
                            }

                            val backgroundColor = when (index) {
                                0 -> PawKeyTheme.colors.green500
                                1 -> PawKeyTheme.colors.green400
                                2 -> PawKeyTheme.colors.green300
                                else -> PawKeyTheme.colors.green500
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .height(48.dp)
                                    .background(PawKeyTheme.colors.gray100, RoundedCornerShape(6.dp)) // 회색 배경
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(percentage)
                                        .fillMaxHeight()
                                        .background(backgroundColor, RoundedCornerShape(6.dp))
                                ) {
                                    Text(
                                        text = tag,
                                        color = Color.White,
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .padding(horizontal = 16.dp),
                                        style = PawKeyTheme.typography.body16Sb
                                    )
                                }
                            }
                        }
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
            petName = "반려견 이름",
            date = "2025/05/17",
            location = "홍대입구역",
            distance = "3km",
            time = "1시간 소요",
            option = listOf("조용해요", "가로등 많아요", "산책로 깨끗해요")
        )
    }
}