package edu.javeriana.fixup.data.datasource.interfaces

import edu.javeriana.fixup.data.network.dto.ReviewDto

interface ReviewDataSource {
    suspend fun getReviewsByUserId(userId: String): List<ReviewDto>
    suspend fun createReview(review: ReviewDto)
}
