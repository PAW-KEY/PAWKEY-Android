package com.paw.key.presentation.ui.mypage.userinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.presentation.ui.mypage.userinfo.component.UserEditTextField
import com.paw.key.presentation.ui.mypage.userinfo.component.UserGenderButton
import com.paw.key.presentation.ui.mypage.userinfo.component.UserProfileItem
import com.paw.key.presentation.ui.mypage.userinfo.viewmodel.UserProfileViewModel
import kotlinx.coroutines.flow.first

@Composable
fun UserProfileRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val userId = PreferenceDataStore.getUserId()

    LaunchedEffect(Unit) {
        viewModel.getUserProfiles(userId = userId.first())
    }

    UserProfileScreen(
        name = state.value.name,
        gender = state.value.gender,
        birth = state.value.name,
        navigateUp = navigateUp,
        modifier = modifier
    )
}

@Composable
private fun UserProfileScreen(
    name: String,
    gender: String,
    birth: String,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TopBar(
            title = "내 정보 수정",
            onBackClick = navigateUp
        )


        Spacer(modifier = Modifier.height(4.dp))

        UserProfileItem(
            label = "닉네임",
            profileItem = {
                UserEditTextField(
                    value = name,
                    onValueChange = {},
                    placeholder = "닉네임을 입력해주세요",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true,
                    singleLine = true
                )
            }
        )

        UserProfileItem(
            label = "생년원일",
            profileItem = {
                UserEditTextField(
                    value = birth,
                    onValueChange = {},
                    placeholder = "닉네임을 입력해주세요",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true,
                    singleLine = true
                )
            }
        )

        UserProfileItem(
            label = "성별",
            profileItem = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = modifier.fillMaxWidth()
                ) {
                    UserGenderButton(
                        user = "남성",
                        isSelect = gender == "남성",
                        onClick = { },
                        modifier = Modifier
                            .weight(1f)
                    )
                    UserGenderButton(
                        user = "여성",
                        isSelect = gender == "여성",
                        onClick = { },
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.weight(1F))

        PawkeyButton(
            text = "저장하기",
            enabled = true,
            onClick = { },
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(34.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun UserProfileScreenPreview() {
    PawKeyTheme {
        UserProfileScreen(
            name = "김도기",
            gender = "여성",
            birth = "2002/06/21",
            navigateUp = {}
        )
    }
}