package com.paw.key.domain.repository.localstorage

interface LocalStorageRepository {
    // 토큰 관련
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
    suspend fun removeTokens()

    // 사용자 정보 관련
    suspend fun saveUserId(userId: Int)
    suspend fun getUserId(): Int
    suspend fun saveUserProvider(provider: String)
    suspend fun getUserProvider(): String
    suspend fun saveUserRegionId(regionId: Int)
    suspend fun getUserRegionId(): Int

    // 펫 정보 관련
    suspend fun savePetId(petId: Int)
    suspend fun getPetId(): Int
    suspend fun savePetName(petName: String)
    suspend fun getPetName(): String


    // 기기 정보 관련
    suspend fun saveDeviceId(deviceId: String)
    suspend fun getDeviceId(): String

    // 전체 초기화 (로그아웃/탈퇴 시)
    suspend fun clearInfo()
}
