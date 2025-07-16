package com.paw.key.domain.repository

interface LikeRepository {
    suspend fun likeCourse(userId: Int, courseId: Int): Result<Unit>
    suspend fun unlikeCourse(userId: Int, courseId: Int): Result<Unit>
}