package com.paw.key.presentation.ui.mypage.route.petinfo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.mypage.route.petinfo.PetProfileRoute
import kotlinx.serialization.Serializable

fun NavController.navigatePetProfile(
    navOptions: NavOptions?
) {
    navigate(PetProfile, navOptions)
}

fun NavGraphBuilder.petProfileNavGraph(
    navigateUp: () -> Unit
    ) {
    composable<PetProfile> {
        PetProfileRoute(
            navigateUp = navigateUp,
        )
    }
}

@Serializable
data object PetProfile : Route