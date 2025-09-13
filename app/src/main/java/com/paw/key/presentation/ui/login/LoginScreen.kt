package com.paw.key.presentation.ui.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.core.extension.noRippleClickable
import com.paw.key.presentation.ui.login.component.LoginTextField
import com.paw.key.presentation.ui.login.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    navigateHome: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isLoginFormValid = viewModel.state.collectAsStateWithLifecycle().value.isLoginValid
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LoginScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateNext = {
            coroutineScope.launch {
                PreferenceDataStore.saveLoginInfo(
                    email = state.email,
                    password = state.password
                )
                navigateNext()
            }
        },
        snackBarHostState = snackBarHostState,
        email = state.email,
        password = state.password,
        isPasswordVisible = state.isPasswordVisible,
        isLoginFormValid = isLoginFormValid,
        modifier = modifier,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onClickIcon = viewModel::onPasswordVisibilityChanged,
        navigateHome = navigateHome
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    navigateHome : () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onClickIcon: () -> Unit,
    snackBarHostState: SnackbarHostState,
    email: String,
    password: String,
    isPasswordVisible: Boolean,
    isLoginFormValid: Boolean,
    modifier: Modifier = Modifier,
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val passwordFocusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(PawKeyTheme.colors.white1)
            .verticalScroll(scrollState)
    ) {
        TopBar(
            title = "기존 계정으로 로그인",
            onBackClick = navigateUp,
            onClickTitle = navigateHome,
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding()
            )
        )

        Column(
            modifier = Modifier
                .padding(top = 80.dp)
                .padding(horizontal = 16.dp)
                .imePadding()
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "아이디",
                modifier = Modifier.padding(bottom = 8.dp),
                color = PawKeyTheme.colors.black,
                style = PawKeyTheme.typography.body14Sb
            )

            LoginTextField(
                textValue = email,
                placeHolder = "사용하실 아이디를 입력해주세요",
                isPassword = true,
                onTextChanged = onEmailChanged,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "비밀번호",
                modifier = Modifier.padding(bottom = 8.dp),
                color = PawKeyTheme.colors.black,
                style = PawKeyTheme.typography.body14Sb
            )

            LoginTextField(
                textValue = password,
                placeHolder = "사용하실 비밀번호를 입력해주세요",
                isPassword = isPasswordVisible,
                onTextChanged = onPasswordChanged,
                focusRequester = passwordFocusRequester,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                suffix = {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            if (isPasswordVisible) R.drawable.ic_eye_linear_gray_valid
                            else R.drawable.ic_eye_linear_invalid
                        ),
                        contentDescription = null,
                        modifier = Modifier.noRippleClickable(onClickIcon),
                        tint = PawKeyTheme.colors.gray200
                    )
                },
                modifier = Modifier
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    },
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .navigationBarsPadding()
                .padding(bottom = 60.dp)
        ) {
           /* PawkeyButton(
                text = "신규 계정으로 회원가입",
                onClick = navigateUp,
                enabled = true,
                isBackGround = true,
                isBorder = false,
                modifier = Modifier.fillMaxWidth()
            )*/

            Spacer(modifier = Modifier.height(12.dp))

            PawkeyButton(
                text = "로그인",
                onClick = navigateNext,
                enabled = isLoginFormValid,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen(){
    PawKeyTheme{
        LoginScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            navigateNext = {},
            onEmailChanged = {},
            onPasswordChanged = {},
            onClickIcon = {},
            navigateHome = {},
            snackBarHostState = SnackbarHostState(),
            email = "",
            password = "",
            isPasswordVisible = false,
            isLoginFormValid = true,
        )
    }
}