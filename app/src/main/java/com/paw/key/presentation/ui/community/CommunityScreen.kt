package com.paw.key.presentation.ui.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarHostState
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
fun CommunityRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    CommunityScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        snackBarHostState = snackBarHostState,
        modifier = modifier,
    )
}

@Composable
fun CommunityScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_community),
            contentDescription = stringResource(R.string.ic_community_description)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "커뮤니티 기능은",
                style = PawKeyTheme.typography.body16Sb,
                color = PawKeyTheme.colors.gray300
            )
            Text(
                text = "아직 준비중이에요",
                style = PawKeyTheme.typography.body16Sb,
                color = PawKeyTheme.colors.gray300
            )
        }
    }
}


@Preview
@Composable
private fun CommunityScreenPreview() {
    CommunityScreen(
        paddingValues = PaddingValues(),
        navigateUp = {},
        navigateNext = {},
        snackBarHostState = SnackbarHostState(),
    )
}