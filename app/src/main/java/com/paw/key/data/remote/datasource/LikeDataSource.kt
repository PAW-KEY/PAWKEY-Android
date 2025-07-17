package com.paw.key.data.remote.datasource

import com.paw.key.data.service.LikeService
import javax.inject.Inject

class LikeDataSource @Inject constructor(
    private val likeService: LikeService
) {
    suspend fun likeCourse(userId: Int, postId: Int) =
        likeService.likeCourse(userId, postId)

    suspend fun unlikeCourse(userId: Int, postId: Int) =
        likeService.unlikeCourse(userId, postId)
}