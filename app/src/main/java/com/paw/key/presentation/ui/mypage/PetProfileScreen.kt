package com.paw.key.presentation.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.paw.key.R
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun PetProfileRoute(
    navigateUp : () -> Unit,
    modifier: Modifier = Modifier,
) {
    PetProfileScreen(
        navigateUp = navigateUp,
        modifier = modifier
    )
}

@Composable
fun PetProfileScreen(
    navigateUp: () -> Unit,
    name: String = "까루",
    gender: String = "남아",
    breed: String = "코리안 숏헤어",
    age: String = "4세",
    personality: String = "활동적",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        TopBar(title = "반려견 프로필",
            onBackClick = { navigateUp() })

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .size(108.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .border(2.dp, PawKeyTheme.colors.green500, CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        // 정보 항목
        PetProfileItem(label = "이름", value = name)
        PetProfileItem(label = "성별", value = gender)

        // 중성화 여부 (단일 텍스트)
        Text(
            text = "중성화했어요",
            style = PawKeyTheme.typography.caption12Sb2,
            color = PawKeyTheme.colors.gray300,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
        )

        PetProfileItem(label = "견종", value = breed)
        PetProfileItem(label = "나이", value = age)

        // 성향
        Text(
            text = "성향",
            style = PawKeyTheme.typography.body14Sb,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 10.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Column(
                modifier = Modifier.width(133.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "에너지 레벨",
                    style = PawKeyTheme.typography.body14R,
                    color = PawKeyTheme.colors.gray600
                )
                Text(
                    text = "활동적이에요",
                    style = PawKeyTheme.typography.head18Sb,
                    color = PawKeyTheme.colors.green500
                )
            }

            Spacer(modifier = Modifier.width(62.dp))

            Column(
                modifier = Modifier.width(133.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "사회성 레벨",
                    style = PawKeyTheme.typography.body14R,
                    color = PawKeyTheme.colors.gray600
                )
                Text(
                    text = "불편해해요",
                    style = PawKeyTheme.typography.head18Sb,
                    color = PawKeyTheme.colors.green500
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun PetProfileItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = PawKeyTheme.typography.body14Sb,
            modifier = Modifier.padding(start = 16.dp)
        )
        if (value.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = PawKeyTheme.typography.head18Sb,
                color = PawKeyTheme.colors.green500,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PetProfileScreenPreview() {
    PawKeyTheme {
        PetProfileScreen(
            navigateUp = {},
        )
    }
}