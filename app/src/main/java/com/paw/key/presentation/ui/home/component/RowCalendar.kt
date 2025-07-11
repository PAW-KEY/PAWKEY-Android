package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Preview(showBackground = true)
@Composable
private fun PreviewRowCalendar() {
    PawKeyTheme {
        Column(

        ) {
            RowCalendar(
                date = "7월"
            )

            CalendarItem(
                date = "12",
                day = "월",
                state = true
            )

            CalendarItem(
                date = "12",
                day = "월",
                state = false
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
            .border(
                width = 1.dp,
                color = PawKeyTheme.colors.green500,
                shape = RoundedCornerShape(15.dp),
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 17.dp),
        ) {
            Text(
                text = "7월",
                color = PawKeyTheme.colors.black,
                style = PawKeyTheme.typography.head18Sb,
            )

            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1F)
                    .height(74.dp),
            ) {
                items(count = 30) { item ->
                    CalendarItem(
                        date = "12",
                        day = "월",
                        state = true
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
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .width(42.dp)
            .height(74.dp),
    ) {
        Box(
            modifier = Modifier
                .width(42.dp)
                .height(58.dp)
                .background(
                    shape = (RoundedCornerShape(8.dp)),
                    color = if (state) {
                        PawKeyTheme.colors.system_green
                    } else {
                        PawKeyTheme.colors.white1
                    }
                ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
            ) {
                Text(
                    text = date,
                    color =
                        if (state) {
                            PawKeyTheme.colors.white1
                        } else {
                            PawKeyTheme.colors.gray900.copy(alpha = 0.6F)
                        },
                    style = PawKeyTheme.typography.body14R,
                )

                Text(
                    text = day,
                    color =
                        if (state) {
                            PawKeyTheme.colors.white1
                        } else {
                            PawKeyTheme.colors.gray900.copy(alpha = 0.6F)
                        },
                    style = PawKeyTheme.typography.body14R,
                )
            }
        }

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_calendar_dot),
            contentDescription = "state",
            modifier = Modifier
                .padding(start = 1.dp, top = 5.dp),
            tint = if (state) {
                PawKeyTheme.colors.system_green
            } else {
                PawKeyTheme.colors.white1
            },
        )
    }
}

