package com.paw.key.presentation.ui.mypage.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.MainTabRoute
import com.paw.key.presentation.ui.mypage.main.MyPageRoute
import com.paw.key.presentation.ui.mypage.route.courseinfo.model.CourseType
import kotlinx.serialization.Serializable

fun NavController.navigateMyPage(
    navOptions: NavOptions?
) {
    navigate(MyPage, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigatePetProfile: () -> Unit,
    navigateCourseInfo: (CourseType) -> Unit,
    navigatePetProfileList: () -> Unit,
    navigateUserProfile: () -> Unit,
    navigateLogin: () -> Unit,
    navigateDbtiStart: () -> Unit,  // ← 추가!
    modifier: Modifier = Modifier
) {
    composable<MyPage> {
        MyPageRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigatePetProfile = navigatePetProfile,
            navigateCourseInfo = navigateCourseInfo,
            navigatePetProfileList = navigatePetProfileList,
            navigateUserProfile = navigateUserProfile,
            navigateToLogin = navigateLogin,
            navigateDbtiStart = navigateDbtiStart,
            modifier = modifier
        )
    }
}

@Serializable
data object MyPage : MainTabRoute