package com.paw.key.presentation.ui.course.walkrecord.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun WalkReviewInfoHolder(
    icon : Int,
    content : String,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = stringResource(R.string.course_review_location_icon_description),
            tint = PawKeyTheme.colors.green500,
            modifier = Modifier
                .padding(end = 8.dp)
        )

        Text(
            text = content,
            color = PawKeyTheme.colors.gray400,
            style = PawKeyTheme.typography.body14M,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Preview
@Composable
private fun WalkReviewInfoHolderPreview() {
    PawKeyTheme {
        WalkReviewInfoHolder(
            icon = R.drawable.ic_walk_review_add_image,
            content = "서울시 강남구 역삼동"
        )
    }
}