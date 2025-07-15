package com.paw.key.data.remote.datasource

import com.paw.key.data.dto.request.walkcourse.WalkCourseRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.walkcourse.WalkCourseResponseDto
import com.paw.key.data.service.walkcourse.WalkCourseService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class WalkCourseDataSource @Inject constructor(
    private val walkCourseService: WalkCourseService
) {
    suspend fun postWalkCourse(
        userId: Int,
        file: MultipartBody.Part,
        walkCourseRequestDto: WalkCourseRequestDto
    ): BaseResponse<WalkCourseResponseDto> {
        val jsonString = Json.encodeToString(WalkCourseRequestDto.serializer(), walkCourseRequestDto)
        val requestBody = jsonString.toRequestBody("application/json".toMediaType())

        return walkCourseService.postWalkCourse(userId, file, requestBody)
    }
}