package com.paw.key.data.remote.datasource.mypage

import com.paw.key.data.dto.request.mypage.UpdatePetRequestDto
import com.paw.key.data.dto.request.mypage.UpdateUserRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.mypage.ReviewPostListResponseDto
import com.paw.key.data.dto.response.mypage.RoutePostListResponseDto

interface MypageDataSource {
    suspend fun updateUser(body: UpdateUserRequestDto): BaseResponse<Unit>
    suspend fun updatePet(body: UpdatePetRequestDto): BaseResponse<Unit>
    suspend fun getMyRoutes(): BaseResponse<RoutePostListResponseDto>
    suspend fun getLikedPosts(): BaseResponse<RoutePostListResponseDto>
    suspend fun getMyReviews(): BaseResponse<ReviewPostListResponseDto>
}