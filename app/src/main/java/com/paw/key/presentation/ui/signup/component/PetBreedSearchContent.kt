package com.paw.key.presentation.ui.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.extension.noRippleClickable
import kotlinx.coroutines.launch

private val dummyPetBreeds = listOf(
    "닥스훈트", "달마시안", "말라뮤트", "말티즈", "믹스견",
    "미니핀", "보스턴 테리어", "불독", "비글", "비숑 프리제",
    "사모예드", "시바견", "시츄", "요크셔 테리어", "진돗개",
    "치와와", "포메라니안", "푸들"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetBreedSearchContent(
    sheetState: SheetState,
    selectedBreed : String,
    onBreedSelected : (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // 바텀 시트용
    var bottomPetBreed by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    Column (
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .background(
                color = PawKeyTheme.colors.background,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "견종 검색",
            style = PawKeyTheme.typography.subTitle,
            color = PawKeyTheme.colors.contents,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 24.dp,
                    bottom = 24.dp
                ),
            textAlign = TextAlign.Center
        )

        SignUpTextField(
            value = bottomPetBreed,
            onValueChange = {
                bottomPetBreed = it
            },
            placeholder = "견종을 검색해보세요",
            suffix = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_signup_search),
                    contentDescription = "breed search",
                    tint = PawKeyTheme.colors.contents
                )
            },
            modifier = Modifier.onFocusChanged { focusState ->
                if (focusState.isFocused && sheetState.currentValue != SheetValue.Expanded) {
                    scope.launch {
                        sheetState.expand()
                    }
                }
            }
        )

        PetBreedSearchList(
            breedList = dummyPetBreeds,
            petBreed = bottomPetBreed,
            selectedBreed = selectedBreed,
            onBreedSelected = onBreedSelected,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
fun PetBreedSearchList(
    breedList : List<String>,
    petBreed : String,
    selectedBreed : String,
    onBreedSelected : (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredList = if (petBreed.isBlank()) {
        breedList
    } else {
        breedList.filter { it.contains(petBreed, ignoreCase = true) }
    }

    LazyColumn(
        modifier = modifier
            .padding(top = 8.dp)
    ) {
        itemsIndexed(
            items = filteredList
        ) { index, item ->
            PetBreedSearchItem(
                petBreed = item,
                onBreedSelected = onBreedSelected,
                isPetBreedSelected = petBreed == item || selectedBreed == item,
            )
        }
    }
}

@Composable
fun PetBreedSearchItem(
    petBreed : String,
    onBreedSelected : (String) -> Unit,
    isPetBreedSelected : Boolean,
    modifier: Modifier = Modifier
) {
    val textColor = if (isPetBreedSelected) {
        PawKeyTheme.colors.background
    } else {
        PawKeyTheme.colors.contents
    }

    val backgroundColor = if (isPetBreedSelected) {
        PawKeyTheme.colors.primary
    } else {
        PawKeyTheme.colors.background
    }

    Text(
        text = petBreed,
        style = PawKeyTheme.typography.bodyActive,
        color = textColor,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 11.dp
            )
            .noRippleClickable {
                onBreedSelected(petBreed)
            },
        textAlign = TextAlign.Start
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PetPetBreedSearchContentPreview() {
    PawKeyTheme {
        PetBreedSearchContent(
            selectedBreed = "",
            onBreedSelected = {},
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        )
    }
}