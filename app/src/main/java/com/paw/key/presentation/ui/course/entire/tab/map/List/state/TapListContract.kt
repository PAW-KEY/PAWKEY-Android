package com.paw.key.presentation.ui.course.entire.tab.map.List.state

import androidx.compose.runtime.Immutable

class TapListContract {
    @Immutable
    data class TapListState(
        val isSelectItem: Boolean = false,
        val selectedSortOption: String = "최신순",

        val selectedWalkTime: String = "",
        val selectedMood: String = "",
        val selectedDogFriend: String = "",

        val selectedSafety: List<String> = emptyList(),
        val selectedConvenience: List<String> = emptyList(),
        val selectedEnvironment: List<String> = emptyList(),

        val isWalkTimeExpanded: Boolean = false,
        val isMoodExpanded: Boolean = false,
        val isDogFriendExpanded: Boolean = false,
        val isSafetyExpanded: Boolean = false,
        val isConvenienceExpanded: Boolean = false,
        val isEnvironmentExpanded: Boolean = false,
    )

    object Options {
        val sortOptions = listOf("최신순", "인기순")
        val walkTimeOptions = listOf("20분 이내", "21-40분", "41-60분", "1시간 이상")
        val moodOptions = listOf("조용한 분위기", "적당한 유동인구", "유동인구 많음")
        val dogFriendOptions = listOf("친구 많은 길", "가끔 마주침", "단독 산책 희망")
        val safetyOptions = listOf("차량 거의 없음", "야간에도 밝음", "넓은 보도", "킥보드/자전거 거의 없음", "보도/차도 구분됨")
        val convenienceOptions = listOf("벤치", "편의점", "배변 봉투 쓰레기통", "반려견 동반 카페")
        val environmentOptions = listOf("풀 많은 길 위주", "흙길 위주", "아스팔트/벽돌길 위주", "뛰어놀 공간")
    }
}