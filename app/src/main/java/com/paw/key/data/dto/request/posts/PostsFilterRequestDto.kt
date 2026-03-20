package com.paw.key.data.dto.request.posts

import com.paw.key.domain.entity.posts.FilterSelectedIOptionEntity
import com.paw.key.domain.entity.posts.FilterSelectedItemEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsFilterRequestDto(
    @SerialName("selectedOptions")
    val selectedOptions: List<FilterOptionRequestDto>
)

fun FilterSelectedItemEntity.toDto() = PostsFilterRequestDto(
    selectedOptions = selectedOptions.map { it.toDto() }
)


@Serializable
data class FilterOptionRequestDto(
    @SerialName("categoryId")
    val categoryId: Int?, // 카테고리 id
    @SerialName("durationId")
    val durationId: Int?, // 소요 시간 카테고리 id
    @SerialName("optionsIds")
    val optionsIds: List<Int?> // 선택한 옵션 id 리스트
)

fun FilterSelectedIOptionEntity.toDto() = FilterOptionRequestDto(
    categoryId = categoryId,
    durationId = durationId,
    optionsIds = optionsIds
)

