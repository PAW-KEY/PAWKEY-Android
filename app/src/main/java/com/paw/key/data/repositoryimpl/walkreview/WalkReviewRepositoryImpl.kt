package com.paw.key.data.repositoryimpl.walkreview

import com.paw.key.data.remote.datasource.walkreview.WalkReviewDataSource
import com.paw.key.domain.model.entity.walkreview.WalkReviewCategoryListEntity
import com.paw.key.domain.model.entity.walkreview.WalkReviewIdEntity
import com.paw.key.domain.model.entity.walkreview.WalkReviewInfoEntity
import com.paw.key.domain.model.entity.walkreview.WalkReviewRecordEntity
import com.paw.key.domain.repository.walkreview.WalkReviewRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class WalkReviewRepositoryImpl @Inject constructor(
    private val dataSource: WalkReviewDataSource
) : WalkReviewRepository {
    override suspend fun postWalkReview(
        userId: Int,
        imageFiles: List<MultipartBody.Part>,
        walkReviewRequest: WalkReviewRecordEntity
    ): Result<WalkReviewIdEntity> {
        return runCatching {
            dataSource.postWalkReview(
                userId = userId,
                imageFiles = imageFiles,
                walkReviewRequestDto = walkReviewRequest.toDto()
            ).data.toEntity()
        }
    }

    override suspend fun getWalkReviewInfo(
        userId: Int,
        routeId: Int
    ): Result<WalkReviewInfoEntity> {
        return runCatching {
            dataSource.getWalkReviewInfo(
                userId = userId,
                routeId = routeId
            ).data.toEntity()
        }
    }

    override suspend fun getWalkReviewCategory(
        userId: Int
    ): Result<WalkReviewCategoryListEntity> {
        return runCatching {
            dataSource.getWalkReviewCategory(
                userId = userId
            ).data.toEntity()
        }
    }
}