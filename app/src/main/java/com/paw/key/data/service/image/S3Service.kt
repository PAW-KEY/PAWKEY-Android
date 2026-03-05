package com.paw.key.data.service.image

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Url

interface S3Service {
    @PUT
    suspend fun uploadToS3(
        @Url presignedUrl: String,
        @Body file: RequestBody
    ): Response<Unit>
}