package com.itechpro.domain.model.navigationEvent


sealed class ProductNavEvent: NavEvent {
    data class GoToProductsCategory(val categoryId: String) : ProductNavEvent()
    data class GoToNotifications(val categoryId: String) : ProductNavEvent()

    data class GoToAddServiceRequest(
        val id: String,
        val serviceName: String,
        val idUnit: String,
        val discount: String
    ) : ProductNavEvent()
    data class GoToProductDetail(
        val id: String,
        val idUnit: String,
    ) : ProductNavEvent()


    object None : ProductNavEvent() // trạng thái mặc định
}
