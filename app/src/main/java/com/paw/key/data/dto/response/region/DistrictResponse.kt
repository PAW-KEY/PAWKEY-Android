package com.paw.key.data.dto.response.region

import com.paw.key.domain.entity.signup.DistrictEntity
import com.paw.key.domain.entity.signup.Dong
import com.paw.key.domain.entity.signup.Gu
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DistrictDataDto(
    @SerialName("districtDtos")
    val districtDtos: List<DistrictDto>
)

@Serializable
data class DistrictDto(
    @SerialName("gu")
    val gu: GuDto,

    @SerialName("dongs")
    val dongs: List<DongDto>
)

@Serializable
data class GuDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String
)

@Serializable
data class DongDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
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