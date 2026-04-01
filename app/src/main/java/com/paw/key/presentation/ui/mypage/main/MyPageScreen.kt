package com.paw.key.presentation.ui.mypage.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.collectSideEffect
import com.paw.key.presentation.ui.mypage.main.component.MyList
import com.paw.key.presentation.ui.mypage.main.component.MyPageCard
import com.paw.key.presentation.ui.mypage.main.component.OwnerCard
import com.paw.key.presentation.ui.mypage.main.component.SettingList
import com.paw.key.presentation.ui.mypage.main.model.MyListState
import com.paw.key.presentation.ui.mypage.main.model.MyPageSideEffect
import com.paw.key.presentation.ui.mypage.main.model.MyPageState
import com.paw.key.presentation.ui.mypage.main.viewmodel.MyPageViewModel
import com.paw.key.presentation.ui.mypage.route.courseinfo.model.CourseType

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigatePetProfile: () -> Unit,
    navigateCourseInfo: (CourseType) -> Unit,
    navigatePetProfileList: () -> Unit,
    navigateUserProfile: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectSideEffect {
        when(it) {
            MyPageSideEffect.NavigateNext -> TODO()
            MyPageSideEffect.NavigateToLogin -> navigateToLogin()
            MyPageSideEffect.NavigateUp -> TODO()
            else -> {
                Toast.makeText(context, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    MyPageScreen(
        state = state,
        paddingValues = paddingValues,
        deleteUser = viewModel::removeUser,
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
    deleteUser: () -> Unit,
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
            isBackVisible = false,
            onClickTitle = deleteUser
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
                    ownerEmail = "",
                    navigateUserProfile = navigateUserProfile
                )
            }

            item {
                with(state.petInfo) {
                    MyPageCard(
                        userName = petName,
                        userAge = "6개월",
                        userGender = petGender,
                        dogBreed = petBreed,
                        buttonTitle = "DBTI검사하러 가기",
                        dogImage = petImageUrl
                    )
                }
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
            deleteUser = {}
        )
    }
}