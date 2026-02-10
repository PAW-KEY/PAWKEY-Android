package com.paw.key.presentation.ui.mypage.courseinfo.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.paw.key.presentation.ui.mypage.courseinfo.model.CourseType
import com.paw.key.presentation.ui.mypage.courseinfo.navigation.CourseInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CourseInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args = savedStateHandle.toRoute<CourseInfo>()

    val courseType: CourseType = CourseType.valueOf(args.courseType)

    init {
        fetchCourseData()
    }

    private fun fetchCourseData() {
        when (courseType) {
            CourseType.MyCourse -> { /* 내가 기록한 산책 로드 */
            }

            CourseType.AllCourse -> { /* 저장 목록 로드 */
            }

            CourseType.ReviewCourse -> { /* 후기 로드 */
            }
        }
    }
}