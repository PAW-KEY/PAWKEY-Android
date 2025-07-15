package com.paw.key.data.repositoryimpl.filter

import com.paw.key.data.remote.datasource.filter.FilterOptionDataSource
import com.paw.key.domain.model.entity.filter.Category
import com.paw.key.domain.model.entity.filter.CategoryOption
import com.paw.key.domain.model.entity.filter.FilterEntity
import com.paw.key.domain.repository.filter.FilterOptionRepository
import javax.inject.Inject

class FilterOptionRepositoryImpl @Inject constructor(
    private val dataSource: FilterOptionDataSource
) : FilterOptionRepository {

    override suspend fun getFilterOptions(userId: Int): Result<FilterEntity> {
        return try {
            val response = dataSource.getFilterOptions(userId)

            if (response.code == "S000") {
                val filterEntity = FilterEntity(
                    categoryList = try {
                        response.data.categoryList.map { categoryDto ->
                            Category(
                                categoryId = categoryDto.categoryId ?: 0,
                                categoryName = categoryDto.categoryName ?: "",
                                categoryOptions = categoryDto.categoryOptions?.map { optionDto ->
                                    CategoryOption(
                                        categoryOptionId = optionDto.categoryOptionId ?: 0,
                                        categoryOptionText = optionDto.categoryOptionText ?: ""
                                    )
                                } ?: emptyList()
                            )
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        emptyList()
                    }
                )

                filterEntity.categoryList?.forEach { category ->
                }

                Result.success(filterEntity)
            } else {
                Result.failure(Exception("API Error: ${response.message}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}