package com.paw.key.presentation.ui.signup.model

import androidx.compose.runtime.Immutable
import com.paw.key.domain.entity.signup.DistrictEntity
import com.paw.key.domain.entity.signup.Dong
import com.paw.key.domain.entity.signup.Gu
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Immutable
data class SignUpLocationInfo(
    val regionList: ImmutableList<DistrictModel> = persistentListOf(),
    val selectedGu: GuModel = GuModel(0, ""),
    val selectedDong: DongModel = DongModel(0, ""),
)

@Immutable
data class DistrictModel(
    val gu: GuModel,
    val dongs: ImmutableList<DongModel> = persistentListOf()
)

@Immutable
data class GuModel(
    val id: Int,
    val name: String
)

@Immutable
data class DongModel(
    val id: Int,
    val name: String
)

fun Dong.toState() = DongModel(
    id = id,
    name = name
)

fun Gu.toState() = GuModel(
    id = id,
    name = name
)

fun DistrictEntity.toState() = DistrictModel(
    gu = gu.toState(),
    dongs = dongs.map { it.toState() }.toImmutableList()
)