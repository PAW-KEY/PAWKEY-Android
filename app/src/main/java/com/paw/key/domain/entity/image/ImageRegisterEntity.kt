package com.paw.key.domain.entity.image

data class ImageRegisterEntity(
    val imageUrl: String,
    val contentType: String,
    val width: Int,
    val height: Int,
    val domain: ImageDomainType
)

data class ImageRegisterResultEntity(
    val imageId: Int
)

