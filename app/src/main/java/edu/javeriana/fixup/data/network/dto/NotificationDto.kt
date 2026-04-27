package edu.javeriana.fixup.data.network.dto

data class LikeNotificationDto(
    val reviewId: String,
    val likerId: String,
    val likerName: String,
    val targetUserId: String // User who owns the review and should receive the notification
)

data class FollowNotificationDto(
    val targetUserId: String,
    val followerName: String
)

data class NotificationDto(
    val id: String,
    val title: String,
    val message: String,
    val date: String,
    val isRead: Boolean,
    val profileImageUrl: String? = null,
    val previewImageUrl: String? = null,
    val actionType: String? = null // e.g., "RESPOND", "VIEW"
)
