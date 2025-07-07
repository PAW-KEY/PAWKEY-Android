package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
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
            style = PawKeyTheme.typography.head22B,
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
        Text(text = ownerName, style = PawKeyTheme.typography.head20B2)
        Spacer(Modifier.width(10.dp))
        Text(text = role, style = PawKeyTheme.typography.body14M)

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
                style = PawKeyTheme.typography.caption12Sb1,
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
                Text(name, style = PawKeyTheme.typography.head20B2)
                Text("$age · $gender", style = PawKeyTheme.typography.body14R)
                Spacer(Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    tags.forEach {
                        TagChip(text = it)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("산책 횟수", style = PawKeyTheme.typography.caption12Sb1)
                Text(
                    walkCount,
                    style = PawKeyTheme.typography.body14Sb,
                    color = PawKeyTheme.colors.beige500 // 초록색
                )
            }

            // 가운데 구분선
            Box(
                modifier = Modifier
                    .height(32.dp)
                    .width(1.dp)
                    .background(Color(0xFFE8E8E8))
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("누적 거리", style = PawKeyTheme.typography.caption12Sb1)
                Text(
                    totalDistance,
                    style = PawKeyTheme.typography.body14Sb,
                    color = PawKeyTheme.colors.beige500 // 초록색
                )
            }
        }
    }
}

@Composable
fun WalkRouteList(routes: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text("산책 루트 관리", style = PawKeyTheme.typography.caption12Sb1, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))

        routes.forEachIndexed { index, route ->
            if (index != 0) {
                Divider(color = Color(0xFFE8E8E8), thickness = 1.dp)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = route,
                    modifier = Modifier.weight(1f),
                    style = PawKeyTheme.typography.body14M,
                    color = PawKeyTheme.colors.gray950
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