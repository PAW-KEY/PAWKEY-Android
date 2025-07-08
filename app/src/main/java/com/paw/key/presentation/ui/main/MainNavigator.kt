package com.paw.key.presentation.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.paw.key.presentation.ui.community.navigation.navigateCommunity
import com.paw.key.presentation.ui.course.navigation.navigateCourse
import com.paw.key.presentation.ui.dummy.navigation.navigateDummy
import com.paw.key.presentation.ui.dummy.next.navigateDummyNext
import com.paw.key.presentation.ui.home.navigation.Home
import com.paw.key.presentation.ui.home.navigation.navigateHome
import com.paw.key.presentation.ui.mypage.navigation.navigateMyPage
import com.paw.key.presentation.ui.owner.navigation.navigateOwner
import com.paw.key.presentation.ui.pet.navigation.navigatePet

class MainNavigator (
    val navController: NavHostController
) {
    private val currentDestination : NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = Home

    val currentTab : MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            navController.currentDestination?.route?.let {
                popUpTo(it) {
                    inclusive = true
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.HOME -> navController.navigateHome(navOptions)
            MainTab.COURSE -> navController.navigateCourse(navOptions)
            MainTab.COMMUNITY -> navController.navigateCommunity(navOptions)
            MainTab.MYPAGE -> navController.navigateMyPage(navOptions)
        }
    }
    fun navigateMyPage(navOptions: NavOptions? = null) {
        navController.navigateMyPage(navOptions = navOptions)
    }
    fun navigatePet(navOptions: NavOptions? = null) {
        navController.navigatePet(navOptions = navOptions)
    }

    fun navigateOwner(navOptions: NavOptions? = null) {
        navController.navigateOwner(navOptions = navOptions)
    }
//
    // 더미용 Todo : 나중에 위에거로 교환예정
    fun navigateToDummy(navOptions: NavOptions? = null) {
        navController.navigateDummy(navOptions = navOptions)
    }

    fun navigateDummyNext(navOptions: NavOptions? = null) {
        navController.navigateDummyNext(navOptions = navOptions)
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    @Composable
    fun showBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
