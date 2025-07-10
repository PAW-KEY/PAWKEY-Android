package com.paw.key.presentation.ui.course.walk.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun WalkRecordItem(
    recordTitle : Int,
    recordContent : String,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(recordTitle),
            color = PawKeyTheme.colors.gray500,
            style = PawKeyTheme.typography.caption12Sb2
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = recordContent,
            color = PawKeyTheme.colors.green500,
            style = PawKeyTheme.typography.head20B2
        )
    }
}

@Preview
@Composable
private fun WalkRecordItemPreview() {
    PawKeyTheme {
        WalkRecordItem(
            recordTitle = R.string.course_record_distance,
            recordContent = "10km"
        )
    }
}