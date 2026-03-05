package edu.javeriana.fixup.ui.model

import androidx.annotation.DrawableRes
import edu.javeriana.fixup.R

data class NotificationsUiState(
    val requests: List<NotificationItemModel> = emptyList(),
    val isLoading: Boolean = false
)

data class NotificationItemModel(
    val name: String,
    val time: String,
    val description: String,
    @DrawableRes val profileImage: Int,
    val hasPreview: Boolean = false,
    val showButton: Boolean = false,
    @DrawableRes val previewImage: Int? = null
)
