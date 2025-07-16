package com.paw.key.domain.model.entity.uerprofile

data class UserProfileEntity(
    val userId: Int,
    val loginId: String,
    val name: String,
    val gender: String,
    val age: Int,
    val region: String
)