package com.paw.key.domain.entity.signup


data class DistrictEntity(
    val gu: Gu,
    val dongs: List<Dong>
)

data class Gu(
    val id: Int,
    val name: String
)

data class Dong(
    val id: Int,
    val name: String
)