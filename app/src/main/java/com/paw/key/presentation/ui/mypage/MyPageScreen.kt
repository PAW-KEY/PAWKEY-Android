package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.paw.key.R
import com.paw.key.core.designsystem.component.SubChip
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.presentation.ui.mypage.component.GrayChip
import com.paw.key.presentation.ui.mypage.state.MyPageState
import com.paw.key.presentation.ui.mypage.viewmodel.MyPageViewModel
import kotlinx.coroutines.flow.first

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateUserProfile: () -> Unit,
    navigatePetProfile: () -> Unit,
    navigateArchivedCourse: () -> Unit,
    navigateSavedCourse: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val userId = PreferenceDataStore.getUserId()

    LaunchedEffect(Unit) {
        viewModel.getUserProfiles(userId = userId.first())
        viewModel.getPetProfiles(userId = userId.first())
    }
//    LaunchedEffect(Unit) {
//        val userId = 2 // ← 임시 테스트용
//        viewModel.getUserProfiles(userId = userId)
//        viewModel.getPetProfiles(userId = userId)
//    }

    MyPageScreen(
        state = state.value,
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateUserProfile = navigateUserProfile,
        navigatePetProfile = navigatePetProfile,
        navigateArchivedCourse = navigateArchivedCourse,
        navigateSavedCourse = navigateSavedCourse,
        snackBarHostState = snackBarHostState,
        modifier = modifier
    )
}

@Composable
fun MyPageScreen(
    state: MyPageState,
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateUserProfile: () -> Unit,
    navigatePetProfile: () -> Unit,
    navigateArchivedCourse: () -> Unit,
    navigateSavedCourse : () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PawKeyTheme.colors.white2)
            .padding(paddingValues)
    ){
        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            item {
                Text(
                    text = "마이페이지",
                    style = PawKeyTheme.typography.head22B,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 12.dp)
                )
                OwnerCard(
                    ownerName = state.ownerName,
                    navigateUserProfile = navigateUserProfile
                )
                Spacer(modifier = Modifier.height(19.dp))

                PetCard(
                    name = state.petName,
                    age = state.petAge,
                    gender = if (state.petGender == "M") {
                        "남아"
                    } else {
                        "여아"
                    },
                    tags = state.petTags,
                    walkCount = state.walkCount,
                    totalDistance = state.totalDistance,
                    image = state.petImageUrl,
                    navigatePetProfile = navigatePetProfile
                )

                Spacer(modifier = Modifier.height(12.dp))

                WalkRouteList(
                    routes = listOf("저장한 산책 루트", "내가 기록한 산책 루트"),
                    navigateSavedCourse = navigateSavedCourse,
                    navigateArchivedCourse = navigateArchivedCourse
                )
            }
        }
    }
}

@Composable
fun OwnerCard(
    ownerName: String,
    navigateUserProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(80.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp)
            .clickable { navigateUserProfile() },

        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = ownerName, style = PawKeyTheme.typography.head20B2)
        Spacer(Modifier.width(10.dp))
        Text(text = "견주", style = PawKeyTheme.typography.body14M, color = PawKeyTheme.colors.gray400)

        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
            contentDescription = "견주 프로필 이동",
            tint = PawKeyTheme.colors.gray300
        )
    }
}

@Composable
fun PetCard(
    name: String,
    age: String,
    gender: String,
    tags: List<String>,
    image: String,
    walkCount: Int,
    totalDistance: String,
    navigatePetProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(PawKeyTheme.colors.green500)
                .height(44.dp)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp), // Text의 베이스라인에 맞추기 위해 아이콘 크기 명시
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_personal_card),
                    contentDescription = "반려견 프로필",
                    modifier = Modifier.padding(top = 4.dp),

                            tint = Color.White
                )
            }
            Spacer(modifier.width(4.dp)
            )
            Text(
                text = "반려견 프로필",
                style = PawKeyTheme.typography.body16Sb,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
                modifier = modifier.clickable { navigatePetProfile() },
                contentDescription = "반려견 프로필 이동",
                tint = PawKeyTheme.colors.white1
            )
        }

        Column(modifier = modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier.width(16.dp))
                Column {
                    Text(name, style = PawKeyTheme.typography.head20B2)
                    Text("$age · $gender", style = PawKeyTheme.typography.caption12R, color = PawKeyTheme.colors.gray300)
                }
            }

            Spacer(modifier.height(8.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                tags.forEach {
                    GrayChip(
                        text = it
                    )
                }
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("산책 횟수", style = PawKeyTheme.typography.caption12Sb1)
                Text(
                    text = "${walkCount}회",
                    style = PawKeyTheme.typography.head20Sb,
                    color = PawKeyTheme.colors.green500
                )
            }

            Box(
                modifier = modifier
                    .height(32.dp)
                    .width(1.dp)
                    .background(color = PawKeyTheme.colors.gray100)
            )

            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("누적 거리", style = PawKeyTheme.typography.caption12Sb1)
                Text(
                    totalDistance,
                    style = PawKeyTheme.typography.head20Sb,
                    color = PawKeyTheme.colors.green500
                )
            }
        }
    }
}

@Composable
fun WalkRouteList(
    routes: List<String>,
    navigateSavedCourse: () -> Unit,
    navigateArchivedCourse: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text("산책 루트 관리", style = PawKeyTheme.typography.caption12Sb1, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))

        routes.forEachIndexed { index, route ->
            if (index != 0) {
                HorizontalDivider(thickness = 1.dp, color = PawKeyTheme.colors.gray50)
            }
            val iconRes = if (index == 0) {
                R.drawable.ic_mypage_heart
            } else {
                R.drawable.ic_mypage_edit
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = iconRes),
                    contentDescription = "산책루트 아이콘"
                )
                Text(

                    text = route,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    style = PawKeyTheme.typography.body16Sb,
                    color = PawKeyTheme.colors.gray950
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
                    modifier = modifier.clickable {
                        if (index == 0)
                            navigateSavedCourse()
                        else navigateArchivedCourse()
                    },
                    contentDescription = "산책루트 메뉴 이동"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    PawKeyTheme {
        MyPageScreen(state = MyPageState(
            ownerName = "김도기님",
            petName = "포비",
            petAge = "12세",
            petGender = "여아",
            petTags = listOf("조금 느긋해요", "#오토바이소리", "#대형견"),
            walkCount = 7,
            totalDistance = "14km"
        ),
            paddingValues = PaddingValues(),
            navigateUp = {},
            navigateUserProfile = {},
            navigatePetProfile = {},
            navigateArchivedCourse = {},
            navigateSavedCourse = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}