package com.paw.key.presentation.ui.course.walkcomplete.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.course.walkcomplete.WalkCompletionRoute
import kotlinx.serialization.Serializable

fun NavController.navigateWalkCompletion(
    navOptions: NavOptions?,
    routeId: Int
) {
    navigate(WalkCompletion(routeId), navOptions)
}

@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.walkCompletionNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: (routeId : Int) -> Unit,
) {
    composable<WalkCompletion> { backStackEntry ->
        val routeId = backStackEntry.arguments?.getInt("routeId") ?: 0
        WalkCompletionRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = {
                navigateNext(routeId)
            },
        )
    }
}

@Serializable
data class WalkCompletion(val routeId : Int) : Route