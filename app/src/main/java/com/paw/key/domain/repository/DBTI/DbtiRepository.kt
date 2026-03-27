package com.paw.key.domain.repository

import com.paw.key.domain.entity.DBTI.DbtiResultEntity
import com.paw.key.domain.entity.dbti.DbtiQuestionEntity

interface DbtiRepository {
    suspend fun getQuestions(): Result<List<DbtiQuestionEntity>>

    suspend fun submitResult(
        petId: Long,
        optionIds: List<Int>
    ): Result<DbtiResultEntity>

    suspend fun getResult(petId: Long): Result<DbtiResultEntity>
}