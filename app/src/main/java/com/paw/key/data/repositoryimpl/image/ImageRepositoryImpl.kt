package com.paw.key.data.repositoryimpl.image

import com.paw.key.core.util.suspendRunCatching
import com.paw.key.data.dto.image.presigned.toDto
import com.paw.key.data.dto.image.register.toDto
import com.paw.key.data.remote.datasource.image.ImageDataSource
import com.paw.key.data.remote.datasource.image.ImageLocalDataSource
import com.paw.key.domain.entity.image.ImageDomainType
import com.paw.key.domain.entity.image.ImagePresignedEntity
import com.paw.key.domain.entity.image.ImagePresignedResultEntity
import com.paw.key.domain.entity.image.ImageRegisterEntity
import com.paw.key.domain.entity.image.ImageRegisterResultEntity
import com.paw.key.domain.repository.image.ImageRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageDataSource: ImageDataSource,
    private val imageLocalDataSource: ImageLocalDataSource
) : ImageRepository {
    override suspend fun registerImage(
        uriString : String,
        domainType: ImageDomainType,
    ): Result<ImageRegisterResultEntity> =
        suspendRunCatching{
            val optimizedFile = imageLocalDataSource.getOptimizedFile(uriString.split("#").last())
            val (width, height) = imageLocalDataSource.getImageSize(optimizedFile)

            try {
                val registerEntity = ImageRegisterEntity(
                    imageUrl = uriString.split("#").first(),
                    contentType = optimizedFile.extension,
                    width = width,
                    height = height,
                    domain = domainType
                )

                imageDataSource.registerImage(
                    dto = registerEntity.toDto()
                ).data.toEntity()

            } finally {
                imageLocalDataSource.clearCache()
            }
        }

    override suspend fun presignedImage(presignedEntity: ImagePresignedEntity): Result<ImagePresignedResultEntity> =
        suspendRunCatching{
            imageDataSource.presignedImage(
                dto = presignedEntity.toDto()
            ).data.toEntity()
        }

    override suspend fun uploadS3(
        presignedUrl: String,
        uriString: String
    ): Result<Unit> = suspendRunCatching{
        val file = imageLocalDataSource.getOptimizedFile(uriString)

        val requestBody = file.asRequestBody("image/webp".toMediaTypeOrNull())

        val response = imageDataSource.uploadS3(presignedUrl, requestBody)

        imageLocalDataSource.clearCache()

        if (!response.isSuccessful) {
            throw Exception("S3 Upload Failed: ${response.code()}")
        }
    }

}