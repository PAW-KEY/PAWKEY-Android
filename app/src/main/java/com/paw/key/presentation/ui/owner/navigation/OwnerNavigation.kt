package com.paw.key.presentation.ui.owner.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.MainTabRoute
import com.paw.key.presentation.ui.owner.OwnerRoute
import kotlinx.serialization.Serializable

fun NavController.navigateOwner(
    navOptions: NavOptions?
) {
    navigate(Owner, navOptions)
}

fun NavGraphBuilder.ownerNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState
) {
    composable<Owner> {
        OwnerRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            snackBarHostState = snackBarHostState
        )
    }
}

@Serializable
data object Owner : MainTabRoute