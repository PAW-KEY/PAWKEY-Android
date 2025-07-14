package com.paw.key.presentation.ui.home.component

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Preview(showBackground = true)
@Composable
private fun PreviewRowCalendar() {
    PawKeyTheme {
        Column {
            RowCalendar(date = "7월")

            CalendarItem(
                date = "17",
                day = "목",
                state = true
            )

            CalendarItem(
                date = "20",
                day = "일",
                state = false,
                isToday = true
            )
        }
    }
}

@Composable
fun RowCalendar(
    date: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(103.dp)
            .background(
                color = PawKeyTheme.colors.white1,
                shape = RoundedCornerShape(size = 16.dp),
            )
            .clip(RoundedCornerShape(size = 16.dp)),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 17.dp),
        ) {
            Text(
                text = date,
                color = PawKeyTheme.colors.black,
                style = PawKeyTheme.typography.head18Sb,
                modifier = Modifier
                    .padding(bottom = 28.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                items(7) { index ->
                    val dates = listOf("14", "15", "16", "17", "18", "19", "20")
                    val days = listOf("월", "화", "수", "목", "금", "토", "일")
                    val hasActivity = listOf(true, true, false, true, false, false, false)
                    val selectedIndex = 3
                    val todayIndex = 6

                    CalendarItem(
                        date = dates[index],
                        day = days[index],
                        state = hasActivity[index],
                        isSelected = index == selectedIndex,
                        isToday = index == todayIndex,
                        isAfterSelected = index > selectedIndex && index < todayIndex
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarItem(
    date: String,
    day: String,
    modifier: Modifier = Modifier,
    state: Boolean = false,
    isSelected: Boolean = false,
    isToday: Boolean = false,
    isAfterSelected: Boolean = false,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .width(38.dp)
            .height(74.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(38.dp)
                .height(58.dp)
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = when {
                        isSelected -> PawKeyTheme.colors.system_green
                        else -> Color.Transparent
                    }
                ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = date,
                    color = when {
                        isSelected -> PawKeyTheme.colors.white1
                        isToday -> Color.Red
                        isAfterSelected -> PawKeyTheme.colors.black
                        else -> PawKeyTheme.colors.gray900.copy(alpha = 0.6f)
                    },
                    style = PawKeyTheme.typography.body14R,
                )

                Text(
                    text = day,
                    color = when {
                        isSelected -> PawKeyTheme.colors.white1
                        isToday -> Color.Red
                        isAfterSelected -> PawKeyTheme.colors.black
                        else -> PawKeyTheme.colors.gray900.copy(alpha = 0.6f)
                    },
                    style = PawKeyTheme.typography.body14R,
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .size(6.dp)
                .background(
                    color = if (state) PawKeyTheme.colors.system_green else Color.Transparent,
                    shape = RoundedCornerShape(50)
                )
        )
    }
}