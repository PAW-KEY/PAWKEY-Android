package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.paw.key.R
import com.paw.key.core.designsystem.component.SubChip
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateUserProfile: () -> Unit,
    navigatePetProfile: () -> Unit,
    navigateArchivedCourse: () -> Unit,
    navigateSavedCourse: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    MyPageScreen(
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
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateUserProfile: () -> Unit,
    navigatePetProfile: () -> Unit,
    navigateArchivedCourse: () -> Unit,
    navigateSavedCourse : () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
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
            OwnerCard(ownerName = "김도기님", role = "견주", navigateUserProfile = navigateUserProfile)

            Spacer(modifier = Modifier.height(19.dp))

            PetCard(
                name = "포비",
                age = "12세",
                gender = "여아",
                tags = listOf("조금 느긋해요", "#오토바이소리", "#대형견"),
                walkCount = "7회",
                totalDistance = "14km",
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

@Composable
fun OwnerCard(
    ownerName: String,
    role: String,
    navigateUserProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(80.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = ownerName, style = PawKeyTheme.typography.head20B2)
        Spacer(Modifier.width(10.dp))
        Text(text = role, style = PawKeyTheme.typography.body14M)

        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
            modifier = modifier.clickable { navigateUserProfile() },
            contentDescription = "견주 프로필 이동"
        )
    }
}

@Composable
fun PetCard(
    name: String,
    age: String,
    gender: String,
    tags: List<String>,
    walkCount: String,
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
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_personal_card),
                contentDescription = "반려견 프로필",
                tint = Color.White
            )
            Spacer(modifier.width(4.dp)
            )
            Text(
                text = "반려견 프로필",
                style = PawKeyTheme.typography.caption12Sb1,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
                modifier = modifier.clickable { navigatePetProfile() },
                contentDescription = "반려견 프로필 이동",
                tint = Color.White
            )
        }

        Column(modifier = modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
                Spacer(modifier.width(16.dp))
                Column {
                    Text(name, style = PawKeyTheme.typography.head20B2)
                    Text("$age · $gender", style = PawKeyTheme.typography.body14R)
                }
            }

            Spacer(modifier.height(8.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                tags.forEach {
                    SubChip(
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
                    walkCount,
                    style = PawKeyTheme.typography.head20Sb,
                    color = PawKeyTheme.colors.green500
                )
            }

            Box(
                modifier = modifier
                    .height(32.dp)
                    .width(1.dp)
                    .background(color = PawKeyTheme.colors.white1)
            )

            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("누적 거리", style = PawKeyTheme.typography.caption12Sb1) //피그마랑 일치하는 글씨체 없음. 임의로 넣음
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
                Divider(color = PawKeyTheme.colors.gray50, thickness = 1.dp)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = route,
                    modifier = Modifier.weight(1f),
                    style = PawKeyTheme.typography.body16Sb,
                    color = PawKeyTheme.colors.gray950
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
                    modifier = modifier.clickable {
                        if (index == 0) navigateSavedCourse() else navigateArchivedCourse()
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
        MyPageScreen(
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