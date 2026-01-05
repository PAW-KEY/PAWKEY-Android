package com.paw.key.presentation.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.paw.key.R
import com.paw.key.R.string.ic_course_description
import com.paw.key.R.string.ic_home_description
import com.paw.key.R.string.ic_mypage_description
import com.paw.key.core.navigation.MainTabRoute
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.community.navigation.Community
import com.paw.key.presentation.ui.course.navigation.WalkPrepare
import com.paw.key.presentation.ui.home.navigation.Home
import com.paw.key.presentation.ui.mypage.navigation.MyPage


enum class MainTab(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    @StringRes val contentDescription: Int,
    val route: MainTabRoute,
) {
    HOME(
        selectedIcon = R.drawable.ic_home_fill,
        unselectedIcon = R.drawable.ic_home_linear,
        contentDescription = ic_home_description,
        route = Home,
    ),
    COURSE(
        selectedIcon = R.drawable.ic_walk_fill,
        unselectedIcon = R.drawable.ic_walk_linear,
        contentDescription = ic_course_description,
        route = WalkPrepare,
    ),
    ROUTERECOMMAND(
        selectedIcon = R.drawable.ic_route_recommand_fill,
        unselectedIcon = R.drawable.ic_route_recommand_linear,
        contentDescription = R.string.ic_route_recommand_description,
        route = Community,
    ),
    MYPAGE(
        selectedIcon = R.drawable.ic_mypage_fill,
        unselectedIcon = R.drawable.ic_mypage_linear,
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