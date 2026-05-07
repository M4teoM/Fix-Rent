package edu.javeriana.fixup.ui.features.wompi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.javeriana.fixup.data.wompi.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WompiViewModel @Inject constructor(
    private val repository: WompiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WompiUiState())
    val uiState: StateFlow<WompiUiState> = _uiState.asStateFlow()

    fun onCardNumberChange(newValue: String) {
        _uiState.update { it.copy(cardNumber = newValue) }
    }

    fun onCardHolderChange(newValue: String) {
        _uiState.update { it.copy(cardHolder = newValue) }
    }

    fun onExpirationDateChange(newValue: String) {
        _uiState.update { it.copy(expirationDate = newValue) }
    }

    fun onCvcChange(newValue: String) {
        _uiState.update { it.copy(cvc = newValue) }
    }

    fun processPayment(amount: Long, email: String, reference: String) {
        val currentState = _uiState.value
        if (currentState.cardNumber.isBlank() || currentState.cvc.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Por favor completa todos los campos") }
            return
        }

        val parts = currentState.expirationDate.split("/")
        if (parts.size != 2) {
            _uiState.update { it.copy(errorMessage = "Formato de fecha inválido (MM/YY)") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            val request = WompiTransactionRequest(
                amountInCents = amount,
                customerEmail = email,
                reference = reference,
                paymentMethod = WompiPaymentMethod.Card(
                    cardNumber = currentState.cardNumber,
                    cvc = currentState.cvc,
                    expMonth = parts[0],
                    expYear = parts[1],
                    cardHolder = currentState.cardHolder
                )
            )

            val result = repository.processPayment(request)
            
            result.onSuccess { response ->
                _uiState.update { it.copy(isLoading = false, transactionResult = response) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
            }
        }
    }
}
