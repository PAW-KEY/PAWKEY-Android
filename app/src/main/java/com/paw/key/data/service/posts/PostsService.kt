package com.paw.key.data.service.posts

import com.paw.key.data.dto.request.posts.PostsDataRequestDto
import com.paw.key.data.dto.request.posts.PostsFilterRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.posts.CategoryListResponseDto
import com.paw.key.data.dto.response.posts.FilterOptionResponseDto
import com.paw.key.data.dto.response.posts.PostDetailResponseDto
import com.paw.key.data.dto.response.posts.PostsFilterListResponseDto
import com.paw.key.data.dto.response.posts.PostsResponseDto
import com.paw.key.data.dto.response.posts.PostsTop3ReviewResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsService {
    // 산책 게시물 등록
    @POST("posts")
    suspend fun postPosts(
        @Body request: PostsDataRequestDto
    ): BaseResponse<PostsResponseDto>

    // Todo : 게시물 수정, 삭제 3스 때..

    @GET("posts/categories")
    suspend fun getCategories(): BaseResponse<CategoryListResponseDto> // 게시물 작성할 때 필터링 카테고리 조회

    @GET("posts/{postId}")
    suspend fun getPostsDetail(
        @Path("postId") postId: Int
    ): BaseResponse<PostDetailResponseDto>

    @GET("posts/{routeId}/reviews/top3")
    suspend fun getTop3Reviews(
        @Path("routeId") routeId: Int
    ): BaseResponse<PostsTop3ReviewResponseDto>

    @POST("posts/filter")
    suspend fun getPostsFilter(
        @Query("sortBy") sortBy: String = "latest",
        @Query("cursor") cursor: String? = null,
        @Query("size") size: Int = 10,
        @Body request: PostsFilterRequestDto
    ): BaseResponse<PostsFilterListResponseDto>

    @GET("posts/filter")
    suspend fun getCategoriesFilter(): BaseResponse<FilterOptionResponseDto> // 커뮤니티 화면에서 필터링 카테고리 조회

    @POST("posts/{postId}/likes")
    suspend fun postLike(
        @Path("postId") postId: Int
    ): BaseResponse<Unit>
}
