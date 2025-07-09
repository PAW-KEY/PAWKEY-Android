package com.paw.key.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun CourseCard(
    title: String,
    petName:String,
    date: String,
    location: String,
    distance: String,
    time: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 프로필
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray, RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(title, style = PawKeyTheme.typography.body14M,
                    color = PawKeyTheme.colors.green500
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(petName, style = PawKeyTheme.typography.caption12Sb2)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(date, style = PawKeyTheme.typography.caption12R,
                        color = PawKeyTheme.colors.gray300)

                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SubChip(text = distance)
            SubChip(text = time)
            SubChip(text = location)
        }
        Spacer(modifier = Modifier.height(12.dp))

        // 지도 썸네일
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally)
                .width(343.dp)
                .height(156.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))
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
            location = "홍대입구역",
            distance = "3km",
            time = "1시간 소요",
        )
    }
}