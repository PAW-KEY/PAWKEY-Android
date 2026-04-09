package com.paw.key.presentation.ui.region.model

import androidx.compose.runtime.Immutable
import com.paw.key.domain.entity.signup.Gu

@Immutable
data class RegionGuModel(
    val id: Int,
    val name: String
)

fun Gu.toState() = RegionGuModel(
    id = id,
    name = name
)