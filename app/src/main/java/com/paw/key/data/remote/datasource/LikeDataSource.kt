package com.paw.key.data.remote.datasource

import com.paw.key.data.service.LikeService
import javax.inject.Inject

class LikeDataSource @Inject constructor(
    private val likeService: LikeService
) {
    suspend fun likeCourse(userId: Int, courseId: Int) =
        likeService.likeCourse(userId, courseId)

    suspend fun unlikeCourse(userId: Int, courseId: Int) =
        likeService.unlikeCourse(userId, courseId)
}