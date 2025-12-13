package com.paw.key.presentation.ui.mypage.petinfo.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.mypage.petinfo.PetProfileRoute
import kotlinx.serialization.Serializable

fun NavController.navigatePetProfile(
    navOptions: NavOptions?
) {
    navigate(PetProfile, navOptions)
}

fun NavGraphBuilder.petProfileNavGraph(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    ) {
    composable<PetProfile> {
        PetProfileRoute(
            navigateUp = navigateUp,
            modifier = modifier
        )
    }
}

@Serializable
data object PetProfile : Route