package com.paw.key.data.service

import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.petprofile.PetProfileResponseDto
import com.paw.key.data.dto.response.userprofile.UserProfileResponseDto
import retrofit2.http.GET
import retrofit2.http.Header

interface UserProfileService {
    @GET("users/me/userInfo")
    suspend fun getUserProfiles(
        @Header("X-USER-ID") userId: Int
    ): BaseResponse<UserProfileResponseDto>
}