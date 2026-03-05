package edu.javeriana.fixup.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.javeriana.fixup.ui.model.ProfileUiState
import edu.javeriana.fixup.ui.model.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState(isLoading = true))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                val profile = UserRepository.getUserProfile()
                _uiState.value = profile
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                // Aquí se podría manejar el error de carga si fuera necesario
            }
        }
    }

    fun updateName(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName)
    }

    fun updateAddress(newAddress: String) {
        _uiState.value = _uiState.value.copy(address = newAddress)
    }

    fun updatePhone(newPhone: String) {
        _uiState.value = _uiState.value.copy(phone = newPhone)
    }

    fun updateEmail(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }
}
