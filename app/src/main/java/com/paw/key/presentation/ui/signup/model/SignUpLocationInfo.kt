package com.paw.key.presentation.ui.signup.model

import androidx.compose.runtime.Immutable

@Immutable
data class SignUpLocationInfo( // 바텀시트 지역 선택 시 보여주기 위함
    val selectedGu: String = "",
    val selectedDong: String = "",
)