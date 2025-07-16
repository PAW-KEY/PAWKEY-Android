package com.paw.key.data.remote.datasource.list

import com.paw.key.data.dto.request.list.PostsListRequestDto
import com.paw.key.data.service.list.PostsListService
import javax.inject.Inject

class PostsListDataSource @Inject constructor(
    private val service: PostsListService
) {
    suspend fun postList(userId: Int, request: PostsListRequestDto) =
        service.postList(userId, request)

    suspend fun getAllPosts(userId: Int) =
        service.postList(userId, PostsListRequestDto(
            durationStart = 0,
            durationEnd = 0,
            selectedOptions = emptyList()
        ))
}