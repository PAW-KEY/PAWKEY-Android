package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun UserProfileRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    UserProfileScreen(
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        snackBarHostState = snackBarHostState,
        modifier = modifier
    )
}

@Composable
fun UserProfileScreen(
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
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
            navigateUp = {},
            navigateNext = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}