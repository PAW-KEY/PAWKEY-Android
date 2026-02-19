package com.paw.key.domain.repository.list

import com.paw.key.data.dto.request.list.PostsListRequestDto
import com.paw.key.domain.entity.list.ListEntity

interface PostsListRepository {
    suspend fun postList(userId: Int, request: PostsListRequestDto): Result<ListEntity>
    suspend fun getAllPosts(userId: Int): Result<ListEntity>
}
