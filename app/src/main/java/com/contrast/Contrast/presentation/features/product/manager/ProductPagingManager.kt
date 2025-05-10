package com.contrast.Contrast.presentation.features.product.manager


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.itechpro.domain.model.product.Product

class ProductPagingManager {

    private val pageSize = 10
    private var currentPage = 0
    private var allProducts: List<Product> = emptyList()
    private var isLoadingNextPage = false

    private val _pagedProducts = MutableStateFlow<List<Product>>(emptyList())
    val pagedProducts: StateFlow<List<Product>> = _pagedProducts

    fun setInitialProducts(products: List<Product>) {
        allProducts = products
        currentPage = 1
        _pagedProducts.value = products.take(pageSize)
    }

    fun loadNextPage() {
        if (isLoadingNextPage || currentPage * pageSize >= allProducts.size) return

        isLoadingNextPage = true
        val nextPage = currentPage + 1
        val nextItems = allProducts.take(nextPage * pageSize)

        _pagedProducts.value = nextItems
        currentPage = nextPage
        isLoadingNextPage = false
    }

    fun reset() {
        currentPage = 0
        allProducts = emptyList()
        _pagedProducts.value = emptyList()
    }
}
