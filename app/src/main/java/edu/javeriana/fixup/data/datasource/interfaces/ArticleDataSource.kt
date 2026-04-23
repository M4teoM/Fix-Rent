package edu.javeriana.fixup.data.datasource.interfaces

import edu.javeriana.fixup.data.network.dto.ArticleDto

interface ArticleDataSource {
    suspend fun getAllArticles(): List<ArticleDto>
    suspend fun getArticleById(id: String): ArticleDto?
}
