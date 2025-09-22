package com.paw.key.presentation.ui.signup.component

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun SignUpPetImageHolder(
    uri: Uri?,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(94.dp)
    ) {
        if (uri == null) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_signup_profile_edit),
                contentDescription = "add pet profile",
                tint = Color.Unspecified,
            )
        } else {
            AsyncImage(
                model = uri,
                contentDescription = "Pet Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_signup_image_edit),
                contentDescription = "edit icon",
                tint = Color.Unspecified,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpPetImageHolderPreview() {
    PawKeyTheme {
        SignUpPetImageHolder(
            uri = null,
        )
    }
}