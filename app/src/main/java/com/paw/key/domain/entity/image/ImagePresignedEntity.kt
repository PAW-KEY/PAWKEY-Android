package com.paw.key.domain.entity.image

data class ImagePresignedEntity(
    val domain: ImageDomainType,
    val contentType: String
)

data class ImagePresignedResultEntity(
    val uploadUrl: String,
    val imageUrl: String
)