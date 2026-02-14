package com.paw.key.presentation.ui.dbti

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paw.key.R
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme

@Composable
fun StartScreen(
    navigateUp: () -> Unit,
    navigateToTest: () -> Unit,
    showSkipButton: Boolean = false,
    onSkip: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        // 이미지를 배경으로
        Image(
            painter = painterResource(id = R.drawable.doki_welcome),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            TopBar(
                title = "DBTI 검사",
                onBackClick = navigateUp,
                isBackVisible = true
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "반려견 성향을 알아보는\nDBTI 성격 유형 검사",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PawKeyTheme.colors.contents,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp,
                    style = PawKeyTheme.typography.header1
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "본 조사는 사랑하는 반려견 위한 비정식 테스트입니다.",
                    fontSize = 14.sp,
                    color = PawKeyTheme.colors.defaultDark,
                    textAlign = TextAlign.Center,
                    style = PawKeyTheme.typography.body14R
                )

                Spacer(modifier = Modifier.weight(1f))

//            Image(
//                painter = painterResource(id = R.drawable.doki_welcome),
//                contentDescription = "DOKI 캐릭터",
//                modifier = Modifier
//                    .fillMaxSize(), // 전체 화면 차지
//                contentScale = ContentScale.Crop
//            )

                Spacer(modifier = Modifier.weight(1f))

                // 조건부 건너뛰기
                if (showSkipButton && onSkip != null) {
                    TextButton(
                        onClick = onSkip,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "건너뛰기",
                            fontSize = 16.sp,
                            color = PawKeyTheme.colors.defaultDark,
                            style = PawKeyTheme.typography.body16Sb
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                Button(
                    onClick = navigateToTest,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PawKeyTheme.colors.primary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "시작하기",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PawKeyTheme.colors.background,
                        style = PawKeyTheme.typography.mainButtonActive
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StartScreenWithSkipPreview() {
    PawKeyTheme {
        // 회원가입에서 진입 (건너뛰기 있음)
        StartScreen(
            navigateUp = {},
            navigateToTest = {},
            showSkipButton = true,
            onSkip = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StartScreenNoSkipPreview() {
    PawKeyTheme {
        // 마이페이지에서 진입 (건너뛰기 없음)
        StartScreen(
            navigateUp = {},
            navigateToTest = {},
            showSkipButton = false,
            onSkip = null
        )
    }
}