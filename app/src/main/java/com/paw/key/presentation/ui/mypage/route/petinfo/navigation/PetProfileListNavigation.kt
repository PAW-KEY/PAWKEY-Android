package com.paw.key.presentation.ui.mypage.route.petinfo.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.mypage.route.petinfo.PetProfileListRoute
import kotlinx.serialization.Serializable

fun NavController.navigatePetProfileList(
    navOptions: NavOptions?
) {
    navigate(PetProfileList, navOptions)
}

fun NavGraphBuilder.petProfileListNavGraph(
    navigateUp: () -> Unit,
    navigatePetProfile : () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<PetProfileList> {
        PetProfileListRoute(
            navigateUp = navigateUp,
            navigatePetProfile = navigatePetProfile,
            modifier = modifier
        )
    }
}

@Serializable
data object PetProfileList : Route