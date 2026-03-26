package com.paw.key.domain.entity.walk

data class WalkFinishEntity(
    val routeId: Int,
    val petProfile: WalkPetProfileEntity,
    val walkInfo: WalkInfoEntity
)

data class WalkPetProfileEntity(
    val petName: String,
    val petProfileImageUrl: String
)

data class WalkInfoEntity(
    val startAt: String
)

data class WalkFinish(
    val distance: Int,
    val duration: Int,
    val stepCount: Int,
    val endedAt: String
)
