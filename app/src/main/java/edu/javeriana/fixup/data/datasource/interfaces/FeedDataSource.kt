package edu.javeriana.fixup.data.datasource.interfaces

import android.net.Uri
import edu.javeriana.fixup.data.network.model.CategoryDto
import edu.javeriana.fixup.data.network.model.PublicationDto
import edu.javeriana.fixup.data.network.model.ReviewRequestDto
import edu.javeriana.fixup.ui.model.PropertyModel
import edu.javeriana.fixup.ui.model.ReviewModel

/**
 * Contrato del Data Source para Feed.
 */
interface FeedDataSource {
    suspend fun getCategories(): List<CategoryDto>
    suspend fun getPublications(): List<PublicationDto>
    suspend fun getPublicationById(id: Int): PublicationDto
    suspend fun createPublication(property: PropertyModel, imageUri: Uri): PropertyModel
    suspend fun getReviewsByServiceId(serviceId: Int): List<ReviewModel>
    suspend fun createReview(review: ReviewRequestDto): ReviewModel
}
