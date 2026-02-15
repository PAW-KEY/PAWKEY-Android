package com.paw.key.presentation.ui.course.walkreview.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class WalkReviewFilterModel(
    // Todo : 서버 내용으로 변경
    val confusionSingleFilterList: ImmutableList<String> = persistentListOf("적음", "평범", "많음"),
    val frequencySingleFilterList: ImmutableList<String> = persistentListOf("교류 없음", "보통", "교류 활발"),

    val safetyMultipleFilterList: ImmutableList<String> = persistentListOf(
        "차량 적음",
        "보도/차도 분리",
        "보도 넓음",
        "킥보드/자전거 적음 ",
        "야간 밝음"
    ),
    val comfortMultipleFilterList: ImmutableList<String> = persistentListOf(
        "벤치",
        "배변 봉투 쓰레기통",
        "편의점",
        "반려견 동반 카페"
    ),
    val environmentMultipleFilterList: ImmutableList<String> = persistentListOf(
        "잔디길",
        "흙길",
        "포장길",
        "놀이터/공터"
    )
)
