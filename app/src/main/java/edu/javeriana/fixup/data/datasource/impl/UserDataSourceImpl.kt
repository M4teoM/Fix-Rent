package edu.javeriana.fixup.data.datasource.impl

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import edu.javeriana.fixup.data.datasource.interfaces.UserDataSource
import edu.javeriana.fixup.data.network.dto.UserDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserDataSource {

    override suspend fun getUserById(userId: String): UserDto? {
        val document = firestore.collection("users").document(userId).get().await()
        return if (document.exists()) {
            // Mapeo manual del document.id al campo id del DTO
            document.toObject(UserDto::class.java)?.copy(id = document.id)
        } else {
            null
        }
    }

    override suspend fun toggleFollowUser(currentUserId: String, targetUserId: String, isFollowing: Boolean) {
        val batch = firestore.batch()
        
        val currentUserRef = firestore.collection("users").document(currentUserId)
        val targetUserRef = firestore.collection("users").document(targetUserId)
        
        val currentUserUpdate = if (isFollowing) {
            FieldValue.arrayRemove(targetUserId)
        } else {
            FieldValue.arrayUnion(targetUserId)
        }
        
        val targetUserUpdate = if (isFollowing) {
            FieldValue.arrayRemove(currentUserId)
        } else {
            FieldValue.arrayUnion(currentUserId)
        }
        
        batch.update(currentUserRef, "following", currentUserUpdate)
        batch.update(targetUserRef, "followers", targetUserUpdate)
        
        batch.commit().await()
    }
}
