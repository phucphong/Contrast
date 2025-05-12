package com.itechpro.domain.usecase.product

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.itechpro.domain.model.PromoUiData
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.product.ProductDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
@RequiresApi(Build.VERSION_CODES.O)
class StartPromoCountdownUseCase @Inject constructor(
    private val promoCountdownUseCase: PromoCountdownUseCase
) {


    fun startForProductList(
        products: List<Product>,
        stateMap: MutableMap<String, MutableStateFlow<PromoUiData>>,
        coroutineScope: CoroutineScope,
        dispatcher: CoroutineDispatcher
    ): Job {
        return coroutineScope.launch(dispatcher) {
            products.forEach { product ->
                val id = product.id ?: return@forEach
                stateMap.getOrPut(id) { MutableStateFlow(PromoUiData()) }
            }

            while (isActive) {
                val now = LocalDateTime.now()
                products.forEach { product ->
                    val id = product.id ?: return@forEach
                    val promo = promoCountdownUseCase.calculate(product, now)
                    stateMap[id]?.value = promo
                }
                delay(1000)
            }
        }
    }

    fun startForProductDetail(
        product: ProductDetail,
        stateMap: MutableMap<String, MutableStateFlow<PromoUiData>>,
        coroutineScope: CoroutineScope,
        dispatcher: CoroutineDispatcher
    ): Job? {
        val id = product.id ?: return null
        return coroutineScope.launch(dispatcher) {
            stateMap.getOrPut(id) { MutableStateFlow(PromoUiData()) }

            while (isActive) {
                val now = LocalDateTime.now()
                val promo = promoCountdownUseCase.calculateProductDetail(product, now)
                stateMap[id]?.value = promo
                delay(1000)
            }


        }
    }
}
