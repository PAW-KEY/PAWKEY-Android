package com.paw.key.presentation.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.noRippleClickable
import com.paw.key.presentation.ui.home.viewmodel.HomeViewModel
import com.paw.key.presentation.ui.signup.component.FormField
import com.paw.key.presentation.ui.signup.component.LocationButton
import com.paw.key.presentation.ui.signup.component.LocationList

@Preview(showBackground = true)
@Composable
private fun PreviewHomeLocationSettingScreen() {
    PawKeyTheme {
        HomeLocationSettingScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            navigateNext = {},
            navigateHomeLocationSetting = {}
        )
    }
}

@Composable
fun HomeLocationSettingRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    navigateHomeLocationSetting: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    HomeLocationSettingScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        navigateHomeLocationSetting = navigateHomeLocationSetting,
        modifier = modifier,
        viewModel = viewModel
    )
}

@Composable
fun HomeLocationSettingScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    navigateHomeLocationSetting: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_black),
                contentDescription = "뒤로가기",
                modifier = Modifier
                    .noRippleClickable { navigateUp() }
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "저장한 산책 루트",
                    style = PawKeyTheme.typography.body16Sb
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        FormField(
            label = stringResource(id = R.string.ic_onboarding_signup_main_location),
            content = {
                LocationButton(
                    isEnable = true,
                    location = "강남구",
                    onClick = { viewModel.toggleLocationMenu() }
                )
            }
        )

        Spacer(modifier = Modifier.height(46.dp))

        FormField(
            label = stringResource(id = R.string.ic_onboarding_signup_sub_location),
            content = {
                if (state.isLocationMenuVisible) {
                    LocationList(
                        selected = state.selectedLocation,
                        locations = listOf("개포동", "논현동", "뭔동", "동동동", "스꾸삐", "4글자유"),
                        onLocationSelected = { location ->
                            viewModel.selectLocation(location)
                        }
                    )
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        val isFormValid = state.selectedLocation.isNotEmpty()

        PawkeyButton(
            text = stringResource(id = R.string.ic_onboarding_signup_button),
            enabled = isFormValid,
            onClick = {
                if (isFormValid) {
                }
            }
        )

        Spacer(modifier = Modifier.height(46.dp))

    }

}


