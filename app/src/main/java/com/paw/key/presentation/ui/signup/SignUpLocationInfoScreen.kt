package com.paw.key.presentation.ui.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawKeyBottomSheet
import com.paw.key.core.extension.noRippleClickable
import com.paw.key.presentation.ui.signup.component.FormField
import com.paw.key.presentation.ui.signup.component.RegionSearchContent
import com.paw.key.presentation.ui.signup.component.SignUpTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpLocationInfoScreen(
    gu : String,
    dong : String,
    onSelectedLocation : (gu : String, dong : String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSheetOpen by remember { mutableStateOf(false) }

    Column (
        modifier = modifier
            .padding(
                top = 40.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        FormField(
            label = "선택 지역",
            content = {
                SignUpTextField(
                    value = if (gu.isNotEmpty() && dong.isNotEmpty()) "$gu $dong" else "",
                    onValueChange = {},
                    enabled = false,
                    placeholder = "주로 산책하는 지역을 검색해보세요",
                    suffix = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_signup_search),
                            contentDescription = "breed search",
                            tint = Color.Unspecified
                        )
                    },
                    modifier = Modifier
                        .noRippleClickable {
                            scope.launch {
                                isSheetOpen = true
                            }
                        }
                )
            }
        )
    }

    if (isSheetOpen) {
        PawKeyBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { isSheetOpen = false },
        ) {
            RegionSearchContent(
                selectedGu = gu,
                selectedDong = dong,
                onRegionSelected = { gu, dong ->
                    isSheetOpen = false
                    onSelectedLocation(gu, dong)
                }
            )
        }
    }
}