package edu.javeriana.fixup.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import edu.javeriana.fixup.data.datasource.interfaces.ArticleDataSource
import edu.javeriana.fixup.data.network.dto.ArticleDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ArticleDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ArticleDataSource {

    override suspend fun getAllArticles(): List<ArticleDto> {
        val snapshot = firestore.collection("articles").get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(ArticleDto::class.java)?.copy(id = doc.id)
        }
    }

    override suspend fun getArticleById(id: String): ArticleDto? {
        val document = firestore.collection("articles").document(id).get().await()
        return if (document.exists()) {
            document.toObject(ArticleDto::class.java)?.copy(id = document.id)
        } else {
            null
        }
    }
}
