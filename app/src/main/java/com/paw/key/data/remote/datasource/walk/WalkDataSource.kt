package com.paw.key.data.remote.datasource.walk

import com.paw.key.data.service.walk.WalkService
import javax.inject.Inject

class WalkDataSource @Inject constructor(
    private val walkService: WalkService
) {
    suspend fun startWalk() = walkService.startWalk()
    suspend fun pointWalk() = walkService.pointWalk()
    suspend fun finishWalk() = walkService.finishWalk()
}
