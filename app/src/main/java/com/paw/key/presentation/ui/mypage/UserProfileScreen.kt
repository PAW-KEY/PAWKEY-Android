package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.mypage.state.UserProfileState
import com.paw.key.presentation.ui.mypage.viewmodel.UserProfileViewModel

@Composable
fun UserProfileRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    UserProfileScreen(
        state = state.value,
        navigateUp = navigateUp,
        modifier = modifier
    )
}

@Composable
fun UserProfileScreen(
    state: UserProfileState,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    id: String = "sgh1261",
    name: String = "김도기",
    gender: String = "여성",
    age: String = "24세",
    region: String = "강남구 역삼동"
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        TopBar(
            title = "견주 프로필",
            onBackClick = { navigateUp() }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileItem(label = "아이디", value = id)
            ProfileItem(label = "이름", value = name)
            ProfileItem(label = "성별", value = gender)
            ProfileItem(label = "나이", value = age)
            ProfileItem(label = "활동지역", value = region)
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = PawKeyTheme.typography.body14Sb
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = PawKeyTheme.typography.head18Sb,
            color = PawKeyTheme.colors.green500
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    PawKeyTheme {
        UserProfileScreen(
            state = UserProfileState(
                id = "sgh1261",
                name = "김도기",
                gender = "여성",
                age = "24세",
                region = "강남구 역삼동"
            ),
            navigateUp = {}
        )
    }
}