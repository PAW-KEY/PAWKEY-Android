package com.paw.key.domain.entity.mypage

import com.paw.key.data.dto.response.mypage.ReviewPostDto
import com.paw.key.data.dto.response.mypage.RoutePostDto

data class RoutePostEntity(
    val postId: Int,
    val regionName: String,
    val title: String,
    val date: String,
    val durationMinutes: Int,
    val isLiked: Boolean,
    val imageUrl: String,
)

data class ReviewPostEntity(
    val postId: Int,
    val title: String,
    val regionName: String,
    val date: String,
    val categoryOptionSummary: List<String>,
)

fun RoutePostDto.toEntity() = RoutePostEntity(
    postId          = postId,
    regionName      = regionName,
    title           = title,
    date            = date,
    durationMinutes = durationMinutes,
    isLiked         = isLiked,
    imageUrl        = imageUrl,
)

fun ReviewPostDto.toEntity() = ReviewPostEntity(
    postId                = postId,
    title                 = title,
    regionName            = regionName,
    date                  = date,
    categoryOptionSummary = categoryOptionSummary,
)