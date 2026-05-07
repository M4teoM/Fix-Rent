package edu.javeriana.fixup.ui.features.wompi

import edu.javeriana.fixup.data.wompi.WompiTransactionResponse

data class WompiUiState(
    val cardNumber: String = "",
    val cardHolder: String = "",
    val expirationDate: String = "", // MM/YY
    val cvc: String = "",
    val isLoading: Boolean = false,
    val transactionResult: WompiTransactionResponse? = null,
    val errorMessage: String? = null
)
