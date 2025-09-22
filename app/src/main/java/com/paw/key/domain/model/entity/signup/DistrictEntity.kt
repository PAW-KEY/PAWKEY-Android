package com.paw.key.domain.model.entity.signup

import DistrictDto
import DongDto
import GuDto

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

fun DistrictDto.toEntity(): DistrictEntity {
    return DistrictEntity(
        gu = gu.toEntity(),
        dongs = dongs.map { it.toEntity() }
    )
}

fun GuDto.toEntity(): Gu {
    return Gu(
        id = id,
        name = name
    )
}

fun DongDto.toEntity(): Dong {
    return Dong(
        id = id,
        name = name
    )
}