package edu.javeriana.fixup.data.wompi

enum class WompiStatus {
    PENDING,
    APPROVED,
    DECLINED,
    VOIDED,
    ERROR
}

data class WompiTransactionRequest(
    val amountInCents: Long,
    val currency: String = "COP",
    val customerEmail: String,
    val reference: String,
    val paymentMethod: WompiPaymentMethod
)

sealed class WompiPaymentMethod {
    data class Card(
        val cardNumber: String,
        val cvc: String,
        val expMonth: String,
        val expYear: String,
        val cardHolder: String
    ) : WompiPaymentMethod()
    
    data object Nequi : WompiPaymentMethod()
}

data class WompiTransactionResponse(
    val id: String,
    val status: WompiStatus,
    val amountInCents: Long,
    val reference: String,
    val createdAt: String,
    val errorMessage: String? = null
)
