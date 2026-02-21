package com.paw.key.presentation.ui.community.model

enum class SortedType(
    val label: String,
) {
    // Todo : 서버 내용으로 수정
    LATEST("최신순"),
    POPULARITY("인기순"),
    DISTANCE("거리순"),
    TIME("시간순"),
}