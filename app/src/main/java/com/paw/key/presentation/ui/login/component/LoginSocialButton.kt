package com.paw.key.presentation.ui.login.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable

@Preview(showBackground = true)
@Composable
private fun PreviewLoginSocialButton() {
    PawKeyTheme {
        LoginSocialButton(
            logo = R.drawable.ic_login_kakao,
            loginText = "Login with Google",
            onClick = {}
        )
    }
}

@Composable
fun LoginSocialButton(
    logo: Int,
    loginText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(12.dp)

            )
            .noRippleClickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = logo),
            contentDescription = stringResource(id = R.string.ic_login_button_content),
            tint = Color.Unspecified,
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
        )

        Text(
            text = loginText,
            color = PawKeyTheme.colors.contents,
            style = PawKeyTheme.typography.body14Sb,
            modifier = Modifier
                .align(alignment = Alignment.Center)
        )
    }
}