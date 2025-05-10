package com.contrast.Contrast.presentation.features.product.manager

// File: PromoCountdownManager.kt

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itechpro.domain.model.PromoUiData
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.product.ProductDetail
import com.itechpro.domain.usecase.product.PromoCountdownUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
class PromoCountdownManager @Inject constructor(
    private val scope: CoroutineScope,
    private val promoCountdownUseCase: PromoCountdownUseCase,
    private val dispatcher: CoroutineDispatcher
) {

    private var countdownJob: Job? = null

    private val _promoUiDataMap = mutableMapOf<String, MutableStateFlow<PromoUiData>>()
    val promoUiDataMap: Map<String, StateFlow<PromoUiData>> get() = _promoUiDataMap

    @RequiresApi(Build.VERSION_CODES.O)
    fun startPromoCountdown(products: List<Product>) {
        countdownJob?.cancel()
        countdownJob = scope.launch(dispatcher) {
            products.forEach { product ->
                val id = product.id ?: return@forEach
                _promoUiDataMap.getOrPut(id) { MutableStateFlow(PromoUiData()) }
            }
            while (isActive) {
                val now = LocalDateTime.now()
                products.forEach { product ->
                    val id = product.id ?: return@forEach
                    val promo = promoCountdownUseCase.calculate(product, now)
                    _promoUiDataMap[id]?.value = promo
                }
                delay(1000)
            }
        }
    }

    fun startPromoCountdownForDetail(product: ProductDetail) {
        countdownJob?.cancel()
        countdownJob = scope.launch(dispatcher) {
            val id = product.id ?: return@launch
            _promoUiDataMap.getOrPut(id) { MutableStateFlow(PromoUiData()) }
            while (isActive) {
                val now = LocalDateTime.now()
                val promo = promoCountdownUseCase.calculateProductDetail(product, now)
                _promoUiDataMap[id]?.value = promo
                delay(1000)
            }
        }
    }

    fun stop() {
        countdownJob?.cancel()
    }
}
