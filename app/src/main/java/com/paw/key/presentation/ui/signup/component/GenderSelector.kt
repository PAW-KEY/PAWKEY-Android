package com.paw.key.presentation.ui.signup.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.presentation.ui.signup.state.Gender

@Composable
fun GenderSelector(
    selectedGender: Gender,
    onGenderSelected: (Gender) -> Unit,
    modifier: Modifier = Modifier,
    type : String? = "유저",
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        if (type == "유저") {
            SignUpUserSelectButton(
                user = "남성",
                isSelect = selectedGender == Gender.MALE,
                onClick = { onGenderSelected(Gender.MALE) },
                modifier = Modifier.weight(1f)
            )

            SignUpUserSelectButton(
                user = "여성",
                isSelect = selectedGender == Gender.FEMALE,
                onClick = { onGenderSelected(Gender.FEMALE) },
                modifier = Modifier.weight(1f)
            )
        } else {
            SignUpUserSelectButton(
                user = "남아",
                isSelect = selectedGender == Gender.MALE,
                onClick = { onGenderSelected(Gender.MALE) },
                modifier = Modifier.weight(1f)
            )

            SignUpUserSelectButton(
                user = "여아",
                isSelect = selectedGender == Gender.FEMALE,
                onClick = { onGenderSelected(Gender.FEMALE) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
private fun GenderSelectorPreview() {
    PawKeyTheme {
        GenderSelector(
            selectedGender = Gender.MALE,
            onGenderSelected = {},
            type = "성별"
        )
    }
}