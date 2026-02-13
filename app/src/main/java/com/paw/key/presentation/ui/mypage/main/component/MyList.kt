package com.paw.key.presentation.ui.mypage.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.mypage.main.model.MyListState

@Composable
fun MyList(
    listTitle: String,
    listContent: MyListState,
    onListClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PawKeyTheme.colors.background,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = listTitle,
            style = PawKeyTheme.typography.buttonSmall,
            color = PawKeyTheme.colors.contents
        )

        listContent.myList.forEachIndexed { index, item ->
            MyListItem(
                title = item.title,
                iconRes = item.iconRes,
                onListClick = { onListClick(index) },
            )
        }
    }
}

@Composable
fun SettingList(
    listTitle: String,
    listContent: MyListState,
    onListClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PawKeyTheme.colors.background,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = listTitle,
            style = PawKeyTheme.typography.buttonSmall,
            color = PawKeyTheme.colors.contents
        )

        listContent.settingList.forEachIndexed { index, item ->
            MyListItem(
                title = item.title,
                iconRes = item.iconRes,
                onListClick = { onListClick(index) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewMyList() {
    PawKeyTheme {
        MyList(
            listTitle = "산책 루트 관리",
            listContent = MyListState(),
            onListClick = {}
        )
    }
}