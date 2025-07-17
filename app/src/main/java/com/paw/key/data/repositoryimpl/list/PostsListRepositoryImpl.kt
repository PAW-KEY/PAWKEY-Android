package com.paw.key.data.repositoryimpl.list

import com.paw.key.data.dto.request.list.PostsListRequestDto
import com.paw.key.data.dto.response.list.toEntity
import com.paw.key.data.remote.datasource.list.PostsListDataSource
import com.paw.key.domain.model.entity.list.ListEntity
import com.paw.key.domain.repository.list.PostsListRepository
import javax.inject.Inject

class PostsListRepositoryImpl @Inject constructor(
    private val dataSource: PostsListDataSource,
) : PostsListRepository {
    override suspend fun postList(userId: Int, request: PostsListRequestDto)
            : Result<ListEntity> = runCatching {
        val response = dataSource.postList(userId, request)
        if (response.code == "S000") {
            response.data.toEntity()
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun getAllPosts(userId: Int): Result<ListEntity> = runCatching {
        val response = dataSource.getAllPosts(userId)
        if (response.code == "S000") {
            response.data.toEntity() ?: throw Exception("Data is null")
        } else {
            throw Exception(response.message)
        }
    }
}