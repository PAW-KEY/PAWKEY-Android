package com.paw.key.presentation.ui.mypage.courseinfo.model

enum class CourseType(
    val courseType: String,
) {
    MyCourse(courseType = "내가 기록한 산책"),
    AllCourse(courseType = "저장 목록"),
    ReviewCourse(courseType = "내가 남긴 후기")
}


data class CourseData(
    val location: String,
    val title: String,
    val imageUrl: String,
    val distance: String,
    val time: String,
    val date: String,
)