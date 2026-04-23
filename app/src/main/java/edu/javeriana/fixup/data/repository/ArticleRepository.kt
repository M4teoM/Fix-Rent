package edu.javeriana.fixup.data.repository

import edu.javeriana.fixup.data.datasource.interfaces.ArticleDataSource
import edu.javeriana.fixup.data.mapper.toDomain
import edu.javeriana.fixup.ui.model.ArticleModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val articleDataSource: ArticleDataSource
) {
    fun getAllArticles(): Flow<Result<List<ArticleModel>>> = flow {
        try {
            val articleDtos = articleDataSource.getAllArticles()
            emit(Result.success(articleDtos.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getArticleById(id: String): Flow<Result<ArticleModel>> = flow {
        try {
            val articleDto = articleDataSource.getArticleById(id)
            if (articleDto != null) {
                emit(Result.success(articleDto.toDomain()))
            } else {
                emit(Result.failure(Exception("Artículo no encontrado")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
