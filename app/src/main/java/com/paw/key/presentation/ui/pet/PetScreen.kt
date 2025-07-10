package com.paw.key.presentation.ui.pet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun PetRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    PetScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        snackBarHostState = snackBarHostState,
        modifier = modifier
    )
}

@Composable
fun PetScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    name: String = "포비",
    gender: String = "남아",
    breed: String = "미니어처 슈나우저",
    age: String = "12세",
    personality: String = "활동적",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        Spacer(modifier = modifier.height(30.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
                contentDescription = "뒤로가기",
                modifier = modifier
                    .size(24.dp)
                    .clickable { navigateUp() }
            )
            Spacer(modifier = modifier.width(16.dp))
            Text(
                text = "반려견 프로필",
                style = PawKeyTheme.typography.body16Sb
            )
        }

        Spacer(modifier = modifier.height(40.dp))

        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .border(
                    width = 1.35.dp,
                    color = PawKeyTheme.colors.green500,
                    shape = CircleShape
                )
        )

        PetProfileItem(label = "이름", value = name)
        PetProfileItem(label = "성별", value = gender)
        PetProfileItem(label = "견종", value = breed)
        PetProfileItem(label = "나이", value = age)

        Column {
            PetProfileItem(label = "성향", value = "") // 상단 타이틀만 필요하다면 value는 빈 값으로

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "에너지 레벨",
                        style = PawKeyTheme.typography.body14R,
                        color = PawKeyTheme.colors.gray600
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "활동적이에요",
                        style = PawKeyTheme.typography.head18Sb,
                        color = PawKeyTheme.colors.green500
                    )
                }

                Column {
                    Text(
                        text = "사회성 레벨",
                        style = PawKeyTheme.typography.body14R,
                        color = PawKeyTheme.colors.gray600
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "불편해해요",
                        style = PawKeyTheme.typography.head18Sb,
                        color = PawKeyTheme.colors.green500
                    )
                }
            }
        }
    }
}

@Composable
fun PetProfileItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = PawKeyTheme.typography.body14Sb
        )
        Spacer(modifier = modifier.height(4.dp))
        Text(
            text = value,
            style = PawKeyTheme.typography.head18Sb,
            color = PawKeyTheme.colors.green500
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PetScreenPreview() {
    PawKeyTheme {
        PetScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            navigateNext = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}
