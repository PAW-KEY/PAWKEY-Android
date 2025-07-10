package com.paw.key.presentation.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.paw.key.presentation.ui.community.navigation.communityNavGraph
import com.paw.key.presentation.ui.course.entire.navigation.courseNavGraph
import com.paw.key.presentation.ui.course.entire.tab.map.navigation.walkCourseNavGraph
import com.paw.key.presentation.ui.course.walkcomplete.navigation.walkCompletionNavGraph
import com.paw.key.presentation.ui.course.walkrecord.navigation.walkReviewNavGraph
import com.paw.key.presentation.ui.dummy.navigation.dummyNavGraph
import com.paw.key.presentation.ui.dummy.next.dummyNextNavGraph
import com.paw.key.presentation.ui.home.navigation.homeNavGraph
import com.paw.key.presentation.ui.login.navigation.loginNavGraph
import com.paw.key.presentation.ui.mypage.navigation.myPageNavGraph
import com.paw.key.presentation.ui.owner.navigation.ownerNavGraph
import com.paw.key.presentation.ui.pet.navigation.petNavGraph
import com.paw.key.presentation.ui.signup.navigation.signupNavGraph
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
            navigateUp = navigator.navController::navigateUp,
            navigateNext = navigator::navigateCourse,
            snackBarHostState = snackbarHostState,
            modifier = modifier,
        )

        courseNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateWalkCourse,
            setOnVisibleRecord = navigator::setOnVisibleRecord,
            snackBarHostState = snackbarHostState
        )

        walkCourseNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateWalkCompletion,
            snackBarHostState = snackbarHostState
        )

        walkCompletionNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateWalkReview,
            snackBarHostState = snackbarHostState
        )

        walkReviewNavGraph(
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateDummyNext,
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
            navigateNext = navigator::navigateDummyNext,
            snackBarHostState = snackbarHostState
        )

        ownerNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateDummyNext,
            snackBarHostState = snackbarHostState
        )

        petNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateDummyNext,
            snackBarHostState = snackbarHostState
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
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateDummyNext,
            navigateLogin = navigator::navigateLogin,
            snackBarHostState = snackbarHostState
        )

        loginNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateDummyNext,
            navigateSignUp = navigator::navigateSignUp,
            snackBarHostState = snackbarHostState
        )

        signupNavGraph(
            paddingValues = paddingValues,
            navigateUp = navigator::navigateUp,
            navigateNext = navigator::navigateDummyNext,
            navigateLogin = navigator::navigateLogin,
            snackBarHostState = snackbarHostState
        )
    }
}