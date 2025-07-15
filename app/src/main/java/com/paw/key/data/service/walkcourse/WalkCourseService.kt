package com.paw.key.data.service.walkcourse

import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.walkcourse.WalkCourseResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface WalkCourseService {
    @Multipart
    @POST("routes")
    suspend fun postWalkCourse(
        @Header("X-USER-ID") userId: Int,
        @Part trackingImage: MultipartBody.Part,
        @Part("routeRequest") routeRequest: RequestBody
    ): BaseResponse<WalkCourseResponseDto>
}