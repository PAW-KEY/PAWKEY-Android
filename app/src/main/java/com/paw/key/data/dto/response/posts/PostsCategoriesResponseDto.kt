package com.paw.key.data.dto.response.posts

import com.paw.key.domain.entity.posts.FilterItemEntity
import com.paw.key.domain.entity.posts.FilterOptionEntity
import com.paw.key.domain.entity.posts.PostsCategoryEntity
import com.paw.key.domain.entity.posts.PostsFilterEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.collections.map

@Serializable
data class CategoryListResponseDto(
    @SerialName("categoryList")
    val categoryList: List<FilterItemDto>
) {
    fun toEntity() = PostsCategoryEntity(
        categoryList = categoryList.map { it.toEntity() }
    )
}

@Serializable
data class FilterOptionResponseDto(
    @SerialName("durationList")
    val durationList: List<FilterItemDto>,
    @SerialName("categoryList")
    val categoryList: List<FilterItemDto>
) {
    fun toEntity() = PostsFilterEntity(
        durationList = durationList.map { it.toEntity() },
        categoryList = categoryList.map { it.toEntity() }
    )
}

@Serializable
data class FilterItemDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("selectionType")
    val selectionType: String,
    @SerialName("options")
    val options: List<FilterOptionDto>
) {
    fun toEntity() = FilterItemEntity(
        id = id,
        name = name,
        selectionType = selectionType,
        options = options.map { it.toEntity() }
    )
}

@Serializable
data class FilterOptionDto(
    @SerialName("id")
    val id: Int,
    @SerialName("text")
    val text: String
) {
    fun toEntity() = FilterOptionEntity(
        id = id,
        text = text
    )
}
