package com.paw.key.domain.repository.walk

import com.paw.key.domain.entity.walk.WalkCompleteEntity
import com.paw.key.domain.entity.walk.WalkFinish
import com.paw.key.domain.entity.walk.WalkFinishEntity
import com.paw.key.domain.entity.walk.WalkPoint
import com.paw.key.domain.entity.walk.WalkStartEntity
import kotlinx.coroutines.flow.StateFlow

interface WalkRepository {
    suspend fun startWalk(
        deviceInfo: String?
    ) : Result<WalkStartEntity>

    suspend fun pointWalk(
        walkPoint: WalkPoint
    ) : Result<Unit>

    suspend fun finishWalk(
        routeId: String,
        walkFinish: WalkFinish
    ) : Result<WalkFinishEntity>

    suspend fun completeWalk(
        routeId: Int
    ) : Result<WalkCompleteEntity>

    val finishResult: StateFlow<WalkFinishEntity?>
    val finishWalkInfo: StateFlow<WalkFinish?>
}
