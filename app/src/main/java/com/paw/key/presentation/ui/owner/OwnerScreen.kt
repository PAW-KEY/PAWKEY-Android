package com.paw.key.presentation.ui.owner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun OwnerRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    OwnerScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateNext = navigateNext,
        snackBarHostState = snackBarHostState,
        modifier = modifier
    )
}

@Composable
fun OwnerScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    name: String = "김도기",
    gender: String = "여성",
    age: String = "24세",
    region: String = "강남구 역삼동"
) {
    PawKeyTheme {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "뒤로가기",
                    modifier = Modifier
                        .clickable { navigateUp() }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "견주 프로필",
                    style = PawKeyTheme.typography.body16Sb
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            ProfileItem(label = "이름", value = name)
            ProfileItem(label = "성별", value = gender)
            ProfileItem(label = "나이", value = age)
            ProfileItem(label = "활동지역", value = region)
        }
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
fun OwnerScreenPreview() {
    OwnerScreen(
        paddingValues = PaddingValues(),
        navigateUp = {},
        navigateNext = {},
        snackBarHostState = SnackbarHostState()
    )
}
