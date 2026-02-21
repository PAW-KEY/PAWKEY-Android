package com.paw.key.domain.entity.user

data class UserInfoEntity(
    val name: String,
    val birth: String,
    val gender: String,
    val dongId: Int,
    val pet: PetInfoEntity
)

data class UserInfoResultEntity(
    val userId: Int,
    val petId: Int,
)
