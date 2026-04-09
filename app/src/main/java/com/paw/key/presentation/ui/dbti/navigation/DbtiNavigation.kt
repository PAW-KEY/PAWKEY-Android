package com.paw.key.presentation.ui.dbti.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.paw.key.presentation.ui.dbti.StartScreen
import com.paw.key.presentation.ui.dbti.test.TestScreen
import kotlinx.serialization.Serializable

fun NavController.navigateDbtiStart(
    showSkip: Boolean = false,
    navOptions: NavOptions? = null
) {
    navigate(DbtiStart(showSkip), navOptions)
}

fun NavController.navigateDbtiTest(navOptions: NavOptions? = null) {
    navigate(DbtiTest, navOptions)
}

fun NavGraphBuilder.dbtiNavGraph(
    navController: NavController,
    navigateUp: () -> Unit,
    navigateHome: () -> Unit,
) {
    composable<DbtiStart> { backStackEntry ->
        val args = backStackEntry.toRoute<DbtiStart>()

        StartScreen(
            navigateUp = navigateUp,
            navigateToTest = { navController.navigateDbtiTest() },
            showSkipButton = args.showSkip,
            onSkip = if (args.showSkip) navigateHome else null
        )
    }

    composable<DbtiTest> {
        TestScreen(
            onBackClick = navigateUp
        )
    }
}

@Serializable
data class DbtiStart(
    val showSkip: Boolean = false
)

@Serializable
data object DbtiTest