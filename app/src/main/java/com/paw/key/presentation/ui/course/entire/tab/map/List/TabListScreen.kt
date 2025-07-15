package com.paw.key.presentation.ui.course.entire.tab.map.List

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paw.key.R
import com.paw.key.core.designsystem.component.CourseCard
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable
import com.paw.key.presentation.ui.course.entire.tab.map.List.viewmodel.TapListViewModel

@Preview(showBackground = true)
@Composable
private fun PreviewTabListScreen() {
    PawKeyTheme {
        TabListScreen(
            navigateToDetail = {}
        )
    }
}

@Composable
fun TapListRoute(
    navigateToDetail: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TapListViewModel = hiltViewModel(),
) {
    TabListScreen(
        modifier = modifier,
        navigateToDetail = navigateToDetail
    )
}

@Composable
fun TabListScreen(
    navigateToDetail: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TapListViewModel = hiltViewModel(),
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = PawKeyTheme.colors.white1)
                .padding(horizontal = 16.dp, vertical = 11.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_course_optin_filter),
                contentDescription = "filter",
                tint = Color.Unspecified,
                modifier = Modifier
                    .noRippleClickable {
                        showBottomSheet = true
                    }
            )
            OptionChip(
                text = "선택한 옵션이 없어요",
                isActionChip = true
            )
        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(PawKeyTheme.colors.white2)
                .padding(bottom = 36.dp)
        ) {
            // Todo : 나중에 서버용 리스트로 변경
            item {
                CourseCard(
                    title = "제목을 입력해주세요",
                    petName = "안녕꼬리",
                    date = "21/1/1",
                    isRecord = true,
                    onCLickItem = {
                        navigateToDetail()
                    }
                )
            }
            item {
                CourseCard(
                    title = "제목을 입력해주세요",
                    petName = "안녕꼬리",
                    date = "21/1/1",
                    isRecord = true,
                    onCLickItem = {}
                )
            }
            item {
                CourseCard(
                    title = "제목을 입력해주세요",
                    petName = "안녕꼬리",
                    date = "21/1/1",
                    isRecord = true,
                    onCLickItem = {}
                )
            }
            item {
                CourseCard(
                    title = "제목을 입력해주세요",
                    petName = "안녕꼬리",
                    date = "21/1/1",
                    isRecord = true,
                    onCLickItem = {}
                )
            }
        }
        if (showBottomSheet) {
            CourseOptionBottomSheet(
                viewModel = viewModel,
                onDismissRequest = { showBottomSheet = false }
            )
        }
    }
}

@Composable
private fun OptionChip(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isActionChip: Boolean = false,
) {
    Box(
        modifier = modifier
            .background(
                color = PawKeyTheme.colors.white1,
                shape = RoundedCornerShape(60.dp)
            )
            .border(
                width = 1.dp,
                color = if (isActionChip) PawKeyTheme.colors.gray200 else PawKeyTheme.colors.gray50,
                shape = RoundedCornerShape(60.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 7.dp)
    ) {
        Text(
            text = text,
            color = if (isActionChip) PawKeyTheme.colors.black else PawKeyTheme.colors.gray200,
            style = PawKeyTheme.typography.caption12R
        )
    }
}

