package com.paw.key.presentation.ui.dbti.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.presentation.ui.dbti.StartScreen
import com.paw.key.presentation.ui.dbti.test.TestScreen
import kotlinx.serialization.Serializable

fun NavController.navigateDbtiStart(navOptions: NavOptions? = null) {
    navigate(DbtiStart, navOptions)
}

fun NavController.navigateDbtiTest(navOptions: NavOptions? = null) {
    navigate(DbtiTest, navOptions)
}

fun NavGraphBuilder.dbtiNavGraph(
    navController: NavController,  // 파라미터 추가!
    navigateUp: () -> Unit,
) {
    composable<DbtiStart> {
        StartScreen(
            navigateUp = navigateUp,
            navigateToTest = { navController.navigateDbtiTest() },  // 연결!
            showSkipButton = false,
            onSkip = null
        )
    }

    composable<DbtiTest> {
        TestScreen(
            onBackClick = navigateUp
        )
    }
}

@Serializable
data object DbtiStart

@Serializable
data object DbtiTest