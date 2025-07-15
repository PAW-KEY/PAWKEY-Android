package com.paw.key.data.repositoryimpl

import com.paw.key.data.dto.request.walkcourse.WalkCourseRequestDto
import com.paw.key.data.remote.datasource.WalkCourseDataSource
import com.paw.key.domain.model.entity.walkcourse.WalkCourseRegionIdEntity
import com.paw.key.domain.repository.walkcourse.WalkCourseRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class WalkCourseRepositoryImpl @Inject constructor(
    private val walkCourseDataSource: WalkCourseDataSource
) : WalkCourseRepository {
    override suspend fun postWalkCourse(
        userId: Int,
        image: MultipartBody.Part,
        routeRequestDto: WalkCourseRequestDto
    ): Result<WalkCourseRegionIdEntity> = runCatching {
        walkCourseDataSource.postWalkCourse(userId, image, routeRequestDto)
            .data.toEntity()
    }
}
