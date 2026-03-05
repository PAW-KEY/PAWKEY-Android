package com.paw.key.domain.usecase.walk

import com.paw.key.domain.entity.walk.WalkFinish
import com.paw.key.domain.repository.walk.WalkRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

// 걸음 수, 거리, 시간 등을 가져오기 위한 usecase
class GetWalkInfoUseCase @Inject constructor(
    private val repository: WalkRepository
) {
    operator fun invoke(): StateFlow<WalkFinish?> {
        return repository.finishWalkInfo
    }
}