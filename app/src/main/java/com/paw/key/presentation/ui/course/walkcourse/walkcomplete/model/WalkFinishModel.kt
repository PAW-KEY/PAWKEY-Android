package com.paw.key.presentation.ui.course.walkcourse.walkcomplete.model

import com.paw.key.domain.entity.walk.WalkFinishEntity
import com.paw.key.domain.entity.walk.WalkInfoEntity
import com.paw.key.domain.entity.walk.WalkPetProfileEntity

data class WalkFinishModel(
    val routeId: Int = -1,
    val petProfile: WalkCompletePetProfileModel = WalkCompletePetProfileModel(),
    val walkInfo: WalkCompleteInfoModel = WalkCompleteInfoModel()
)

fun WalkFinishEntity.toUiModel(): WalkFinishModel {
    return WalkFinishModel(
        routeId = routeId,
        petProfile = petProfile.toUiModel(),
        walkInfo = walkInfo.toUiModel()
    )
}


data class WalkCompletePetProfileModel(
    val petName: String = "",
    val petImage: String = "",
)

fun WalkPetProfileEntity.toUiModel(): WalkCompletePetProfileModel {
    return WalkCompletePetProfileModel(
        petName = petName,
        petImage = petProfileImageUrl
    )
}

data class WalkCompleteInfoModel(
    val startedAt: String = "",
)

fun WalkInfoEntity.toUiModel() : WalkCompleteInfoModel {
    return WalkCompleteInfoModel(
        startedAt = startAt
    )
}