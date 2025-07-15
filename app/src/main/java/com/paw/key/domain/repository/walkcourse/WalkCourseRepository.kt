package com.paw.key.domain.repository.walkcourse

import com.paw.key.data.dto.request.walkcourse.WalkCourseRequestDto
import com.paw.key.domain.model.entity.walkcourse.WalkCourseRegionIdEntity
import okhttp3.MultipartBody

interface WalkCourseRepository {
    suspend fun postWalkCourse(
        userId: Int,
        image: MultipartBody.Part,
        routeRequestDto: WalkCourseRequestDto
    ): Result<WalkCourseRegionIdEntity>
}