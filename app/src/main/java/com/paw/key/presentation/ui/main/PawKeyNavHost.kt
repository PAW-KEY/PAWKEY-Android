package com.paw.key.presentation.ui.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
import com.paw.key.presentation.ui.course.walkreview.navigation.navigateWalkReview
import com.paw.key.presentation.ui.course.walkreview.navigation.walkReviewNavGraph
import com.paw.key.presentation.ui.dummy.navigation.dummyNavGraph
import com.paw.key.presentation.ui.dummy.next.dummyNextNavGraph
import com.paw.key.presentation.ui.home.navigation.homeLocationSettingNavGraph
import com.paw.key.presentation.ui.home.navigation.homeNavGraph
import com.paw.key.presentation.ui.login.navigation.loginNavGraph
import com.paw.key.presentation.ui.mypage.navigation.archivedCourseNavGraph
import com.paw.key.presentation.ui.mypage.navigation.archivedDetailNavGraph
import com.paw.key.presentation.ui.mypage.navigation.myPageNavGraph
import com.paw.key.presentation.ui.mypage.navigation.petProfileNavGraph
import com.paw.key.presentation.ui.mypage.navigation.savedCourseNavGraph
import com.paw.key.presentation.ui.mypage.navigation.savedDetailNavGraph
import com.paw.key.presentation.ui.mypage.navigation.userProfileNavGraph
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
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
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
            navigateNext = navigator::navigateRegional,
            navigateHomeLocationSetting = navigator::navigateHomeLocationSetting,
            modifier = modifier,
        )

        courseNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateWalkCourse,
            navigateToDetail = {
                // Todo : 마찬가지로 이것도 그냥 넣어놓음 나중에 리스트 연결 후 예쩡 / 리스트 아이템이동
                navigator.navigateArchivedDetail(
                    pageId = 20,
                    routeId = 3
                )
            },
            setOnVisibleRecord = navigator::setOnVisibleRecord,
            snackBarHostState = snackbarHostState
        )

        sharedWalkCourseNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = {
                // Todo : 마찬가지로 이것도 그냥 넣어놓음 나중에 리스트 연결 후 예쩡
                navigator::navigateSharedWalkCompletion
            },
            snackBarHostState = snackbarHostState
        )

        sharedWalkCompletionNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = {
                // Todo : 마찬가지로 이것도 그냥 넣어놓음 나중에 리스트 연결 후 예쩡
                navigator.navigateSharedWalkReview(
                    routeId = 2
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
                Log.e("navigateNext", "navigateNext : $it")
                navigator.navigateWalkCompletion(
                    routeId = it,
                )
            },
            snackBarHostState = snackbarHostState
        )

        walkCompletionNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = {
                navigator.navigateWalkReview(
                    routeId = it,
                )
            },
        )

        walkReviewNavGraph(
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateCourse,
            navigateShared = {
                navigator.navigateArchivedDetail( // Todo 새로 생성 시라 pageId 변경
                    pageId = 0,
                    routeId = it
                )
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
            navigateUserProfile = navigator::navigateUserProfile,
            navigatePetProfile = navigator::navigatePetProfile,
            navigateArchivedCourse = navigator::navigateArchivedCourse,
            navigateSavedCourse = navigator::navigateSavedCourse,
            snackBarHostState = snackbarHostState
        )

        savedCourseNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateSavedDetail,
            snackBarHostState = snackbarHostState
        )

        archivedCourseNavGraph(
            navigateUp = navigator::navigateUp,
            navigateNext = {
                navigator.navigateArchivedDetail(
                    // Todo : 리스트에서 상세정보 item id 넣어놓기 일단2
                    pageId = 0,
                    routeId = 2
                )
            },
            modifier = modifier
        )

        savedDetailNavGraph(
            navigateUp = navigator::navigateUp,
            navigateToWalk = navigator::navigateWalkCourse,
            snackBarHostState = snackbarHostState
        )

        archivedDetailNavGraph(
            navigateUp = navigator::navigateUp,
            navigateToSharedWalk = navigator::navigateSharedWalkCourse,
            modifier = modifier
        )

        userProfileNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateMyPage,
            navigateNext = navigator::navigateDummyNext,
            snackBarHostState = snackbarHostState
        )

        petProfileNavGraph(
            navigateUp = navigator::navigateUp,
            modifier = modifier
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
            navigateNext = navigator::navigateDummyNext,
            navigateSignUp = navigator::navigateLogin,
            snackBarHostState = snackbarHostState
        )


        loginNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateHome,
            navigateNext = {
                navigator.navigateSignUpFlow()
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
            navController = navigator.navController,
            navigateToHome = {
                val options = navOptions {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
                navigator.navigateHome(navOptions = options)
            }
        )


    }
}