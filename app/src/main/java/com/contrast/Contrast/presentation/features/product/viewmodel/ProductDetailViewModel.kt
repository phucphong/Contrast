package com.contrast.Contrast.presentation.features.product.viewmodel

import androidx.lifecycle.ViewModel

import com.itechpro.domain.model.product.OptionItem


// ViewModel
class ProductDetailViewModel : ViewModel() {
    val sizes = listOf("Size S", "Size M", "Size L").mapIndexed { i, s -> OptionItem("$i", s) }
    val sugarLevels = listOf("Ngọt bình thường", "Ít ngọt").mapIndexed { i, s -> OptionItem("$i", s) }
    val iceLevels = listOf("Đá bình thường", "Ít đá", "Đá riêng", "Không đá").mapIndexed { i, s -> OptionItem("$i", s) }
    val toppings = listOf(
        OptionItem("1", "Trân châu phủ mai đào", 15000),
        OptionItem("2", "Bánh flan", 10000),
        OptionItem("3", "Kem sữa phủ mai", 15000),
        OptionItem("4", "Thạch chuối", 15000)
    )
}