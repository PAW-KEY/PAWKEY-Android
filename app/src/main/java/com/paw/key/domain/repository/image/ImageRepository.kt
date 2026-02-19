package com.paw.key.domain.repository.image

import com.paw.key.domain.entity.image.ImageDomainType
import com.paw.key.domain.entity.image.ImagePresignedEntity
import com.paw.key.domain.entity.image.ImagePresignedResultEntity
import com.paw.key.domain.entity.image.ImageRegisterResultEntity

interface ImageRepository {
    suspend fun registerImage(
        uriString : String,
        domainType: ImageDomainType,
    ) : Result<ImageRegisterResultEntity>

    suspend fun presignedImage(
        presignedEntity : ImagePresignedEntity
    ) : Result<ImagePresignedResultEntity>

    suspend fun uploadS3(
        presignedUrl : String,
        uriString : String
    ) : Result<Unit>
}