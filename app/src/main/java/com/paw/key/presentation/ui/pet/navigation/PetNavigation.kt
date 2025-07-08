package com.paw.key.presentation.ui.pet.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.pet.PetRoute
import kotlinx.serialization.Serializable

fun NavController.navigatePet(
    navOptions: NavOptions?
) {
    navigate(Pet, navOptions)
}

fun NavGraphBuilder.petNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState
) {
    composable<Pet> {
        PetRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            snackBarHostState = snackBarHostState
        )
    }
}

@Serializable
data object Pet : Route