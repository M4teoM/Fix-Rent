package edu.javeriana.fixup.data.datasource.impl

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import edu.javeriana.fixup.data.datasource.ProfileDataSource
import edu.javeriana.fixup.data.network.model.ReviewRequestDto
import edu.javeriana.fixup.data.network.service.FixUpApiService
import edu.javeriana.fixup.ui.model.ReviewModel
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

/**
 * Implementación de ProfileDataSource usando Firebase Auth y Storage.
 */
class ProfileDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val apiService: FixUpApiService
) : ProfileDataSource {

    override suspend fun uploadProfileImage(uri: Uri, timeoutMillis: Long): String {
        return withTimeout(timeoutMillis) {
            val user = auth.currentUser ?: throw Exception("Usuario no autenticado")
            val storageRef = storage.reference.child("profilePictures/${user.uid}.jpg")
            
            storageRef.putFile(uri).await()
            storageRef.downloadUrl.await().toString()
        }
    }

    override suspend fun updateProfilePhotoUrl(photoUrl: String) {
        val user = auth.currentUser ?: throw Exception("Usuario no autenticado")
        val profileUpdates = userProfileChangeRequest {
            photoUri = Uri.parse(photoUrl)
        }
        user.updateProfile(profileUpdates).await()
    }

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override suspend fun getReviewsByUserId(userId: String): List<ReviewModel> {
        return try {
            apiService.getReviewsByUserId(userId).map { dto ->
                ReviewModel(
                    id = dto.id?.toString() ?: "",
                    userId = dto.userId.toString(),
                    rating = dto.rating,
                    comment = dto.comment,
                    userName = "Usuario ${dto.userId}"
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun createReview(review: ReviewModel): ReviewModel {
        val request = ReviewRequestDto(
            userId = review.userId.toIntOrNull() ?: 1,
            serviceId = 1,
            rating = review.rating,
            comment = review.comment
        )
        val resultDto = apiService.createReview(request)
        return ReviewModel(
            id = resultDto.id?.toString() ?: "",
            userId = resultDto.userId.toString(),
            rating = resultDto.rating,
            comment = resultDto.comment,
            userName = "Usuario ${resultDto.userId}"
        )
    }

    override suspend fun updateReview(id: String, review: ReviewModel): ReviewModel {
        val request = ReviewRequestDto(
            userId = review.userId.toIntOrNull() ?: 1,
            serviceId = 1,
            rating = review.rating,
            comment = review.comment
        )
        val resultDto = apiService.updateReview(id, request)
        return ReviewModel(
            id = resultDto.id?.toString() ?: "",
            userId = resultDto.userId.toString(),
            rating = resultDto.rating,
            comment = resultDto.comment,
            userName = "Usuario ${resultDto.userId}"
        )
    }

    override suspend fun deleteReview(id: String) {
        apiService.deleteReview(id)
    }
}
