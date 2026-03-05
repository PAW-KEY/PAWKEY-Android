package com.paw.key.data.remote.datasource.walk

import com.paw.key.data.dto.request.walk.WalkFinishRequestDto
import com.paw.key.data.dto.request.walk.WalkPointRequestDto
import com.paw.key.data.dto.request.walk.WalkStartRequestDto
import com.paw.key.data.service.walk.WalkService
import javax.inject.Inject

class WalkDataSource @Inject constructor(
    private val walkService: WalkService
) {
    suspend fun startWalk(
        dto : WalkStartRequestDto
    ) = walkService.startWalk(
        body = dto
    )

    suspend fun pointWalk(
        dto : WalkPointRequestDto
    ) = walkService.pointWalk(
        body = dto
    )

    suspend fun finishWalk(
        routeId : String,
        dto : WalkFinishRequestDto
    ) = walkService.finishWalk(
        body = dto,
        routeId = routeId
    )

    suspend fun completeWalk(
        routeId : String
    ) = walkService.getRouteGeometry(
        routeId = routeId
    )
}
