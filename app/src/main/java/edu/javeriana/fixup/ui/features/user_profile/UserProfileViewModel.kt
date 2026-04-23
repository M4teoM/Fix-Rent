package edu.javeriana.fixup.ui.features.user_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.javeriana.fixup.data.repository.UserRepository
import edu.javeriana.fixup.data.repository.ReviewRepository
import edu.javeriana.fixup.ui.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState: StateFlow<UserProfileUiState> = _uiState.asStateFlow()

    fun loadUserProfile(userId: String?) {
        if (userId == null) {
            _uiState.update { it.copy(error = "ID de usuario no proporcionado") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            userRepository.getUserById(userId).collect { result ->
                result.onSuccess { user ->
                    viewModelScope.launch {
                        fetchReviews(userId, user)
                    }
                }.onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
            }
        }
    }

    private suspend fun fetchReviews(userId: String, user: UserModel) {
        reviewRepository.getReviewsByUserId(userId).collect { result ->
            result.onSuccess { reviews ->
                _uiState.update { 
                    it.copy(
                        user = user,
                        reviews = reviews,
                        isLoading = false,
                        error = null
                    )
                }
            }.onFailure { error ->
                _uiState.update { 
                    it.copy(
                        user = user,
                        isLoading = false,
                        error = "Error al cargar reseñas: ${error.message}"
                    )
                }
            }
        }
    }
}
