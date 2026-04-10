package edu.javeriana.fixup.data.datasource.interfaces

import edu.javeriana.fixup.ui.features.checkout.CheckoutItemUiModel

/**
 * Contrato del Data Source para Checkout.
 */
interface CheckoutDataSource {
    fun getCheckoutItems(): List<CheckoutItemUiModel>
}
