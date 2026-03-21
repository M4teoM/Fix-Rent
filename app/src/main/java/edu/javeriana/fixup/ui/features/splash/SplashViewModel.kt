package edu.javeriana.fixup.ui.features.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.javeriana.fixup.data.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val isUserLoggedIn: Boolean
        get() = authRepository.isUserLoggedIn
}
