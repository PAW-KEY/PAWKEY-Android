package com.paw.key.domain.repository

import com.paw.key.domain.entity.dbti.DbtiQuestionEntity

interface DbtiRepository {
    suspend fun getQuestions(): Result<List<DbtiQuestionEntity>>
}