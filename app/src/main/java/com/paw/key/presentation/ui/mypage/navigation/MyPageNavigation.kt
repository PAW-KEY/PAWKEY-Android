package com.paw.key.presentation.ui.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.MainTabRoute
import com.paw.key.presentation.ui.mypage.MyPageRoute
import com.paw.key.presentation.ui.mypage.PetProfileScreen
import com.paw.key.presentation.ui.mypage.UserProfileScreen
import kotlinx.serialization.Serializable

fun NavController.navigateMyPage(
    navOptions: NavOptions?
) {
    navigate(MyPage, navOptions)
}

//견주 프로필
fun NavController.navigateUserProfile(
    navOptions: NavOptions?
) {
    navigate(UserProfile, navOptions)
}

//반려견 프로필
fun NavController.navigatePetProfile(
    navOptions: NavOptions?
) {
    navigate(PetProfile, navOptions)
}

//저장한 산책루트
fun NavController.navigateSavedCourseList(
    navOptions: NavOptions?
) {
    navigate(SavedCourseList, navOptions)
}

//기록한 산책루트
fun NavController.navigateArchivedCourseList(
    navOptions: NavOptions?
) {
    navigate(ArchivedCourseList, navOptions)
}



fun NavGraphBuilder.myPageNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState
) {
    composable<MyPage> {
        MyPageRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            snackBarHostState = snackBarHostState
        )
    }
    composable<UserProfile> {
        UserProfileScreen(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            snackBarHostState = snackBarHostState
        )
    }

    composable<PetProfile> {
        PetProfileScreen(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            snackBarHostState = snackBarHostState
        )
    }
//
//    composable<SavedCourseList> {
//        SavedCourseListScreen(
//            paddingValues = paddingValues,
//            navigateUp = navigateUp
//        )
//    }
//
//    composable<ArchivedCourseList> {
//        ArchivedCourseListScreen(
//            paddingValues = paddingValues,
//            navigateUp = navigateUp
//        )
//    }
}

@Serializable
data object MyPage : MainTabRoute
@Serializable
data object UserProfile
@Serializable
data object PetProfile
@Serializable
data object SavedCourseList
@Serializable
data object ArchivedCourseList