package com.paw.key.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
private fun PreviewSettingButton() {
    SettingButton()
}

@Composable
fun SettingButton(){
    Box(
        modifier = Modifier
            .height(64.dp)
            .width(140.dp)
            .background(color = PawKeyTheme.colors.white1
                , shape = RoundedCornerShape(8.dp)
            ),
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ){
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_setting),
                contentDescription = "setting",
            )

            Text(
                text ="내 지역 관리",
                color = PawKeyTheme.colors.black,
                style = PawKeyTheme.typography.body16Sb
            )
        }
    }
}