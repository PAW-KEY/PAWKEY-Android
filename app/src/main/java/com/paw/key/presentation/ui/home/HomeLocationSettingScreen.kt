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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable
import com.paw.key.presentation.ui.home.viewmodel.HomeViewModel
import com.paw.key.presentation.ui.signup.component.FormField
import com.paw.key.presentation.ui.signup.component.LocationItem
import com.paw.key.presentation.ui.signup.component.LocationItemList
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
    navigateNext: (Int) -> Unit,
    navigateHomeLocationSetting: () -> Unit,
    modifier: Modifier = Modifier,
) {
    HomeLocationSettingScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        navigateHomeLocationSetting = navigateHomeLocationSetting,
        modifier = modifier
    )
}

@Composable
fun HomeLocationSettingScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: (Int) -> Unit,
    navigateHomeLocationSetting: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val regionList by viewModel.regionList.collectAsStateWithLifecycle()

    val selectedGu = state.selectedLocation.selectedGu
    val selectedDong = state.selectedLocation.selectedDong

    val guOptions = regionList.map { it.gu.name }

    val dongOptions = if (selectedGu.isNotEmpty()) {
        regionList.find { it.gu.name == selectedGu }?.dongs?.map {
            LocationItem(id = it.id, name = it.name)
        } ?: emptyList()
    } else {
        emptyList()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_black),
                contentDescription = "뒤로가기",
                modifier = Modifier.noRippleClickable { navigateUp() }
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.ic_home_location_title),
                    style = PawKeyTheme.typography.body16Sb
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
        }

        Spacer(modifier = Modifier.height(27.dp))

        FormField(
            label = stringResource(id = R.string.ic_onboarding_signup_main_location),
            content = {
                LocationList(
                    selected = selectedGu,
                    locations = guOptions,
                    onLocationSelected = { guName ->
                        val selectedGuItem = regionList.find { it.gu.name == guName }
                        selectedGuItem?.let {
                            viewModel.onGuSelected(it.gu.name, it.gu.id)
                        }
                    }
                )
            }
        )

        Spacer(modifier = Modifier.height(46.dp))

        if (selectedGu.isNotEmpty()) {
            FormField(
                label = stringResource(id = R.string.ic_onboarding_signup_sub_location),
                content = {
                    LocationItemList(
                        selected = selectedDong,
                        locations = dongOptions,
                        onLocationSelected = { locationItem ->
                            viewModel.onDongSelected(locationItem.name, locationItem.id)
                        }
                    )
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        val isFormValid = selectedGu.isNotEmpty() && selectedDong.isNotEmpty()

        PawkeyButton(
            text = stringResource(id = R.string.ic_onboarding_signup_button),
            enabled = isFormValid,
            onClick = {
                if (isFormValid) {
                    navigateNext(state.selectedLocation.selectedDongId)
                }
            }
        )

        Spacer(modifier = Modifier.height(46.dp))
    }
}