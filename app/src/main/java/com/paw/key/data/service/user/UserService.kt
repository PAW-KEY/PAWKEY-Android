package com.paw.key.data.service.user

import com.paw.key.data.dto.request.user.UserInfoRequestDto
import com.paw.key.data.dto.request.user.UserWithDrawRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.petprofile.PetProfileResponseDto
import com.paw.key.data.dto.response.user.PetBreedsResponseDto
import com.paw.key.data.dto.response.user.UserInfoResponseDto
import com.paw.key.data.dto.response.userprofile.UserProfileResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @POST("users")
    suspend fun createUser(
        @Body body: UserInfoRequestDto
    ): BaseResponse<UserInfoResponseDto>

    @HTTP(method = "DELETE", path = "auth/withdraw", hasBody = true)
    suspend fun deleteUser(
        @Body request: UserWithDrawRequestDto
    ) : BaseResponse<Unit>

    @GET("pets/breeds")
    suspend fun getPetBreeds(): BaseResponse<PetBreedsResponseDto>

    @GET("pets/{petId}")
    suspend fun getPetProfiles(
        @Path("petId") petId: Int
    ): BaseResponse<PetProfileResponseDto>

    @GET("users/me/userInfo")
    suspend fun getUserProfiles(): BaseResponse<UserProfileResponseDto>
}
