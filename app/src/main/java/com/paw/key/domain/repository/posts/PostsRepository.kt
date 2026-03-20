package com.paw.key.domain.repository.posts

import com.paw.key.data.dto.request.posts.PostsListRequestDto
import com.paw.key.domain.entity.posts.PostsEntity

interface PostsRepository {
    suspend fun postList(userId: Int, request: PostsListRequestDto): Result<PostsEntity>
}
