package edu.javeriana.fixup.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import edu.javeriana.fixup.data.datasource.interfaces.ReviewDataSource
import edu.javeriana.fixup.ui.model.ReviewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementación de ReviewDataSource que usa Firebase Firestore como fuente de datos en tiempo real.
 */
class ReviewFirebaseDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ReviewDataSource {

    override fun getReviewsByUserId(userId: String): Flow<Result<List<ReviewModel>>> = callbackFlow {
        val subscription = firestore.collection("reviews")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Result.failure(error))
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val reviews = mapSnapshotToReviews(snapshot)
                    trySend(Result.success(reviews))
                }
            }
        awaitClose { subscription.remove() }
    }

    override fun getReviewsByServiceId(serviceId: String): Flow<Result<List<ReviewModel>>> = callbackFlow {
        val subscription = firestore.collection("reviews")
            .whereEqualTo("serviceId", serviceId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Result.failure(error))
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val reviews = mapSnapshotToReviews(snapshot)
                    trySend(Result.success(reviews))
                }
            }
        awaitClose { subscription.remove() }
    }

    private fun mapSnapshotToReviews(snapshot: QuerySnapshot): List<ReviewModel> {
        return snapshot.documents.mapNotNull { doc ->
            val rating = doc.getLong("rating")?.toInt() ?: 0
            val comment = doc.getString("comment") ?: ""
            val authorName = doc.getString("authorName") ?: "Usuario"
            val authorProfileImageUrl = doc.getString("authorProfileImageUrl") ?: ""
            val serviceTitle = doc.getString("serviceTitle") ?: doc.getString("articleName") ?: ""
            val serviceId = doc.getString("serviceId") ?: doc.getString("articleId") ?: ""
            val userId = doc.getString("userId") ?: ""
            val likedByRaw = doc.get("likedBy")
            val likedBy = if (likedByRaw is List<*>) {
                likedByRaw.filterIsInstance<String>()
            } else {
                emptyList()
            }
            
            ReviewModel(
                id = doc.id,
                userId = userId,
                serviceId = serviceId,
                rating = rating,
                comment = comment,
                authorName = authorName,
                authorProfileImageUrl = authorProfileImageUrl,
                serviceTitle = serviceTitle,
                date = "", 
                likedBy = likedBy
            )
        }
    }

    override suspend fun createReview(review: ReviewModel): Result<Boolean> {
        return try {
            val reviewMap = hashMapOf(
                "userId" to review.userId,
                "serviceId" to review.serviceId,
                "rating" to review.rating,
                "comment" to review.comment,
                "authorName" to review.authorName,
                "authorProfileImageUrl" to review.authorProfileImageUrl,
                "serviceTitle" to review.serviceTitle,
                "likedBy" to review.likedBy
            )
            firestore.collection("reviews").add(reviewMap).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
