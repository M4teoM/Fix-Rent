package edu.javeriana.fixup.ui.features.notifications

data class NotificationsUiState(
    val requests: List<NotificationItemModel> = emptyList(),
    val isLoading: Boolean = false
)

data class NotificationItemModel(
    val id: String,
    val title: String,
    val message: String,
    val date: String,
    val isRead: Boolean,
    val profileImageUrl: String? = null,
    val showButton: Boolean = false,
    val hasPreview: Boolean = false,
    val previewImageUrl: String? = null
)
