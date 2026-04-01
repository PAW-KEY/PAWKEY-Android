package com.paw.key.data.repositoryimpl.walk

import com.paw.key.core.util.suspendRunCatching
import com.paw.key.data.dto.request.walk.WalkStartRequestDto
import com.paw.key.data.dto.request.walk.toDto
import com.paw.key.data.remote.datasource.walk.WalkDataSource
import com.paw.key.domain.entity.walk.WalkCompleteEntity
import com.paw.key.domain.entity.walk.WalkFinish
import com.paw.key.domain.entity.walk.WalkFinishEntity
import com.paw.key.domain.entity.walk.WalkPoint
import com.paw.key.domain.entity.walk.WalkStartEntity
import com.paw.key.domain.repository.walk.WalkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class WalkRepositoryImpl @Inject constructor(
    private val dataSource: WalkDataSource,
) : WalkRepository {
    private val _finishResult = MutableStateFlow<WalkFinishEntity?>(null)
    override val finishResult =_finishResult.asStateFlow()

    private val _finishWalkInfo = MutableStateFlow<WalkFinish?>(null)
    override val finishWalkInfo = _finishWalkInfo.asStateFlow()

    override suspend fun startWalk(deviceInfo: String?): Result<WalkStartEntity> =
        suspendRunCatching {
            dataSource.startWalk(
                dto = WalkStartRequestDto(deviceInfo = "ANDROID")
            ).data.toEntity()
        }

    override suspend fun pointWalk(walkPoint: WalkPoint): Result<Unit> =
        suspendRunCatching {
            dataSource.pointWalk(
                dto = walkPoint.toDto()
            ).data
        }

    override suspend fun finishWalk(
        routeId: String,
        walkFinish: WalkFinish
    ): Result<WalkFinishEntity> = suspendRunCatching {
        val result = dataSource.finishWalk(
            routeId = routeId,
            dto = walkFinish.toDto()
        ).data.toEntity()

        _finishWalkInfo.value = walkFinish
        _finishResult.value = result

        result
    }

    override suspend fun completeWalk(routeId: String): Result<WalkCompleteEntity> =
        suspendRunCatching {
            dataSource.completeWalk(
                routeId = routeId
            ).data.toEntity()
        }
}