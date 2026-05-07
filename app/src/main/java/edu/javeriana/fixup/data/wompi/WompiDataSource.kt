package edu.javeriana.fixup.data.wompi

interface WompiDataSource {
    suspend fun processPayment(request: WompiTransactionRequest): WompiTransactionResponse
    suspend fun getTransactionStatus(id: String): WompiTransactionResponse
}
