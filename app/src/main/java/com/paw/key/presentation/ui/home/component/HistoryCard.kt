package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.component.SubChip
import com.paw.key.core.designsystem.theme.PawKeyTheme


@Preview
@Composable
private fun PreviewHistoryCard() {
    PawKeyTheme {
        HistoryCard()
    }
}

@Composable
internal fun HistoryCard(

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(298.dp)
            .background(
                color = PawKeyTheme.colors.white2,
                shape = RoundedCornerShape(12.dp)
            ),
    ) {
        Column()
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                Column(

                ) {
                    HistoryTop()

                    Box(
                        modifier = Modifier
                            .height(169.dp)
                            .fillMaxWidth()
                            .background(color = PawKeyTheme.colors.black)
                            .padding(top = 114.dp, start = 16.dp)
                    ) {
                        InfoColumn(
                            location = "강남구 역삼동", date = "2025.06.26(금)", time = "23:20-23:30"
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    SubChip(text = "2.2km")

                    SubChip(text = "30분")

                    SubChip(text = "3208걸음")
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    SubChip(text = "자동차 소음이 적어요")

                    SubChip(text = "반려견 동반 카페")
                }
            }
        }
    }
}

@Composable
fun HistoryTop(

) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(41.dp)
            .background(color = PawKeyTheme.colors.green400)
            .padding(horizontal = 16.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 12.dp, topEnd = 12.dp
                )
            ),
    ) {
        Text(
            text = "최근산책",
            color = PawKeyTheme.colors.white1,
            style = PawKeyTheme.typography.body14Sb,
        )
    }
}

@Composable
fun InfoColumn(
    location: String,
    date: String,
    time: String,
) {
    Column(
        verticalArrangement = Arrangement.Center,

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),

            ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home_location),
                contentDescription = "location",
                tint = Color.Unspecified,
            )

            Text(
                text = location,
                color = PawKeyTheme.colors.white1,
                style = PawKeyTheme.typography.body14M,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home_location),
                contentDescription = "date",
                tint = Color.Unspecified
            )
            Text(
                text = date,
                color = PawKeyTheme.colors.white1,
                style = PawKeyTheme.typography.body14M,
            )
            Text(
                text = time,
                color = PawKeyTheme.colors.white1,
                style = PawKeyTheme.typography.body14M,
            )
        }
    }
}
