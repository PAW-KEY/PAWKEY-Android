package com.paw.key.presentation.ui.detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.paw.key.core.navigation.Route
import com.paw.key.presentation.ui.detail.DetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateDetail(
    navOptions: NavOptions?
) {
    navigate(Detail, navOptions)
}

fun NavGraphBuilder.detailNavGraph(
    paddingValues: PaddingValues,
) {
    composable<Detail> {
        DetailRoute(
            paddingValues = paddingValues,
        )
    }
}

@Serializable
data object Detail : Route