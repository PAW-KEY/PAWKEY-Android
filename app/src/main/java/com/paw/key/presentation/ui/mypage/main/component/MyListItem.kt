package com.paw.key.presentation.ui.mypage.main.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.paw.key.core.extension.noRippleClickable

@Composable
fun MyListItem(
    title: String,
    @DrawableRes iconRes: Int,
    onListClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = PawKeyTheme.colors.background)
            .noRippleClickable(onClick = onListClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = "$title Icon",
            tint = Color.Unspecified
        )

        Text(
            text = title,
            style = PawKeyTheme.typography.bodyDefault,
            color = PawKeyTheme.colors.contents
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_mypage_arrow_right),
            contentDescription = "Arrow Right",
            tint = Color.Unspecified
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun MyListItemPreview() {
    PawKeyTheme {
        MyListItem(
            title = "내가 기록한 산책",
            onListClick = {},
            iconRes = R.drawable.ic_mypage_edit,
            modifier = Modifier.fillMaxWidth()
        )
    }
}