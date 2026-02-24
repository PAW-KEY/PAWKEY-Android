package com.paw.key.data.repositoryimpl

import com.paw.key.data.remote.datasource.DbtiDataSource
import com.paw.key.domain.entity.dbti.DbtiOptionEntity
import com.paw.key.domain.entity.dbti.DbtiQuestionEntity
import com.paw.key.domain.repository.DbtiRepository
import javax.inject.Inject

class DbtiRepositoryImpl @Inject constructor(
    private val dbtiDataSource: DbtiDataSource
) : DbtiRepository {

    override suspend fun getQuestions(): Result<List<DbtiQuestionEntity>> {
        return runCatching {
            val response = dbtiDataSource.getQuestions("YOUR_TOKEN") // TODO: 실제 토큰으로 변경
            response.data.questions.map { questionDto ->
                DbtiQuestionEntity(
                    id = questionDto.id,
                    categoryCode = questionDto.category.code,
                    categoryName = questionDto.category.name,
                    content = questionDto.content,
                    options = questionDto.options.map { optionDto ->
                        DbtiOptionEntity(
                            id = optionDto.id,
                            content = optionDto.content,
                            imageUrl = optionDto.imageUrl,
                            value = optionDto.value
                        )
                    }
                )
            }
        }
    }
}