package com.paw.key.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable

@Composable
fun FeedbackItem(
    item : String,
    textColor: androidx.compose.ui.graphics.Color,
    borderColor: androidx.compose.ui.graphics.Color,
    onClickFeedback: (String) -> Unit
) {
    Text(
        text = item,
        style = PawKeyTheme.typography.body14R,
        color = textColor,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .noRippleClickable {
                onClickFeedback(item)
            }
    )
}