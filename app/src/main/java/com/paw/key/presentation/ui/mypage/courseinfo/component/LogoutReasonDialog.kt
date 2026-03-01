package com.paw.key.presentation.ui.mypage.courseinfo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun LogoutReasonDialog(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PawKeyTheme.colors.white1,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ){
    }

}

@Composable
private fun ReasonDialogContent(
    dialogText: String,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    val textColor = when (isSelected) {
        true -> PawKeyTheme.colors.defaultDark
        false -> PawKeyTheme.colors.contents
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PawKeyTheme.colors.white1
            )
            .padding(vertical = 11.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = false,
            onClick = onSelected,
            colors = RadioButtonDefaults.colors
                (
                selectedColor = PawKeyTheme.colors.primary,
                unselectedColor = PawKeyTheme.colors.defaultButton
            )
        )

        Text(
            text = dialogText,
            color = textColor,
            style = if (isSelected) PawKeyTheme.typography.bodyActive else PawKeyTheme.typography.bodyDefault
        )
    }
}

@Preview
@Composable
private fun ReviewLogoutReasonDialog(){
    PawKeyTheme {
        ReasonDialogContent(
            dialogText = "요즘 산책을 잘 안해요",
            onSelected = {},
        )
    }
}