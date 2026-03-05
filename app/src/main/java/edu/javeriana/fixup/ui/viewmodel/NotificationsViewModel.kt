package edu.javeriana.fixup.ui.viewmodel

import androidx.lifecycle.ViewModel
import edu.javeriana.fixup.R
import edu.javeriana.fixup.ui.model.NotificationItemModel
import edu.javeriana.fixup.ui.model.NotificationsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotificationsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NotificationsUiState(isLoading = true))
    val uiState: StateFlow<NotificationsUiState> = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        // Simulando carga de datos
        val mockNotifications = listOf(
            NotificationItemModel(
                name = "AndresContreras",
                time = "1d",
                description = "Ha solicitado una cotización",
                profileImage = R.drawable.pf1,
                showButton = true
            ),
            NotificationItemModel(
                name = "nebulanomad",
                time = "1d",
                description = "Ha dado me gusta a tu idea",
                profileImage = R.drawable.pf2,
                hasPreview = true,
                previewImage = R.drawable.cocina
            ),
            NotificationItemModel(
                name = "emberecho",
                time = "2d",
                description = "Ha escrito una review\nQuedó perfecto!!! 🎉🎉",
                profileImage = R.drawable.pf3
            ),
            NotificationItemModel(
                name = "lunavoyager",
                time = "3d",
                description = "Ha guardado tu idea",
                profileImage = R.drawable.pf4,
                hasPreview = true,
                previewImage = R.drawable.comedor
            ),
            NotificationItemModel(
                name = "shadowlynx",
                time = "4d",
                description = "Ha hecho un comentario en tu idea",
                profileImage = R.drawable.pf5,
                hasPreview = true,
                previewImage = R.drawable.exterior
            )
        )
        _uiState.value = NotificationsUiState(requests = mockNotifications, isLoading = false)
    }
}
