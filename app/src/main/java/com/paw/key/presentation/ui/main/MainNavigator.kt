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
import com.paw.key.presentation.ui.course.entire.navigation.navigateCourse
import com.paw.key.presentation.ui.course.sharedwalk.complete.navigation.navigateSharedWalkCompletion
import com.paw.key.presentation.ui.course.sharedwalk.review.navigation.navigateSharedWalkReview
import com.paw.key.presentation.ui.course.sharedwalk.sharedroute.navigation.navigateSharedWalkCourse
import com.paw.key.presentation.ui.course.walk.navigation.navigateWalkCourse
import com.paw.key.presentation.ui.course.walkcomplete.navigation.navigateWalkCompletion
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
import com.paw.key.presentation.ui.mypage.navigation.navigateUserProfile
import com.paw.key.presentation.ui.region.navigation.navigateRegional
import com.paw.key.presentation.ui.mypage.navigation.navigateSavedDetail
import com.paw.key.presentation.ui.mypage.navigation.navigateUserProfile
import com.paw.key.presentation.ui.onboard.navigation.navigateOnboarding
import com.paw.key.presentation.ui.region.navigation.navigateRegional
import com.paw.key.presentation.ui.signup.navigation.navigateSignLevel
import com.paw.key.presentation.ui.signup.navigation.navigateSignUp
import com.paw.key.presentation.ui.signup.navigation.navigateSignUpActivity
import com.paw.key.presentation.ui.signup.navigation.navigateSignUpDog
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
            MainTab.COURSE -> navController.navigateCourse(navOptions)
            MainTab.COMMUNITY -> navController.navigateCommunity(navOptions)
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


    fun navigateSignUp(navOptions: NavOptions? = null) {
        navController.navigateSignUp(navOptions = navOptions)
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
    
    fun navigateSavedDetail(navOptions: NavOptions? = null) {
        navController.navigateSavedDetail(navOptions = navOptions)
    }


    
    fun navigateArchivedCourse(navOptions: NavOptions? = null) {
        navController.navigateArchivedCourse(navOptions = navOptions)
    }

    fun navigateCourse(index : Int = 0, navOptions: NavOptions? = null) {
        navController.navigateCourse(
            index = index,
            navOptions = navOptions
        )
    }

    fun navigateHome(navOptions: NavOptions? = null) {
        navController.navigateHome(navOptions = navOptions)
    }

    fun navigateHomeLocationSetting(navOptions: NavOptions? = null) {
        navController.navigateHomeLocationSetting(navOptions = navOptions)
    }

    /*메인 탭 산택 기준 - 산책하기, 완료, 리뷰*/
//    fun navigateRegional(navOptions: NavOptions? = null) {
//        navController.navigateRegional(navOptions = navOptions)
//    }
    fun navigateSharedWalkCourse(navOptions: NavOptions? = null) {
        navController.navigateSharedWalkCourse(navOptions = navOptions)
    }

    fun navigateSharedWalkReview(routeId: Int, navOptions: NavOptions? = null) {
        navController.navigateSharedWalkReview(
            routeId = routeId,
            navOptions = navOptions
        )
    }

    fun navigateSharedWalkCompletion(
        routeId: Int,
        navOptions: NavOptions? = null
    ) {
        navController.navigateSharedWalkCompletion(
            routeId = routeId,
            navOptions = navOptions
        )
    }

    fun navigateArchivedDetail(
        routeId: Int,
        navOptions: NavOptions? = null
    ) {
        navController.navigateArchivedDetail(
            routeId = routeId,
            navOptions = navOptions
        )
    }

    fun navigateWalkCourse(navOptions: NavOptions? = null) {
        navController.navigateWalkCourse(navOptions = navOptions)
    }

    fun navigateWalkCompletion(routeId : Int, navOptions: NavOptions? = null) {
        navController.navigateWalkCompletion(
            routeId = routeId,
            navOptions = navOptions
        )
    }

    fun navigateWalkReview(routeId: Int, navOptions: NavOptions? = null) {
        navController.navigateWalkReview(
            routeId = routeId,
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

    fun navigateSignUpActivity(navOptions: NavOptions? = null) {
        navController.navigateSignUpActivity(navOptions = navOptions)
    }

    fun navigateSignUpDog(navOptions: NavOptions? = null) {
        navController.navigateSignUpDog(navOptions = navOptions)
    }

    fun navigateSignUpLevel(navOptions: NavOptions? = null) {
        navController.navigateSignLevel(navOptions = navOptions)
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
