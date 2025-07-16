package com.paw.key.data.service.list

import com.paw.key.data.dto.request.list.PostsListRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.list.PostsListResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PostsListService {
    @POST("posts/filter")
    suspend fun postList(
        @Header("X-USER-ID") userId: Int,
        @Body request: PostsListRequestDto
    ): BaseResponse<PostsListResponseDto>
}