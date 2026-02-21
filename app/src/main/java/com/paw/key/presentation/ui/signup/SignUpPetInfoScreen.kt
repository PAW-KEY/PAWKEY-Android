package com.paw.key.presentation.ui.signup

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.paw.key.R
import com.paw.key.core.designsystem.component.PawKeyBottomSheet
import com.paw.key.core.extension.noRippleClickable
import com.paw.key.core.util.DateVisualTransformation
import com.paw.key.presentation.ui.signup.component.FormField
import com.paw.key.presentation.ui.signup.component.GenderSelector
import com.paw.key.presentation.ui.signup.component.PetBreedSearchContent
import com.paw.key.presentation.ui.signup.component.SignUpNeuteringCheckRadio
import com.paw.key.presentation.ui.signup.component.SignUpPetImageHolder
import com.paw.key.presentation.ui.signup.component.SignUpTextField
import com.paw.key.presentation.ui.signup.model.PetInfoItemModel
import com.paw.key.presentation.ui.signup.model.SignUpPetInfo
import com.paw.key.presentation.ui.signup.state.Gender
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPetInfoScreen(
    petInfo: SignUpPetInfo,
    petBreedList: ImmutableList<PetInfoItemModel>,
    requestPetInfo : () -> Unit,
    deniedPermission: () -> Unit,
    onPetNameChanged : (String) -> Unit,
    onPetBirthDateChanged : (String) -> Unit,
    onPetGenderChanged : (Gender) -> Unit,
    onPetNeuteredChanged : (Boolean) -> Unit,
    onPetBreedChanged : (PetInfoItemModel) -> Unit,
    onSelectedImage: (Uri?) -> Unit,
    modifier: Modifier = Modifier
) {
    var isSheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val petBirthDateFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            onSelectedImage(uri)
        }
    )

    // 구버전 권한 요청용
    val legacyGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            onSelectedImage(uri)
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                legacyGalleryLauncher.launch("image/*")
            } else {
                deniedPermission
            }
        }
    )

    if (petBreedList.isEmpty()) {
        LaunchedEffect(Unit) {
            requestPetInfo()
        }
    }

    Column(
        modifier = modifier
            .padding(
                top = 40.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        SignUpPetImageHolder(
            uri = petInfo.petImage,
            modifier = Modifier
                .noRippleClickable {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    } else {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
        )

        Spacer(modifier = Modifier.height(20.dp))

        FormField(
            label = "이름",
            content = {
                SignUpTextField(
                    value = petInfo.petName,
                    onValueChange = {
                        if (it.length <= 8) {
                            onPetNameChanged(it)
                        }
                    },
                    placeholder = "최대 8글자 이내로 입력해주세요",
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            petBirthDateFocusRequester.requestFocus()
                        }
                    ),
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormField(
            label = "생년월일",
            content = {
                SignUpTextField(
                    modifier = Modifier
                        .focusRequester(petBirthDateFocusRequester),
                    value = petInfo.petBirthDate,
                    onValueChange = {
                        if (it.length <= 8) {
                            onPetBirthDateChanged(it)
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
                    selectedGender = petInfo.petGender,
                    onGenderSelected = onPetGenderChanged,
                    type = "반려 동물"
                )
            }
        )

        SignUpNeuteringCheckRadio(
            isNeutered = petInfo.petNeutered,
            onToggle = { onPetNeuteredChanged(!petInfo.petNeutered) },
            modifier = Modifier
                .padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormField(
            label = "견종",
            content = {
                SignUpTextField(
                    value = petInfo.petBreed.name,
                    onValueChange = {},
                    enabled = false,
                    placeholder = "견종을 검색해보세요",
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

        Spacer(modifier = Modifier.height(24.dp))

        if (isSheetOpen) {
            PawKeyBottomSheet(
                onDismissRequest = { isSheetOpen = false },
                sheetState = sheetState,
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures {
                        }
                    }
            ) { sheetState ->
                PetBreedSearchContent(
                    petBreedList = petBreedList,
                    sheetState = sheetState,
                    selectedBreed = petInfo.petBreed.name,
                    onBreedSelected = { selectedModel: PetInfoItemModel ->
                        onPetBreedChanged(selectedModel)
                        scope.launch {

                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                isSheetOpen = false
                            }
                        }
                    },
                )
            }
        }
    }
}