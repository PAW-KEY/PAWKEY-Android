package com.paw.key.data.remote.datasource

import com.paw.key.data.dto.response.dbti.DbtiQuestionsResponse
import com.paw.key.data.service.DbtiService
import javax.inject.Inject

class DbtiDataSource @Inject constructor(
    private val dbtiService: DbtiService
) {
    suspend fun getQuestions(token: String): DbtiQuestionsResponse {
        return dbtiService.getQuestions("Bearer $token")
    }
}