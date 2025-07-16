package com.paw.key.domain.repository

interface LikeRepository {
    suspend fun likeCourse(userId: Int, postId: Int): Result<Unit>
    suspend fun unlikeCourse(userId: Int, postId: Int): Result<Unit>
}