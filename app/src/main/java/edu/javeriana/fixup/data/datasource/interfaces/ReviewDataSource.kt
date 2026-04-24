package edu.javeriana.fixup.data.datasource.interfaces
import edu.javeriana.fixup.ui.model.ReviewModel
import kotlinx.coroutines.flow.Flow

interface ReviewDataSource {
    fun getReviewsByUserId(userId: String): Flow<Result<List<ReviewModel>>>
    fun getReviewsByServiceId(serviceId: String): Flow<Result<List<ReviewModel>>>
    suspend fun createReview(review: ReviewModel): Result<Boolean>
}
