package com.paw.key.data.service

import com.paw.key.data.dto.response.dbti.DbtiQuestionsResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface DbtiService {
    @GET("api/dbti/questions")
    suspend fun getQuestions(
        @Header("Authorization") authorization: String
    ): DbtiQuestionsResponse
}