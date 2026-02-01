package com.paw.key.presentation.ui.mypage.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.paw.key.presentation.ui.mypage.courseinfo.model.CourseType
import com.paw.key.presentation.ui.mypage.main.component.MyList
import com.paw.key.presentation.ui.mypage.main.component.OwnerCard
import com.paw.key.presentation.ui.mypage.main.component.PetCard
import com.paw.key.presentation.ui.mypage.main.component.SettingList
import com.paw.key.presentation.ui.mypage.main.model.MyListState
import com.paw.key.presentation.ui.mypage.main.model.MyPageState
import com.paw.key.presentation.ui.mypage.main.viewmodel.MyPageViewModel
import kotlinx.coroutines.flow.first

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigatePetProfile: () -> Unit,
    navigateCourseInfo: (CourseType) -> Unit,
    navigatePetProfileList: () -> Unit,
    navigateUserProfile: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val userId = PreferenceDataStore.getUserId()

    LaunchedEffect(Unit) {
        viewModel.getUserProfiles(userId = userId.first())
        viewModel.getPetProfiles(userId = userId.first())
    }

    MyPageScreen(
        state = state.value,
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigatePetProfile = navigatePetProfile,
        navigateCourseInfo = navigateCourseInfo,
        navigatePetProfileList = navigatePetProfileList,
        navigateUserProfile = navigateUserProfile,
        modifier = modifier
    )
}

@Composable
fun MyPageScreen(
    state: MyPageState,
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigatePetProfile: () -> Unit,
    navigateCourseInfo: (CourseType) -> Unit,
    navigatePetProfileList: () -> Unit,
    navigateUserProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PawKeyTheme.colors.white2)
            .padding(paddingValues)
    ) {
        TopBar(
            title = "마이페이지",
            onBackClick = navigateUp,
            isBackVisible = false
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(PawKeyTheme.colors.defaultButton)
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                OwnerCard(
                    ownerName = state.ownerName,
                    ownerEmail = state.petName,
                    navigateUserProfile = navigateUserProfile
                )
            }

            item {
                PetCard(
                    name = state.petName,
                    age = state.petAge,
                    gender = if (state.petGender == "M") {
                        "남아"
                    } else {
                        "여아"
                    },
                    image = state.petImageUrl,
                    onPetClick = navigatePetProfileList
                )
            }

            item {
                MyList(
                    listTitle = "산책 루트 관리",
                    listContent = MyListState(),
                    onListClick = { index ->
                        val courseType = when (index) {
                            0 -> CourseType.MyCourse
                            1 -> CourseType.AllCourse
                            2 -> CourseType.ReviewCourse
                            else -> CourseType.MyCourse
                        }
                        navigateCourseInfo(courseType)
                    }
                )
            }

            item {
                SettingList(
                    listTitle = "설정",
                    listContent = MyListState(),
                    onListClick = { }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    PawKeyTheme {
        MyPageScreen(
            state = MyPageState(
                ownerName = "키큰오팔전차님",
                petName = "포비",
                petAge = "12세",
                petGender = "여아",
                petTags = listOf("조금 느긋해요", "#오토바이소리", "#대형견"),
                walkCount = 7,
                totalDistance = "14km"
            ),
            paddingValues = PaddingValues(),
            navigateUp = {},
            navigatePetProfile = {},
            navigateCourseInfo = {},
            navigatePetProfileList = {},
            navigateUserProfile = {},
        )
    }
}