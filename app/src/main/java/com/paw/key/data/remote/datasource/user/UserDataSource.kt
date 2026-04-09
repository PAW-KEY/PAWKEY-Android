package com.paw.key.data.remote.datasource.user

import com.paw.key.data.dto.request.user.UserInfoRequestDto
import com.paw.key.data.dto.request.user.UserLogOutRequestDto
import com.paw.key.data.dto.request.user.UserWithDrawRequestDto
import com.paw.key.data.service.user.UserService
import retrofit2.HttpException
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun createUser(dto: UserInfoRequestDto) = userService.createUser(dto)

    suspend fun getPetBreeds() = userService.getPetBreeds()

    suspend fun getPetProfiles(petId: Int) = userService.getPetProfiles(petId)

    suspend fun deleteUser(dto: UserWithDrawRequestDto) {
        val response = userService.deleteUser(dto)
        if (!response.isSuccessful) {
            throw Exception("회원탈퇴 실패: ${response.code()}")
        }
    }

    suspend fun logOutUser(deviceId: String) {
        val response = userService.logOutUser(
            body = UserLogOutRequestDto(
                deviceId = deviceId
            )
        )
        if (!response.isSuccessful) {
            throw Exception("로그아웃 실패: ${response.code()}")
        }
    }

    suspend fun getUserProfiles() = userService.getUserProfiles()

    suspend fun getNicknameDifference(nickname: String): Boolean {
        return try {
            val response = userService.getNicknameDifference(nickname)
            response.code == "U40901"
        } catch (e: Exception) {
            val errorBody = (e as? HttpException)?.response()?.errorBody()?.string()
            errorBody?.contains("U40901") == true
        }
    }
}
