package edu.javeriana.fixup.ui.features.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.javeriana.fixup.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: edu.javeriana.fixup.data.repository.UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LogInUiState())
    val uiState: StateFlow<LogInUiState> = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email, error = null) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }

    fun onErrorDismissed() {
        _uiState.update { it.copy(error = null) }
    }

    /**
     * Llama al repositorio (que ahora devuelve Result) para iniciar sesión.
     */
    fun signIn(onSuccess: () -> Unit) {
        val email = _uiState.value.email.trim()
        val password = _uiState.value.password

        if (email.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(error = "Por favor completa todos los campos") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val result = authRepository.signIn(email, password)
            
            result.onSuccess { user ->
                // Obtener y registrar el token de FCM
                viewModelScope.launch {
                    try {
                        val token = FirebaseMessaging.getInstance().token.await()
                        userRepository.updateFcmToken(user.uid, token)
                    } catch (e: Exception) {
                        Log.e("LogInViewModel", "Error al obtener token FCM", e)
                    }
                    _uiState.update { it.copy(isLoading = false) }
                    onSuccess()
                }
            }.onFailure { e ->
                // Usamos directamente el mensaje que viene del repositorio (mapeado por AppError)
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
