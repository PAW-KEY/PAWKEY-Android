package com.paw.key.presentation.ui.home.model

import com.paw.key.domain.entity.home.HomeWeatherEntity

data class HomeWeatherModel(
    val temperature : Int = -1,
    val rainyMm : Int = -1,
    val region : String = ""
)

fun HomeWeatherEntity.toUiModel() = HomeWeatherModel(
    temperature = temperature,
    rainyMm = rainyMm,
    region = region
)