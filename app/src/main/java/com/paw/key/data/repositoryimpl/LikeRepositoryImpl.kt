package com.paw.key.data.repositoryimpl

import com.paw.key.data.remote.datasource.LikeDataSource
import com.paw.key.domain.repository.LikeRepository
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(
    private val dataSource: LikeDataSource
) : LikeRepository {

    override suspend fun likeCourse(userId: Int, postId: Int): Result<Unit> {
        return runCatching {
            val response = dataSource.likeCourse(userId = userId, postId = postId)
            if (response.code == "S000") {
                Unit
            } else {
                throw Exception(response.message ?: "좋아요 처리에 실패했습니다")
            }
        }.recoverCatching { exception ->
            when (exception) {
                is HttpException -> {
                    when (exception.code()) {
                        HttpURLConnection.HTTP_BAD_REQUEST ->
                            throw Exception("본인이 작성한 게시글에는 좋아요를 할 수 없습니다")
                        HttpURLConnection.HTTP_CONFLICT ->
                            throw Exception("이미 좋아요를 누른 게시글입니다")
                        HttpURLConnection.HTTP_INTERNAL_ERROR ->
                            throw Exception("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요")
                        else ->
                            throw Exception("좋아요 처리에 실패했습니다 (${exception.code()})")
                    }
                }
                else -> {
                    if (isJsonParsingError(exception)) {
                        Unit
                    } else {
                        throw exception
                    }
                }
            }
        }
    }

    override suspend fun unlikeCourse(userId: Int, postId: Int): Result<Unit> {
        return runCatching {
            val response = dataSource.unlikeCourse(userId = userId, postId = postId)
            if (response.code == "S000") {
                Unit
            } else {
                throw Exception(response.message ?: "좋아요 취소 처리에 실패했습니다")
            }
        }.recoverCatching { exception ->
            when (exception) {
                is HttpException -> {
                    when (exception.code()) {
                        HttpURLConnection.HTTP_BAD_REQUEST ->
                            throw Exception("잘못된 요청입니다")
                        HttpURLConnection.HTTP_CONFLICT ->
                            throw Exception("이미 처리된 요청입니다")
                        HttpURLConnection.HTTP_INTERNAL_ERROR ->
                            throw Exception("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요")
                        else ->
                            throw Exception("좋아요 취소 처리에 실패했습니다 (${exception.code()})")
                    }
                }
                else -> {
                    if (isJsonParsingError(exception)) {
                        Unit
                    } else {
                        throw exception
                    }
                }
            }
        }
    }

    private fun isJsonParsingError(exception: Throwable): Boolean {
        val message = exception.message ?: return false
        return message.contains("Unexpected JSON token") ||
                message.contains("Expected start of the object") ||
                message.contains("JSON input") ||
                message.contains("SerializationException")
    }
}