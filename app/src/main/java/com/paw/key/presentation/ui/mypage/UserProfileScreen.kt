package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.presentation.ui.mypage.viewmodel.UserProfileViewModel
import kotlinx.coroutines.flow.first

@Composable
fun UserProfileRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val userId = PreferenceDataStore.getUserId()

    LaunchedEffect(Unit) {
        viewModel.getUserProfiles(userId = userId.first())
    }

    UserProfileScreen(
        name = state.value.name,
        gender = state.value.gender,
        age = state.value.age.toString(),
        activeRegion = state.value.activeRegion,
        navigateUp = navigateUp,
        modifier = modifier
    )
}

@Composable
fun UserProfileScreen(name: String,
    gender: String,
    age: String,
    activeRegion: String,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
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
            UserProfileItem(label = "이름", value = name)
            UserProfileItem(label = "성별", value = gender)
            UserProfileItem(label = "나이", value = age)
            UserProfileItem(label = "활동지역", value = activeRegion)
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun UserProfileItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = PawKeyTheme.typography.body14Sb,
            modifier = Modifier.padding(start = 16.dp)
        )
        if (value.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = PawKeyTheme.typography.head18Sb,
                color = PawKeyTheme.colors.green500,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    PawKeyTheme {
        UserProfileScreen(
            name = "김도기",
            gender = "여성",
            age = "24",
            activeRegion = "강남구 역삼동",
            navigateUp = {}
        )
    }
}