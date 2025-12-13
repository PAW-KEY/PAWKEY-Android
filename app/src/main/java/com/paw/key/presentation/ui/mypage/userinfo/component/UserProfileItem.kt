package com.paw.key.presentation.ui.mypage.userinfo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun UserProfileItem(
    label: String,
    profileItem: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = PawKeyTheme.colors.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = PawKeyTheme.typography.bodyActive,
            color = PawKeyTheme.colors.contents,
        )

        profileItem()
    }
}

@Preview(showBackground = true)
@Composable
private fun UserProfileItemPreview() {
    PawKeyTheme {
        UserProfileItem(
            label = "Name",
            profileItem = {}
        )
    }
}