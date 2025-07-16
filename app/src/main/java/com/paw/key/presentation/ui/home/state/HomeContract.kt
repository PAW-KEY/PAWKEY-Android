
package com.paw.key.presentation.ui.home.state

import androidx.compose.runtime.Immutable
import com.paw.key.domain.model.entity.archivedlist.ArchivedListEntity
import com.paw.key.domain.model.entity.list.ListEntity

class HomeContract {

    @Immutable
    data class HomeState(
        val isLocationMenuVisible: Boolean = false,
        val postsResult: ListEntity? = null,
        val selectedLocation: LocationInfo = LocationInfo(),
        val courseList: List<ArchivedListEntity> = emptyList(),
        val uiState: HomeUiState = HomeUiState(),
        val selectedGuId: Int = 0,
        val selectedDongId: Int = 0,
        val selectedGu: String = "",
        val selectedDong: String = ""
    )

    @Immutable
    data class LocationInfo(
        val selectedGuId: Int = 0,
        val selectedDongId: Int = 0,
        val selectedGu: String = "",
        val selectedDong: String = ""
    ) {
        val displayLocation: String
            get() = if (selectedGu.isNotEmpty() && selectedDong.isNotEmpty()) {
                "$selectedGu $selectedDong"
            } else if (selectedGu.isNotEmpty()) {
                selectedGu
            } else {
                "위치를 선택해주세요"
            }

        val isLocationSelected: Boolean
            get() = selectedGuId != 0 && selectedDongId != 0
    }

    @Immutable
    data class HomeUiState(
        val isVisible: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    // 액션들을 정의하는 sealed class
    sealed class HomeAction {
        object ToggleLocationMenu : HomeAction()
        data class SelectGu(val guName: String, val guId: Int) : HomeAction()
        data class SelectDong(val dongName: String, val dongId: Int) : HomeAction()
        data class ToggleLike(val postId: Int, val isLiked: Boolean) : HomeAction()
        object RefreshPosts : HomeAction()
        object ClearError : HomeAction()
    }

    // 사이드 이펙트를 정의하는 sealed class
    sealed class HomeSideEffect {
        object NavigateToLocationSetting : HomeSideEffect()
        data class ShowError(val message: String) : HomeSideEffect()
        data class ShowToast(val message: String) : HomeSideEffect()
    }
}