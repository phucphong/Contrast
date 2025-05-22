package com.itechpro.domain.model.income

import com.itechpro.domain.model.home.SliderHome



import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.income.Income
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class ShareInComeUiState(
    val slides: List<SliderHome> = emptyList(),

    val categorys: List<Category> = emptyList(),
    val flashSales: List<Product> = emptyList(),
    val tabs: List<Category> = emptyList(),
    val products: List<Product> = emptyList(),
    val pagedProducts: List<Product> = emptyList(),
    val oders: List<Income> = emptyList(),
    val pagedOders: List<Income> = emptyList(),
    val rotations: List<Rotation> = emptyList(),
    val domain: String = "",
    val pointAffiliate: String = "",
    val token: String = "",
    val displayProduct: String = "",
    val displayService: String = "",
    val displayPriority: String = "",
    val employeeId: String = "",
    val device: String = "",
    val categoryCode: String = "",
    val error: String? = null,
    val type: String = "",
    val selectedTab: Int = 0,
    val diemtamtinh: Double = 0.0,
    val soluongdonhang: Double = 0.0,
    val sotiendoanhthu: Double = 0.0,
    val sotientamtinhhuong: Double = 0.0,
    val sotienhuong: Double = 0.0,
    val isLoading: Boolean = false,

    val navEvent: NavEvent = ProductNavEvent.None,
)
