package edu.javeriana.fixup.data.repository

import android.net.Uri
import edu.javeriana.fixup.data.datasource.interfaces.RentDataSource
import edu.javeriana.fixup.data.network.dto.ReviewRequestDto
import edu.javeriana.fixup.data.network.api.FixUpApiService
import edu.javeriana.fixup.data.util.AppConstants
import edu.javeriana.fixup.ui.model.PropertyModel
import edu.javeriana.fixup.ui.model.ReviewModel
import javax.inject.Inject

class RentRepository @Inject constructor(
    private val dataSource: RentDataSource,
    private val apiService: FixUpApiService
) {
    suspend fun getProperties(): Result<List<PropertyModel>> {
        return try {
            val properties = dataSource.getRentProperties()
            Result.success(properties)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createProperty(property: PropertyModel, imageUri: Uri): Result<PropertyModel> {
        return try {
            val created = dataSource.createProperty(property, imageUri)
            Result.success(created)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewsByServiceId(serviceId: Int): Result<List<ReviewModel>> {
        return try {
            val reviewDtos = apiService.getReviewsByServiceId(serviceId)
            val reviews = reviewDtos.map { dto ->
                ReviewModel(
                    id = dto.id ?: "",
                    userId = dto.userId ?: "",
                    rating = dto.rating?.toInt() ?: 0,
                    comment = dto.comment ?: "",
                    userName = dto.userName ?: "Usuario ${dto.userId}"
                )
            }
            Result.success(reviews)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createReview(serviceId: Int, rating: Int, comment: String): Result<ReviewModel> {
        return try {
            val request = ReviewRequestDto(
                userId = AppConstants.CURRENT_USER_ID,
                serviceId = serviceId.toString(),
                rating = rating,
                comment = comment
            )
            val resultDto = apiService.createReview(request)
            val savedReview = ReviewModel(
                id = resultDto.id ?: "",
                userId = resultDto.userId ?: "",
                rating = resultDto.rating?.toInt() ?: 0,
                comment = resultDto.comment ?: "",
                userName = resultDto.userName ?: "Usuario ${resultDto.userId}"
            )
            Result.success(savedReview)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
