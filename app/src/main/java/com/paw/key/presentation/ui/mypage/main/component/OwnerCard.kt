package com.paw.key.presentation.ui.mypage.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun OwnerCard(
    ownerName: String,
    ownerEmail: String,
    navigateUserProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PawKeyTheme.colors.background,
                shape = RoundedCornerShape(16.dp)
            )
            .noRippleClickable(
                onClick = navigateUserProfile
            )
            .padding(
                horizontal = 16.dp, vertical = 20.dp
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = ownerName,
                style = PawKeyTheme.typography.mainButtonActive,
                color = PawKeyTheme.colors.contents
            )

            Text(
                text = ownerEmail,
                style = PawKeyTheme.typography.subButtonDefault,
                color = PawKeyTheme.colors.defaultMiddle
            )
        }

        Spacer(modifier = Modifier.weight(1F))

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_mypage_arrow_right),
            contentDescription = "owner profile",
            tint = Color.Unspecified
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewOwnerCard() {
    PawKeyTheme {
        OwnerCard(
            ownerName = "키큰오팔전차 님",
            ownerEmail = "hell@gmail.com",
            navigateUserProfile = {}
        )
    }
}