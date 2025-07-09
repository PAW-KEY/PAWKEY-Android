package com.paw.key.presentation.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.paw.key.R
import com.paw.key.core.navigation.MainTabRoute
import com.paw.key.presentation.ui.home.navigation.Home
import com.paw.key.presentation.ui.course.entire.navigation.Course
import com.paw.key.presentation.ui.community.navigation.Community
import com.paw.key.presentation.ui.mypage.navigation.MyPage
import com.paw.key.R.string.ic_home_description
import com.paw.key.R.string.ic_course_description
import com.paw.key.R.string.ic_community_description
import com.paw.key.R.string.ic_mypage_description
import com.paw.key.core.navigation.Route


enum class MainTab(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    @StringRes val contentDescription: Int,
    val route: MainTabRoute,
) {
    // Todo : 아이콘 및 라벨 변경 예정
    HOME(
        selectedIcon = R.drawable.baseline_home_filled_24,
        unselectedIcon = R.drawable.baseline_home_filled_24,
        contentDescription = ic_home_description,
        route = Home,
    ),

    COURSE(
        selectedIcon = R.drawable.baseline_home_filled_24,
        unselectedIcon = R.drawable.baseline_home_filled_24,
        contentDescription = ic_course_description,
        route = Course,
    ),

    COMMUNITY(
        selectedIcon = R.drawable.baseline_home_filled_24,
        unselectedIcon = R.drawable.baseline_home_filled_24,
        contentDescription = ic_community_description,
        route = Community,
    ),

    MYPAGE(
        selectedIcon = R.drawable.baseline_home_filled_24,
        unselectedIcon = R.drawable.baseline_home_filled_24,
        contentDescription = ic_mypage_description,
        route = MyPage,
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}