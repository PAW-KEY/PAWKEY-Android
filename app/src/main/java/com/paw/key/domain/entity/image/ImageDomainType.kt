package com.paw.key.domain.entity.image

enum class ImageDomainType (
    val label : String
) {
    ROUTE("루트 지도 이미지"),
    PET_PROFILE("강아지 프로필 이미지"),
    WALK("산책 이미지"),
    ETC("기타 등등")
}