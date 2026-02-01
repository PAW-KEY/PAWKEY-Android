package com.paw.key.presentation.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.paw.key.presentation.ui.community.navigation.communityNavGraph
import com.paw.key.presentation.ui.course.entire.navigation.courseNavGraph
import com.paw.key.presentation.ui.course.entire.navigation.navigateCourse
import com.paw.key.presentation.ui.course.sharedwalk.complete.navigation.sharedWalkCompletionNavGraph
import com.paw.key.presentation.ui.course.sharedwalk.review.navigation.sharedWalkReviewNavGraph
import com.paw.key.presentation.ui.course.sharedwalk.sharedroute.navigation.sharedWalkCourseNavGraph
import com.paw.key.presentation.ui.course.walk.navigation.walkCourseNavGraph
import com.paw.key.presentation.ui.course.walkcomplete.navigation.walkCompletionNavGraph
import com.paw.key.presentation.ui.course.walkreview.navigation.walkReviewNavGraph
import com.paw.key.presentation.ui.dummy.navigation.dummyNavGraph
import com.paw.key.presentation.ui.dummy.next.dummyNextNavGraph
import com.paw.key.presentation.ui.home.navigation.homeLocationSettingNavGraph
import com.paw.key.presentation.ui.home.navigation.homeNavGraph
import com.paw.key.presentation.ui.login.navigation.loginNavGraph
import com.paw.key.presentation.ui.mypage.courseinfo.navigation.courseInfoNavGraph
import com.paw.key.presentation.ui.mypage.main.navigation.myPageNavGraph
import com.paw.key.presentation.ui.mypage.petinfo.navigation.petProfileListNavGraph
import com.paw.key.presentation.ui.mypage.petinfo.navigation.petProfileNavGraph
import com.paw.key.presentation.ui.mypage.userinfo.navigation.userProfileNavGraph
import com.paw.key.presentation.ui.onboard.navigation.onboardingNavGraph
import com.paw.key.presentation.ui.region.navigation.regionalNavGraph
import com.paw.key.presentation.ui.signup.navigation.signUpNavGraph
import com.paw.key.presentation.ui.splash.navigation.splashNavGraph

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun PawKeyNavHost(
    navigator: MainNavigator,
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 300)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = 300)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = 300)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 300)
            )
        },
    ) {
        homeNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateCourse,
            navigateHomeLocationSetting = navigator::navigateHomeLocationSetting,
            modifier = modifier,
        )

        homeLocationSettingNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = {
                navigator.navigateRegional(
                    regionId = it,
                    navOptions = null
                )
            },
            navigateHomeLocationSetting = navigator::navigateHomeLocationSetting,
            modifier = modifier,
        )

        courseNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateWalkCourse,
            navigateToDetail = { postId, routeId ->
                // Todo : 마찬가지로 이것도 그냥 넣어놓음 나중에 리스트 연결 후 예쩡 / 리스트 아이템이동

            },
            setOnVisibleRecord = navigator::setOnVisibleRecord,
            snackBarHostState = snackbarHostState
        )

        sharedWalkCourseNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = { routeId, pageId ->
                navigator.navigateSharedWalkCompletion(
                    routeId = routeId,
                    pageId = pageId
                )
            },
            snackBarHostState = snackbarHostState
        )

        sharedWalkCompletionNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = { routeId, pageId ->
                // Todo : 마찬가지로 이것도 그냥 넣어놓음 나중에 리스트 연결 후 예쩡
                navigator.navigateSharedWalkReview(
                    routeId = routeId,
                    pageId = pageId
                )
            },
            snackBarHostState = snackbarHostState
        )

        //  Todo : 리스트로 돌아갈 수 있게 - 다이얼로그
        sharedWalkReviewNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = {
                navigator.navController.navigateCourse(index = 1, navOptions = null)
            },
            snackBarHostState = snackbarHostState
        )

        walkCourseNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = {
                navigator.navigateWalkCompletion(
                    routeId = it,
                )
            },
            snackBarHostState = snackbarHostState
        )

        walkCompletionNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = { routeId ->
                navigator.navigateWalkReview(
                    routeId = routeId,
                )
            },
        )

        walkReviewNavGraph(
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateCourse,
            navigateShared = { routeId, pageId ->

            },
            snackBarHostState = snackbarHostState
        )

        communityNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateDummyNext,
            snackBarHostState = snackbarHostState
        )

        myPageNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigatePetProfile = navigator::navigatePetProfile,
            navigateCourseInfo = { courseType ->
                navigator.navigateCourseInfo(courseType)
            },
            navigatePetProfileList = navigator::navigatePetProfileList,
            navigateUserProfile = navigator::navigateUserProfile,
        )

        courseInfoNavGraph(
            navigateUp = navigator::navigateUp,
        )

        userProfileNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateMyPage,
            navigateNext = navigator::navigateDummyNext,
            snackBarHostState = snackbarHostState
        )

        petProfileNavGraph(
            navigateUp = navigator::navigateUp,
        )

        petProfileListNavGraph(
            navigateUp = navigator::navigateUp,
            navigatePetProfile = navigator::navigatePetProfile,
        )

        dummyNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateDummyNext,
            snackBarHostState = snackbarHostState
        )

        dummyNextNavGraph(
            paddingValues = paddingValues
        )

        splashNavGraph(
            paddingValues = paddingValues,
            navigateLogin = {
                val options = navOptions {
                    popUpTo(0) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
                navigator.navigateOnboarding(navOptions = options)
            }
        )

        onboardingNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateLogin,
            navigateSignUp = navigator::navigateLogin,
            snackBarHostState = snackbarHostState
        )


        loginNavGraph(
            paddingValues = paddingValues,
            navigateUp = {
                navigator.navigateUp()
            },
            navigateNext = {
                //navigator.navigateSignUpFlow()
            },
            navigateHome = {
                navigator.navigateHome()
            },
            snackBarHostState = snackbarHostState
        )

        regionalNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateHome,
            snackBarHostState = snackbarHostState
        )

        signUpNavGraph(
            navigateUp = navigator::navigateUp,
            navigateToHome = {
                val options = navOptions {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
                navigator.navigateHome(options)
            }
        )
    }
}