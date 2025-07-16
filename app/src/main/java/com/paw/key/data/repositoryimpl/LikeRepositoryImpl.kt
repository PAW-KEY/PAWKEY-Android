package com.paw.key.data.repositoryimpl

import com.paw.key.data.remote.datasource.LikeDataSource
import com.paw.key.domain.repository.LikeRepository
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(
    private val dataSource: LikeDataSource
) : LikeRepository {

    override suspend fun likeCourse(userId: Int, courseId: Int): Result<Unit> {
        return try {
            dataSource.likeCourse(userId, courseId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun unlikeCourse(userId: Int, courseId: Int): Result<Unit> {
        return try {
            dataSource.unlikeCourse(userId, courseId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}