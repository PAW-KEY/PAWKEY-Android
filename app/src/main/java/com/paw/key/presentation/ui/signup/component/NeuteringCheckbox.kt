package com.paw.key.presentation.ui.signup.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun SignUpNeuteringCheckRadio(
    isNeutered: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable(onToggle)
    ) {
        Icon(
            imageVector = if (isNeutered)
                ImageVector.vectorResource(R.drawable.ic_roundcheck_valid)
            else
                ImageVector.vectorResource(R.drawable.ic_roundcheck_invalid),
            contentDescription = "neutering check",
            tint = Color.Unspecified
        )
        Text(
            text = "중성화 했어요",
            color = PawKeyTheme.colors.default,
            style = PawKeyTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
private fun NeuteringCheckRadioPreview() {
    PawKeyTheme {
        SignUpNeuteringCheckRadio(
            isNeutered = true,
            onToggle = {}
        )
    }
}