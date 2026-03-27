package com.paw.key.data.remote.datasource

import com.paw.key.data.dto.request.dbti.DbtiResultRequest
import com.paw.key.data.dto.response.dbti.DbtiQuestionsResponse
import com.paw.key.data.dto.response.dbti.DbtiResultResponse
import com.paw.key.data.service.DbtiService
import javax.inject.Inject

class DbtiDataSource @Inject constructor(
    private val dbtiService: DbtiService
) {
    suspend fun getQuestions(token: String): DbtiQuestionsResponse {
        return dbtiService.getQuestions("Bearer $token")
    }

    suspend fun submitResult(
        petId: Long,
        token: String,
        optionIds: List<Int>
    ): DbtiResultResponse {
        return dbtiService.submitResult(
            petId = petId,
            authorization = "Bearer $token",
            request = DbtiResultRequest(optionIds = optionIds)
        )
    }

    suspend fun getResult(
        petId: Long,
        token: String
    ): DbtiResultResponse {
        return dbtiService.getResult(
            petId = petId,
            authorization = "Bearer $token"
        )
    }
}