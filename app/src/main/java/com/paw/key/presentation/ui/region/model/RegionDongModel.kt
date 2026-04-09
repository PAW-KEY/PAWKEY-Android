package com.paw.key.presentation.ui.region.model

import androidx.compose.runtime.Immutable
import com.paw.key.domain.entity.signup.Dong

@Immutable
data class RegionDongModel(
    val id: Int,
    val name: String
)

fun Dong.toState() = RegionDongModel(
    id = id,
    name = name
)
