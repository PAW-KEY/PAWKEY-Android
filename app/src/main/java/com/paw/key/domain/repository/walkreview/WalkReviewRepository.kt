package com.paw.key.domain.repository.walkreview

import com.paw.key.data.dto.request.walkreview.WalkCourseReviewRequestDto
import com.paw.key.data.dto.response.walkcourse.WalkCourseResponseDto
import com.paw.key.domain.model.entity.walkreview.WalkReviewCategoryListEntity
import com.paw.key.domain.model.entity.walkreview.WalkReviewInfoEntity
import com.paw.key.domain.model.entity.walkreview.WalkReviewRecordEntity
import okhttp3.MultipartBody

interface WalkReviewRepository {
    suspend fun postWalkReview(
        userId: Int,
        imageFiles: List<MultipartBody.Part>,
        walkReviewRequest: WalkReviewRecordEntity
    ) : Result<Unit>

    suspend fun getWalkReviewInfo(
        userId: Int,
        routeId: Int
    ) : Result<WalkReviewInfoEntity>

    suspend fun getWalkReviewCategory(
        userId: Int
    ) : Result<WalkReviewCategoryListEntity>
}