package com.paw.key.data.remote.datasource.walkreview

import com.paw.key.data.dto.request.walkcourse.WalkCourseRequestDto
import com.paw.key.data.dto.request.walkreview.WalkCourseReviewRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.service.walkreview.WalkReviewService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class WalkReviewDataSource @Inject constructor(
    private val service: WalkReviewService
) {
    suspend fun postWalkReview(
        userId: Int,
        imageFiles: List<MultipartBody.Part>,
        walkReviewRequestDto: WalkCourseReviewRequestDto
    ) : BaseResponse<Unit> {
        val jsonString = Json.encodeToString(WalkCourseReviewRequestDto.serializer(), walkReviewRequestDto)
        val requestBody = jsonString.toRequestBody("application/json".toMediaType())

        return service.postWalkReview(
            userId = userId,
            imageFiles = imageFiles,
            data = requestBody
        )
    }

    suspend fun getWalkReviewInfo(
        userId: Int,
        routeId: Int
    ) = service.getWalkReviewInfo(
        userId = userId,
        routeId = routeId
    )

    suspend fun getWalkReviewCategory(
        userId: Int
    ) = service.getWalkReviewCategory(
        userId = userId
    )
}