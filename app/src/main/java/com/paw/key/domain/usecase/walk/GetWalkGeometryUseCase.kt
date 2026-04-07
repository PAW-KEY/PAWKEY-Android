package com.paw.key.domain.usecase.walk

import com.paw.key.domain.entity.walk.WalkCompleteEntity
import com.paw.key.domain.repository.walk.WalkRepository
import javax.inject.Inject

/**
 * 산책 좌표 정보 가져오기
 * */
class GetWalkGeometryUseCase @Inject constructor(
    private val repository: WalkRepository
) {
    suspend operator fun invoke(routeId: Int) : Result<WalkCompleteEntity> {
        return repository.completeWalk(routeId)
    }
}