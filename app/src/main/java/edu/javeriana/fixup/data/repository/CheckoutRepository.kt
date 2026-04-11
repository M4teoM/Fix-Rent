package edu.javeriana.fixup.data.repository

import edu.javeriana.fixup.data.datasource.interfaces.CheckoutDataSource
import edu.javeriana.fixup.ui.features.checkout.CheckoutItemUiModel
import javax.inject.Inject

class CheckoutRepository @Inject constructor(
    private val dataSource: CheckoutDataSource
) {
    fun getCheckoutItems(): Result<List<CheckoutItemUiModel>> {
        return try {
            Result.success(dataSource.getCheckoutItems())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
