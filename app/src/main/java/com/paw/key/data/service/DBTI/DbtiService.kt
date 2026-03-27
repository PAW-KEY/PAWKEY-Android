package com.paw.key.data.service

import com.paw.key.data.dto.request.dbti.DbtiResultRequest
import com.paw.key.data.dto.response.dbti.DbtiQuestionsResponse
import com.paw.key.data.dto.response.dbti.DbtiResultResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DbtiService {
    @GET("pets/dbti/questions")
    suspend fun getQuestions(
        @Header("Authorization") authorization: String
    ): DbtiQuestionsResponse

    @POST("pets/dbti/{petId}")
    suspend fun submitResult(
        @Path("petId") petId: Long,
        @Header("Authorization") authorization: String,
        @Body request: DbtiResultRequest
    ): DbtiResultResponse

    @GET("pets/dbti/{petId}")
    suspend fun getResult(
        @Path("petId") petId: Long,
        @Header("Authorization") authorization: String
    ): DbtiResultResponse
}