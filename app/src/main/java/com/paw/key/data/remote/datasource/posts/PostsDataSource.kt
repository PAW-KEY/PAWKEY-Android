package com.paw.key.data.remote.datasource.posts

import com.paw.key.data.dto.request.posts.PostsListRequestDto
import com.paw.key.data.service.posts.PostsService
import javax.inject.Inject

class PostsDataSource @Inject constructor(
    private val service: PostsService
) {
    suspend fun postList(userId: Int, request: PostsListRequestDto) =
        { }

    suspend fun getAllPosts(userId: Int) = {}
}