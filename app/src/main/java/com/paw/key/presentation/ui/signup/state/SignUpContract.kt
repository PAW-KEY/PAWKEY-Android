package com.paw.key.presentation.ui.signup.state

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.paw.key.data.dto.response.PetTraitCategoryDto

class SignUpContract {
    @Immutable
    data class SignUpState(
        val selectedGender: Gender = Gender.UNKNOWN,
        val selectedLocation: String = "",
        val isLocationMenuVisible: Boolean = false,
        val name: String = "",
        val age: String = "",
        val dogImage: Uri? = null,

        val dogName: String = "",
        val dogGender: DogGender = DogGender.UNKNOWN,
        val isNeutered: Boolean = false,
        val dogBreed: String = "",
        val ageKnown: AgeKnown = AgeKnown.NONE,
        val dogAge: String = "",

        val selectedDongId: Int = 0,
        val selectedGuId: Int = 0,

        val selectedEnergyLevel: String = "",
        val selectedSocialLevel: String = "",

        val selectedDistrict: List<String> = emptyList(),
        val isDistrictMenuVisible: Boolean = false,

        val petTraitCategoryList: List<PetTraitCategoryDto> = emptyList(),

        val selectedGu: String = "",
        val selectedDong: String = "",

        )

    enum class Gender {
        MALE,
        FEMALE,
        UNKNOWN
    }

    enum class DogGender {
        MALE,
        FEMALE,
        UNKNOWN
    }

    enum class AgeKnown {
        NONE,
        KNOWN,
        UNKNOWN
    }

    sealed class SignUpSideEffect {
        data class ShowSnackBar(val message: String) : SignUpSideEffect()
        data object NavigateUp: SignUpSideEffect()
        data object NavigateNext: SignUpSideEffect()
    }
}
