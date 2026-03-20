package com.paw.key.presentation.ui.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.DateVisualTransformation
import com.paw.key.presentation.ui.signup.component.FormField
import com.paw.key.presentation.ui.signup.component.GenderSelector
import com.paw.key.presentation.ui.signup.component.SignUpTextField
import com.paw.key.presentation.ui.signup.state.Gender

@Composable
fun SignUpUserInfoScreen(
    nickName: String,
    birthDate: String,
    gender: Gender,
    isDuplicate: Boolean,
    onNickNameChanged: (String) -> Unit,
    onBirthDateChanged: (String) -> Unit,
    onGenderChanged: (Gender) -> Unit,
    modifier: Modifier = Modifier,
) {
    val birthDateFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column (
        modifier = modifier
            .padding(16.dp)
    ) {
        FormField(
            label = "닉네임",
            isDuplicate = isDuplicate,
            content = {
                SignUpTextField(
                    value = nickName,
                    onValueChange = {
                        if (it.length <= 8) {
                            onNickNameChanged(it)
                        }
                    },
                    placeholder = "최대 8글자 이내로 입력해주세요",
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            birthDateFocusRequester.requestFocus()
                        }
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormField(
            label = "생년월일",
            content = {
                SignUpTextField(
                    modifier = Modifier
                        .focusRequester(birthDateFocusRequester),
                    value = birthDate,
                    onValueChange = {
                        if (it.length <= 8) {
                            onBirthDateChanged(it)
                        }
                    },
                    placeholder = "YYYYMMDD",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    visualTransformation = DateVisualTransformation()
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormField(
            label = "성별",
            content = {
                GenderSelector(
                    selectedGender = gender,
                    onGenderSelected = onGenderChanged
                )
            }
        )
    }
}

@Preview
@Composable
private fun SignUpUserInfoScreenPreview() {
    PawKeyTheme {
        SignUpUserInfoScreen(
            nickName = "",
            birthDate = "",
            gender = Gender.MALE,
            isDuplicate = true,
            onNickNameChanged = {},
            onBirthDateChanged = {},
            onGenderChanged = {}
        )
    }
}
