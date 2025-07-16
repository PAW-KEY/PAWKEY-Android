package com.paw.key.presentation.ui.signup.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.paw.key.core.navigation.Route
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.presentation.ui.signup.SignUpActivityRoute
import com.paw.key.presentation.ui.signup.SignUpDogRoute
import com.paw.key.presentation.ui.signup.SignUpLevelRoute
import com.paw.key.presentation.ui.signup.SignUpRoute
import com.paw.key.presentation.ui.signup.viewmodel.SignUpViewModel
import kotlinx.serialization.Serializable

@Serializable data object SignUpFlow : Route
@Serializable data object SignUp : Route
@Serializable data object SignUpActivity : Route
@Serializable data object SignUpDog : Route
@Serializable data object SignUpLevel : Route


fun NavHostController.navigateSignUpFlow(navOptions: NavOptions? = null) {
    this.navigate(SignUpFlow, navOptions)
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
fun NavGraphBuilder.signUpNavGraph(
    navController: NavHostController,
    navigateToHome: () -> Unit,
) {
    navigation<SignUpFlow>(
        startDestination = SignUp
    ) {
        composable<SignUp> { backStackEntry ->

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<SignUpFlow>()
            }
            val signUpViewModel: SignUpViewModel = hiltViewModel(parentEntry)

            val context = LocalContext.current
            val loginInfo by PreferenceDataStore.getLoginInfo().collectAsState(
                initial = PreferenceDataStore.LoginInfo("", "")
            )

            LaunchedEffect(loginInfo) {
                if (loginInfo.email.isNotEmpty() && loginInfo.password.isNotEmpty()) {
                    signUpViewModel.setLoginCredentials(
                        email = loginInfo.email,
                        password = loginInfo.password
                    )
                }
            }

            SignUpRoute(
                navigateSignUpActivity = {
                    navController.navigate(SignUpActivity)
                },
                viewModel = signUpViewModel
            )
        }

        composable<SignUpActivity> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<SignUpFlow>()
            }
            val signUpViewModel: SignUpViewModel = hiltViewModel(parentEntry)

            SignUpActivityRoute(
                navigateSignUpDog = {
                    navController.navigate(SignUpDog)
                },
                viewModel = signUpViewModel
            )
        }

        composable<SignUpDog> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<SignUpFlow>()
            }
            val signUpViewModel: SignUpViewModel = hiltViewModel(parentEntry)

            SignUpDogRoute(
                navigateNext = {
                    navController.navigate(SignUpLevel)
                },
                viewModel = signUpViewModel
            )
        }

        composable<SignUpLevel> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<SignUpFlow>()
            }
            val signUpViewModel: SignUpViewModel = hiltViewModel(parentEntry)

            SignUpLevelRoute(
                navigateNext = navigateToHome,
                viewModel = signUpViewModel
            )
        }
    }
}
