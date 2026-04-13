package com.paw.key.data.service.mypage

import com.paw.key.data.dto.request.mypage.UpdatePetRequestDto
import com.paw.key.data.dto.request.mypage.UpdateUserRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.mypage.ReviewPostListResponseDto
import com.paw.key.data.dto.response.mypage.RoutePostListResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface MypageService {

    @PATCH("users/me")
    suspend fun updateUser(
        @Body body: UpdateUserRequestDto,
    ): BaseResponse<Unit>

    @PATCH("pets/me")
    suspend fun updatePet(
        @Body body: UpdatePetRequestDto,
    ): BaseResponse<Unit>

    @GET("users/me/posts")
    suspend fun getMyRoutes(): BaseResponse<RoutePostListResponseDto>

    @GET("users/me/likes")
    suspend fun getLikedPosts(): BaseResponse<RoutePostListResponseDto>

    @GET("users/me/reviews")
    suspend fun getMyReviews(): BaseResponse<ReviewPostListResponseDto>
}