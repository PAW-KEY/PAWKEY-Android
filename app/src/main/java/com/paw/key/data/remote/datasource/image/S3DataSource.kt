package com.paw.key.data.remote.datasource.image

import com.paw.key.data.service.image.S3Service
import okhttp3.RequestBody
import javax.inject.Inject

class S3DataSource @Inject constructor(
    private val s3Service: S3Service
) {
    suspend fun uploadS3(
        presignedUrl : String,
        file : RequestBody
    ) = s3Service.uploadToS3(
        presignedUrl = presignedUrl,
        file = file
    )
}