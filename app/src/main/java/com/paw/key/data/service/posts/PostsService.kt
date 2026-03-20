package com.paw.key.data.service.posts

import com.paw.key.data.dto.request.posts.PostsDataRequestDto
import com.paw.key.data.dto.request.posts.PostsFilterRequestDto
import com.paw.key.data.dto.request.posts.PostsListRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.posts.PostDetailResponseDto
import com.paw.key.data.dto.response.posts.PostsResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsService {
    // 산책 게시물 등록
    @POST("posts")
    suspend fun postPosts(
        @Body request: PostsDataRequestDto
    ): BaseResponse<PostsResponseDto>

    @POST("posts/{postId}/likes")
    suspend fun postLike(
        @Path("postId") postId: Int
    ): BaseResponse<PostsResponseDto>

    @POST("posts/filter")
    suspend fun getPostsFilter(
        @Query("sortBy") sortBy: String = "latest",
        @Query("cursor") cursor: String? = null,
        @Query("size") size: Int = 10,
        @Body request: PostsFilterRequestDto
    ): BaseResponse<PostDetailResponseDto>

    @GET("posts/{postId}")
    suspend fun getPosts(
        @Path("postId") postId: Int
    ): BaseResponse<PostDetailResponseDto>

    @PATCH("posts/{postId}")
    suspend fun patchPosts(
        @Path("postId") postId: Int
    ): BaseResponse<PostDetailResponseDto>

    @GET("posts/{routeId}/reviews/top")
    suspend fun getTopReviews(
        @Path("routeId") routeId: Int
    ): BaseResponse<PostDetailResponseDto>

    @GET("posts/categories")
    suspend fun getCategories(): BaseResponse<PostDetailResponseDto>

    @GET("posts/categories/filter")
    suspend fun getCategoriesFilter(
        @Body request: PostsListRequestDto
    ): BaseResponse<PostDetailResponseDto>


}