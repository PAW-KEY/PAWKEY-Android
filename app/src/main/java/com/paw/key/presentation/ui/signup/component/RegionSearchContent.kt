package com.paw.key.presentation.ui.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable

private val dummySeoulRegions = listOf(
    Pair(
        "강남구", listOf(
            "개포동", "논현동", "대치동", "도곡동", "삼성동", "세곡동", "수서동",
            "신사동", "압구정동", "역삼동", "율현동", "일원동", "자곡동", "청담동"
        )
    ),
    Pair(
        "구로구", listOf(
            "가리봉동", "개봉동", "고척동", "구로동", "궁동", "신도림동", "오류동",
            "온수동", "천왕동", "항동"
        )
    ),
    Pair(
        "금천구", listOf(
            "가산동", "독산동", "시흥동"
        )
    ),
    Pair(
        "노원구", listOf(
            "공릉동", "상계동", "월계동", "중계동", "하계동"
        )
    ),
    Pair(
        "도봉구", listOf(
            "도봉동", "방학동", "쌍문동", "창동"
        )
    ),
    Pair(
        "동대문구", listOf(
            "답십리동", "신설동", "용두동", "이문동", "장안동", "전농동", "제기동",
            "청량리동", "회기동", "휘경동"
        )
    ),
    Pair(
        "동작구", listOf(
            "노량진동", "대방동", "동작동", "본동", "사당동", "상도1동", "상도동",
            "신대방동", "흑석동"
        )
    )
)

@Composable
fun RegionSearchContent(
    selectedGu: String,
    selectedDong: String,
    onRegionSelected: (gu: String, dong: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var searchQuery by remember { mutableStateOf("") }

    var selectedGu by remember {
        mutableStateOf(selectedGu)
    }

    var selectedDong by remember {
        mutableStateOf(selectedDong)
    }

    val filteredRegions = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            dummySeoulRegions
        } else {
            dummySeoulRegions.mapNotNull { (gu, dongList) ->
                val matchingDongs = dongList.filter { dong ->
                    "$gu $dong".contains(searchQuery, ignoreCase = true)
                }

                if (matchingDongs.isNotEmpty()) {
                    Pair(gu, matchingDongs)
                } else {
                    null
                }
            }
        }
    }

    val dongListForSelectedGu = remember(selectedGu, filteredRegions) {
        filteredRegions.find { it.first == selectedGu }?.second ?: emptyList()
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .background(
                color = PawKeyTheme.colors.background,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "산책 지역",
            style = PawKeyTheme.typography.subTitle,
            color = PawKeyTheme.colors.contents,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 24.dp,
                    bottom = 24.dp
                ),
            textAlign = TextAlign.Center
        )

        SignUpTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
            },
            placeholder = "지역을 검색해보세요",
            suffix = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_signup_search),
                    contentDescription = "region search",
                    tint = PawKeyTheme.colors.contents
                )
            }
        )

        RegionSearchList(
            guList = filteredRegions.map { it.first },
            dongList = dongListForSelectedGu,
            selectedGu = selectedGu,
            selectedDong = selectedDong,
            onGuSelected = { gu ->
                selectedGu = gu
                selectedDong = ""
            },
            onDongSelected = { dong ->
                selectedDong = dong
                onRegionSelected(selectedGu, selectedDong)
            },
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
private fun RegionSearchList(
    guList: List<String>,
    dongList: List<String>,
    selectedGu: String,
    selectedDong: String,
    onGuSelected: (String) -> Unit,
    onDongSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(0.45f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(guList) { gu ->
                RegionItem(
                    name = gu,
                    isSelected = gu == selectedGu,
                    onClick = {
                        onGuSelected(gu)
                    }
                )
            }
        }

        VerticalDivider(
            thickness = 1.dp,
            color = PawKeyTheme.colors.defaultButton,
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    color = PawKeyTheme.colors.defaultButton,
                    shape = RoundedCornerShape(8.dp)
                )
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            items(dongList) { dong ->
                RegionItem(
                    name = dong,
                    isSelected = dong == selectedDong,
                    onClick = {
                        onDongSelected(dong)
                    },
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Composable
private fun RegionItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center
) {
    val textColor = if (isSelected) PawKeyTheme.colors.background else PawKeyTheme.colors.contents

    val backgroundColor =
        if (isSelected) PawKeyTheme.colors.primary else PawKeyTheme.colors.background

    Text(
        text = name,
        style = PawKeyTheme.typography.bodyActive,
        color = textColor,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 11.dp
            )
            .noRippleClickable {
                onClick()
            },
        textAlign = textAlign
    )
}


@Preview
@Composable
private fun RegionSearchContentPreview() {
    PawKeyTheme {
        RegionSearchContent(
            selectedGu = "",
            selectedDong = "",
            onRegionSelected = { _, _ -> }
        )

    }
}