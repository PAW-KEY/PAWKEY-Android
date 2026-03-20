package com.paw.key.data.remote.datasource.user

import com.paw.key.core.util.suspendRunCatching
import com.paw.key.data.dto.request.user.UserInfoRequestDto
import com.paw.key.data.dto.request.user.UserWithDrawRequestDto
import com.paw.key.data.service.user.UserService
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

    suspend fun getUserProfiles() = userService.getUserProfiles()

    suspend fun getNicknameDifference(nickname: String): Boolean = suspendRunCatching {
        userService.getNicknameDifference(nickname).code == "U40901"
    }.getOrDefault(false)
}
