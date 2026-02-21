package com.paw.key.domain.entity.walkreview

data class WalkReviewInfoEntity(
    val routeDto : WalkReviewRouteInfoEntity,
    val petName : String,
)

data class WalkReviewRouteInfoEntity(
    val id : Int,
    val locationDescription : String,
    val dateDescription : String,
    val descriptionTags : List<String>,
)