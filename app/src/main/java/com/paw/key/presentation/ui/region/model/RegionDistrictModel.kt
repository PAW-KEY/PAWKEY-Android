package com.paw.key.presentation.ui.region.model

import androidx.compose.runtime.Immutable
import com.paw.key.domain.entity.signup.DistrictEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Immutable
data class RegionDistrictModel(
    val gu: RegionGuModel,
    val dongs: ImmutableList<RegionDongModel> = persistentListOf()
)

fun DistrictEntity.toState() = RegionDistrictModel(
    gu = gu.toState(),
    dongs = dongs.map { it.toState() }.toImmutableList()
)