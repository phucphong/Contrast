package com.itechpro.domain.model.navigationEvent


sealed class ProductNavEvent: NavEvent {
    data class GoToProductsCategory(val categoryId: String) : ProductNavEvent()
    data class GoToNotifications(val categoryId: String) : ProductNavEvent()
    object GoToLogin : ProductNavEvent()
    data class GoToAddServiceRequest(
        val id: String,
        val serviceName: String,
        val idUnit: String,
        val discount: String
    ) : ProductNavEvent()
    data class GoToProductDetail(
        val id: String,
        val idUnit: String,
        val introducerId: String,
    ) : ProductNavEvent()
    data class GoToProductReviews(
        val id: String
    ) : ProductNavEvent()
    data class GoToAddReviews(
        val id: String,
        val idUnit: String,
        val fileTxt: String,
        val name: String,

    ) : ProductNavEvent()
    data class GoToReportProduct(
        val id: String,
        val idUnit: String,
        val fileTxt: String,
        val name: String,

    ) : ProductNavEvent()


    object None : ProductNavEvent() // trạng thái mặc định
}
