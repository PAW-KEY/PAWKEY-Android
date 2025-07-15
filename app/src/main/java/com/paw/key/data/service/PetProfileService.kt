package com.paw.key.data.service

import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.petprofile.PetProfileResponseDto
import retrofit2.http.GET
import retrofit2.http.Header

interface PetProfileService {
    @GET("users/me/pets")
    suspend fun getPetProfiles(
        @Header("X-USER-ID") userId: Int
    ): BaseResponse<List<PetProfileResponseDto>>
}