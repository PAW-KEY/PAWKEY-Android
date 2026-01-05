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
import com.paw.key.presentation.ui.course.navigation.navigateWalkCourse
import com.paw.key.presentation.ui.course.navigation.navigateWalkPrepare
import com.paw.key.presentation.ui.course.walkreview.navigation.navigateWalkReview
import com.paw.key.presentation.ui.dummy.next.navigateDummyNext
import com.paw.key.presentation.ui.home.navigation.navigateHome
import com.paw.key.presentation.ui.home.navigation.navigateHomeLocationSetting
import com.paw.key.presentation.ui.login.navigation.navigateLogin
import com.paw.key.presentation.ui.mypage.navigation.navigateArchivedCourse
import com.paw.key.presentation.ui.mypage.navigation.navigateArchivedDetail
import com.paw.key.presentation.ui.mypage.navigation.navigateMyPage
import com.paw.key.presentation.ui.mypage.navigation.navigatePetProfile
import com.paw.key.presentation.ui.mypage.navigation.navigateSavedCourse
import com.paw.key.presentation.ui.mypage.navigation.navigateSavedDetail
import com.paw.key.presentation.ui.mypage.navigation.navigateUserProfile
import com.paw.key.presentation.ui.onboard.navigation.navigateOnboarding
import com.paw.key.presentation.ui.region.navigation.navigateRegional
import com.paw.key.presentation.ui.signup.navigation.navigateSignUp
import com.paw.key.presentation.ui.splash.navigation.Splash

class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = Splash

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    var isRecordVisible: Boolean = false
        private set

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
            MainTab.COURSE -> navController.navigateWalkPrepare(navOptions)
            MainTab.ROUTERECOMMAND -> navController.navigateCommunity(navOptions)
            MainTab.MYPAGE -> navController.navigateMyPage(navOptions)
        }
    }

    fun setOnVisibleRecord(visible: Boolean) {
        isRecordVisible = visible
    }

    /*온보딩, 로그인, */
    fun navigateOnboarding(navOptions: NavOptions? = null) {
        navController.navigateOnboarding(navOptions = navOptions)
    }

    fun navigateLogin(navOptions: NavOptions? = null) {
        navController.navigateLogin(navOptions = navOptions)
    }

    fun navigateMyPage(navOptions: NavOptions? = null) {
        navController.navigateMyPage(navOptions = navOptions)
    }


    fun navigateUserProfile(navOptions: NavOptions? = null) {
        navController.navigateUserProfile(navOptions = navOptions)
    }

    fun navigatePetProfile(navOptions: NavOptions? = null) {
        navController.navigatePetProfile(navOptions = navOptions)
    }

    fun navigateSavedCourse(navOptions: NavOptions? = null) {
        navController.navigateSavedCourse(navOptions = navOptions)
    }
    
    fun navigateSavedDetail(
        pageId: Int,
        routeId: Int,
        navOptions: NavOptions? = null)
    {
        navController.navigateSavedDetail(
            pageId = pageId,
            routeId = routeId,
            navOptions = navOptions
        )
    }

    // Todo : 나중에 로직 플로우 확인하고 수정예정
    fun navigateSignUp(navOptions: NavOptions? = null) {
        navController.navigateSignUp(navOptions)
    }
    
    fun navigateArchivedCourse(navOptions: NavOptions? = null) {
        navController.navigateArchivedCourse(navOptions = navOptions)
    }


    fun navigateHome(navOptions: NavOptions? = null) {
        navController.navigateHome(navOptions = navOptions)
    }

    fun navigateHomeLocationSetting(navOptions: NavOptions? = null) {
        navController.navigateHomeLocationSetting(navOptions = navOptions)
    }

    fun navigateArchivedDetail(
        routeId: Int,
        pageId : Int,
        navOptions: NavOptions? = null
    ) {
        navController.navigateArchivedDetail(
            pageId = pageId,
            routeId = routeId,
            navOptions = navOptions
        )
    }

    fun navigateWalkCourse(navOptions: NavOptions? = null) {
        navController.navigateWalkCourse(navOptions = navOptions)
    }

    fun navigateWalkReview(
        navOptions: NavOptions? = null
    ) {
        navController.navigateWalkReview(
            navOptions = navOptions
        )
    }


    fun navigateDummyNext(navOptions: NavOptions? = null) {
        navController.navigateDummyNext(navOptions = navOptions)
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateRegional(regionId: Int, navOptions: NavOptions? = null) {
        navController.navigateRegional(regionId, navOptions)
    }



    @Composable
    fun showBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    } && !isRecordVisible
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
