package com.paw.key.data.service.user

import com.paw.key.data.dto.request.user.UserInfoRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.user.PetBreedsResponseDto
import com.paw.key.data.dto.response.user.UserInfoResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("users")
    suspend fun createUser(
        @Body body: UserInfoRequestDto
    ): BaseResponse<UserInfoResponseDto>

    @GET("pets/breeds")
    suspend fun getPetBreeds(): BaseResponse<PetBreedsResponseDto>

}