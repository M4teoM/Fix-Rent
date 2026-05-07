package edu.javeriana.fixup.data.wompi

import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WompiDataSourceImpl @Inject constructor() : WompiDataSource {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    override suspend fun processPayment(request: WompiTransactionRequest): WompiTransactionResponse {
        // Simular retraso de red
        delay(2000)

        val status = when (request.paymentMethod) {
            is WompiPaymentMethod.Card -> {
                // Simular fallo si el número de tarjeta termina en "9999"
                if (request.paymentMethod.cardNumber.endsWith("9999")) {
                    WompiStatus.DECLINED
                } else {
                    WompiStatus.APPROVED
                }
            }
            is WompiPaymentMethod.Nequi -> WompiStatus.APPROVED
        }

        return WompiTransactionResponse(
            id = UUID.randomUUID().toString(),
            status = status,
            amountInCents = request.amountInCents,
            reference = request.reference,
            createdAt = dateFormat.format(Date()),
            errorMessage = if (status == WompiStatus.DECLINED) "Transacción rechazada por el emisor" else null
        )
    }

    override suspend fun getTransactionStatus(id: String): WompiTransactionResponse {
        delay(500)
        return WompiTransactionResponse(
            id = id,
            status = WompiStatus.APPROVED,
            amountInCents = 100000,
            reference = "REF-TEST",
            createdAt = dateFormat.format(Date())
        )
    }
}
