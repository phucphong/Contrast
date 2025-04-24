package com.itechpro.domain.usecase.product

import android.os.Build
import androidx.annotation.RequiresApi
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import com.itechpro.domain.model.product.ProductDetail
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
@RequiresApi(Build.VERSION_CODES.O)
class PromoCountdownUseCase @Inject constructor() {


    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")


    fun calculate(product: Product, now: LocalDateTime = LocalDateTime.now()): PromoUiData {
        val start = runCatching {
            LocalDateTime.parse(product.tungay ?: "", formatter)
        }.getOrNull()

        val end = runCatching {
            LocalDateTime.parse(product.denngay ?: "", formatter)
        }.getOrNull()

        if (start == null || end == null) {
            return PromoUiData(1f, "00:00:00")
        }

        return when {
            now.isAfter(end) -> PromoUiData(1f, "00:00:00")
            now.isBefore(start) -> PromoUiData(0f, "00:00:00")
            else -> {
                val total = Duration.between(start, end).toMillis().toFloat()
                val elapsed = Duration.between(start, now).toMillis().coerceAtLeast(0)
                val progress = (elapsed / total).coerceIn(0f, 1f)

                val remain = Duration.between(now, end).coerceAtLeast(Duration.ZERO)
                val h = remain.toHours()
                val m = remain.toMinutes() % 60
                val s = remain.seconds % 60
                val timeStr = String.format("%02d:%02d:%02d", h, m, s)

                PromoUiData(progress, timeStr)
            }
        }
    }
    fun calculateProductDetail(product: ProductDetail, now: LocalDateTime = LocalDateTime.now()): PromoUiData {
        val start = runCatching {
            LocalDateTime.parse(product.tungay ?: "", formatter)
        }.getOrNull()

        val end = runCatching {
            LocalDateTime.parse(product.denngay ?: "", formatter)
        }.getOrNull()

        if (start == null || end == null) {
            return PromoUiData(1f, "00:00:00")
        }

        return when {
            now.isAfter(end) -> PromoUiData(1f, "00:00:00")
            now.isBefore(start) -> PromoUiData(0f, "00:00:00")
            else -> {
                val total = Duration.between(start, end).toMillis().toFloat()
                val elapsed = Duration.between(start, now).toMillis().coerceAtLeast(0)
                val progress = (elapsed / total).coerceIn(0f, 1f)

                val remain = Duration.between(now, end).coerceAtLeast(Duration.ZERO)
                val h = remain.toHours()
                val m = remain.toMinutes() % 60
                val s = remain.seconds % 60
                val timeStr = String.format("%02d:%02d:%02d", h, m, s)

                PromoUiData(progress, timeStr)
            }
        }
    }
}



