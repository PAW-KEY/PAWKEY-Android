
package com.paw.key.presentation.ui.home.state

import androidx.compose.runtime.Immutable
import com.paw.key.core.util.PreferenceDataStore
import com.paw.key.domain.model.entity.archivedlist.ArchivedListEntity
import com.paw.key.domain.model.entity.list.ListEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeContract {

    @Immutable
    data class HomeState(
        val isLocationMenuVisible: Boolean = false,
        val postsResult: ListEntity? = null,
        val selectedLocation: LocationInfo = LocationInfo(),
        val courseList: List<ArchivedListEntity> = emptyList(),
        val uiState: HomeUiState = HomeUiState(),
        val currentRegion: CurrentRegionInfo = CurrentRegionInfo()
    )

    @Immutable
    data class CurrentRegionInfo(
        val currentId: Int = 0,
        val currentName: String = ""
    )

    @Immutable
    data class LocationInfo(
        val selectedGuId: Int = 0,
        val selectedDongId: Int = 0,
        val selectedGu: String = "",
        val selectedDong: String = ""
    ) {
        val displayLocation: String
            get() = when {
                // 구/동이 모두 선택된 경우
                selectedGu.isNotEmpty() && selectedDong.isNotEmpty() -> {
                    "$selectedGu $selectedDong"
                }
                // 구만 선택된 경우
                selectedGu.isNotEmpty() -> {
                    selectedGu
                }
                // 아무것도 선택되지 않은 경우 - activeRegion 사용
                else -> {
                    try {
                        // 코루틴 블로킹 호출 (UI에서는 이미 로드된 상태여야 함)
                        runBlocking {
                            val activeRegion = PreferenceDataStore.getActiveRegion().first()
                            activeRegion.ifEmpty { "위치를 선택해주세요" }
                        }
                    } catch (e: Exception) {
                        "위치를 선택해주세요"
                    }
                }
            }
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