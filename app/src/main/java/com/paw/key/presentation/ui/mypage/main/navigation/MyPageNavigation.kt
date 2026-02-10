package com.paw.key.presentation.ui.mypage.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.MainTabRoute
import com.paw.key.presentation.ui.mypage.courseinfo.model.CourseType
import com.paw.key.presentation.ui.mypage.main.MyPageRoute
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
            modifier = modifier
        )
    }
}

@Serializable
data object MyPage : MainTabRoute