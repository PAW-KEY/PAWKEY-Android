package com.paw.key.domain.repository.walkreview

import com.paw.key.domain.entity.walkreview.WalkReviewCategoryListEntity
import com.paw.key.domain.entity.walkreview.WalkReviewIdEntity
import com.paw.key.domain.entity.walkreview.WalkReviewInfoEntity
import com.paw.key.domain.entity.walkreview.WalkReviewRecordEntity
import okhttp3.MultipartBody

interface WalkReviewRepository {
    suspend fun postWalkReview(
        userId: Int,
        imageFiles: List<MultipartBody.Part>,
        walkReviewRequest: WalkReviewRecordEntity
    ) : Result<WalkReviewIdEntity>

    suspend fun getWalkReviewInfo(
        userId: Int,
        routeId: Int
    ) : Result<WalkReviewInfoEntity>

    suspend fun getWalkReviewCategory(
        userId: Int
    ) : Result<WalkReviewCategoryListEntity>
}