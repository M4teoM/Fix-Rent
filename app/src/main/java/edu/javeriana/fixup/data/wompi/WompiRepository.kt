package edu.javeriana.fixup.data.wompi

import javax.inject.Inject

class WompiRepository @Inject constructor(
    private val dataSource: WompiDataSource
) {
    suspend fun processPayment(request: WompiTransactionRequest): Result<WompiTransactionResponse> {
        return try {
            val response = dataSource.processPayment(request)
            if (response.status == WompiStatus.APPROVED) {
                Result.success(response)
            } else {
                Result.failure(Exception(response.errorMessage ?: "Transaction declined"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
