package com.itechpro.domain.usecase.calculateTotalAmount

import javax.inject.Inject
import android.util.Log

class CalculateTotalAmountUseCase @Inject constructor() {

    companion object {
        private const val TAG = "TotalAmountUseCase"
    }

    operator fun invoke(
        unitPrice: Double,
        quantity: Double,
        vat: Double,
        discount: Double,
        isPercent: Boolean
    ): Double {
        Log.d(TAG, "unitPrice: $unitPrice, quantity: $quantity, vat: $vat, discount: $discount, isPercent: $isPercent")

        val total = unitPrice * quantity

        val discountAmount = if (isPercent) {
            total * discount / 100.0
        } else {
            discount
        }

        val afterDiscount = total - discountAmount
        val vatAmount = afterDiscount * vat / 100.0

        val finalTotal = (afterDiscount + vatAmount).coerceAtLeast(0.0)

        Log.d(TAG, "→ total: $total, discountAmount: $discountAmount, vatAmount: $vatAmount, finalTotal: $finalTotal")

        return finalTotal
    }
}
