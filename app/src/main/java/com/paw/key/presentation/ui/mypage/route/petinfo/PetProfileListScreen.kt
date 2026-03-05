package com.paw.key.presentation.ui.mypage.route.petinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.mypage.route.petinfo.component.PetInfoCard
import com.paw.key.presentation.ui.mypage.route.petinfo.viewmodel.PetProfileViewModel


@Composable
fun PetProfileListRoute(
    navigateUp: () -> Unit,
    navigatePetProfile: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PetProfileViewModel = hiltViewModel(),
) {
    PetProfileListScreen(
        navigateUp = navigateUp,
        navigatePetProfile = navigatePetProfile,
    )
}

@Composable
fun PetProfileListScreen(
    navigateUp: () -> Unit,
    navigatePetProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = PawKeyTheme.colors.defaultButton
            ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            title = "반려견 정보 수정",
            onBackClick = navigateUp,
        )

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp),
        ) {
            item {
                // TODO : API 연동시 변경 예정
                PetInfoCard(
                    petName = "Buddy",
                    petType = "Dog",
                    petImage = "https://example.com/buddy.jpg",
                    onPetClick = navigatePetProfile,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ReviewPetProfileListScreen() {
    PawKeyTheme {
        PetProfileListScreen(
            navigateUp = { },
            navigatePetProfile = { },
        )
    }
}