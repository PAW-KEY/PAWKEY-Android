package com.paw.key.data.repositoryimpl.posts

import com.paw.key.data.dto.request.posts.PostsListRequestDto
import com.paw.key.data.remote.datasource.posts.PostsDataSource
import com.paw.key.domain.entity.posts.PostsEntity
import com.paw.key.domain.repository.posts.PostsRepository
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val dataSource: PostsDataSource,
) : PostsRepository {
    override suspend fun postList(userId: Int, request: PostsListRequestDto)
            : Result<PostsEntity> = runCatching {
        val response = dataSource.postList(userId, request)
        throw Exception("Data is null")
    }
}