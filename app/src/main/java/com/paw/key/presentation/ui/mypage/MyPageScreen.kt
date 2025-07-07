package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.mypage.component.TagChip

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    PawKeyTheme {
        MyPageScreen(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateNext = navigateNext,
            snackBarHostState = snackBarHostState,
            modifier = modifier
        )
    }
}

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp)
    ) {
        Text(
            text = "마이페이지",
            style = PawKeyTheme.typography.title1B17,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 12.dp)
        )

        OwnerCard(ownerName = "김도기님", role = "견주")

        Spacer(modifier = Modifier.height(19.dp))

        PetCard(
            name = "포비",
            age = "12세",
            gender = "여아",
            tags = listOf("조금 느긋해요", "#오토바이소리", "#대형견"),
            walkCount = "7회",
            totalDistance = "14km"
        )

        Spacer(modifier = Modifier.height(12.dp))

        WalkRouteList(
            routes = listOf("저장한 산책 루트", "내가 기록한 산책 루트")
        )
    }
}

@Composable
fun OwnerCard(ownerName: String, role: String) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = ownerName, style = PawKeyTheme.typography.title3B15)
            Text(text = role, style = PawKeyTheme.typography.body6M13)
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = null
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
    totalDistance: String
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PawKeyTheme.colors.gray950)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "반려견 프로필",
                style = PawKeyTheme.typography.label2M11,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = null
            )
        }

        Row(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(name, style = PawKeyTheme.typography.title3B15)
                Text("$age · $gender", style = PawKeyTheme.typography.body7R13)
                Spacer(Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    tags.forEach {
                        TagChip(text = it)
                    }
                }
            }
        }

        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text("산책 횟수", style = PawKeyTheme.typography.label2M11)
                Text(walkCount, style = PawKeyTheme.typography.body4B13, color = PawKeyTheme.colors.gray950)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("누적 거리", style = PawKeyTheme.typography.label2M11)
                Text(totalDistance, style = PawKeyTheme.typography.body4B13, color = PawKeyTheme.colors.gray950)
            }
        }
    }
}

@Composable
fun WalkRouteList(routes: List<String>) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White))
    {
        Spacer(modifier = Modifier.height(16.dp))
        Text("산책 루트 관리", style = PawKeyTheme.typography.label2M11, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        routes.forEach { route ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = route,
                    modifier = Modifier.weight(1f),
                    style = PawKeyTheme.typography.body6M13
                )
                Image(
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = null
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
            navigateNext = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}