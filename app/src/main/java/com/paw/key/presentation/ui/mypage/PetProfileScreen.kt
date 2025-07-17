package com.paw.key.presentation.ui.mypage

import android.R.attr.contentDescription
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.paw.key.core.designsystem.component.TopBar
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.presentation.ui.mypage.viewmodel.PetProfileViewModel
import kotlinx.coroutines.flow.first

@Composable
fun PetProfileRoute(
    navigateUp : () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PetProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val userId = PreferenceDataStore.getUserId()

    LaunchedEffect(Unit) {
        viewModel.getPetProfiles(userId.first())
    }

    PetProfileScreen(
        imageUrl = state.value.imageUrl,
        name = state.value.name,
        gender = state.value.gender,
        breed = state.value.breed,
        age = state.value.age,
        isNeutered = state.value.isNeutered,
        energyLevel = state.value.energyLevel,
        socialLevel = state.value.socialLevel,
        navigateUp = navigateUp,
        modifier = modifier
    )
}

@Composable
fun PetProfileScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    name: String,
    gender: String,
    breed: String,
    age: String,
    isNeutered: Boolean,
    energyLevel: String,
    socialLevel: String
) {
    // 여기서 바로 가공
    val displayGender = when (gender.uppercase()) {
        "M" -> "남아"
        "F" -> "여아"
        else -> gender
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        TopBar(title = "반려견 프로필", onBackClick = navigateUp)

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .size(108.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .border(2.dp, PawKeyTheme.colors.green500, CircleShape)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        PetProfileItem(label = "이름", value = name)
        PetProfileItem(label = "성별", value = displayGender)

        if (isNeutered) {
            Text(
                text = "중성화했어요",
                style = PawKeyTheme.typography.caption12Sb2,
                color = PawKeyTheme.colors.gray300,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
        } else {
            Text(
                text = "중성화했어요",
                style = PawKeyTheme.typography.caption12Sb2,
                color = PawKeyTheme.colors.gray300,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
        }


        PetProfileItem(label = "견종", value = breed)
        PetProfileItem(label = "나이", value = age)

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
                    text = energyLevel,
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
                    text = socialLevel,
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
    value: String
) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, bottom = 8.dp)
    ) {
        Text(
            text = label,
            style = PawKeyTheme.typography.body14R,
            color = PawKeyTheme.colors.gray600
        )
        Text(
            text = value,
            style = PawKeyTheme.typography.head18Sb,
            color = PawKeyTheme.colors.green500
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PetProfileScreenPreview() {
    PawKeyTheme {
        PetProfileScreen(
            name = "까루",
            gender = "남아",
            breed = "코리안 숏헤어",
            age = "4세",
            energyLevel = "활동적이에요",
            socialLevel = "불편해해요",
            imageUrl = null,
            navigateUp = {},
            isNeutered = true
        )
    }
}