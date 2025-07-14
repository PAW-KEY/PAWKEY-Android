package com.paw.key.data.service


import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.OnboardingPetsResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface OnboardingPetsService {
    @GET("pets/traits/categories")
    suspend fun getPetsCaegories(
        @Header("X-USER-ID") userId: Int,
    ): OnboardingPetsResponse
}