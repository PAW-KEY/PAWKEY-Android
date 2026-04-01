package com.paw.key.domain.usecase.walk

import com.paw.key.domain.entity.walk.WalkFinishEntity
import com.paw.key.domain.repository.walk.WalkRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

// finish 후 complete에서 데이터 공유하기 위함
class GetWalkFinishResultUseCase @Inject constructor(
    private val repository: WalkRepository
) {
    operator fun invoke(): StateFlow<WalkFinishEntity?> {
        return repository.finishResult
    }
}