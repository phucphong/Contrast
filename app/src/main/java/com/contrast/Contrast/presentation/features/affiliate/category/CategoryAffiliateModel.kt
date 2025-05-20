package com.contrast.Contrast.presentation.features.affiliate.category


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.*
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.category.CategoryUiState
import com.itechpro.domain.model.navigationEvent.*
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.product.Product

import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.category.CategoryAffiliateUseCase
import com.itechpro.domain.usecase.product.PromoCountdownUseCase
import com.itechpro.domain.usecase.product.StartPromoCountdownUseCase
import com.itechpro.domain.usecase.sell.SellConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CategoryAffiliateModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: CategoryAffiliateUseCase,
    private val sellConfigUseCase: SellConfigUseCase,
    private var startPromoCountdownUseCase: StartPromoCountdownUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryUiState())
    val state: StateFlow<CategoryUiState> = _state

    private val _promoUiDataMap = mutableMapOf<String, MutableStateFlow<PromoUiData>>()
    val promoUiDataMap: Map<String, StateFlow<PromoUiData>> get() = _promoUiDataMap

    private var countdownJob: Job? = null
    private var currentUser: CurrentUserInfo? = null


    init {
        viewModelScope.launch(dispatcher) {
            runCatching { getCurrentUserUseCase() }
                .onSuccess {
                    currentUser = it
                    _state.update { state ->
                        state.copy(
                            domain = it.domain,
                            token = it.token,
                            pointAffiliate = it.pointAffiliate,
                            displayProduct = it.displayProduct,
                            displayService = it.displayService,
                            displayPriority = it.displayPriority
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(errorMessage = stringProvider.getString(R.string.error_connection) )
                    }
                }
        }
    }

    fun initCategory(
        displayProduct: String,
        displayService: String,
        displayPriority: String,
        categoryId: String
    ) {
        val config = sellConfigUseCase.generateConfig(displayProduct, displayService, displayPriority)

        _state.update {
            it.copy(
                tabs = config.tabs,
                type = config.type,
                objApi = config.objApi,
                modeApi = config.modeApi
            )
        }

        // Tiếp tục gọi cấp danh mục đầu tiên
        getCategory1( config.type, categoryId)
    }


    fun onItemNotificationSelected() {
        _state.update { it.copy(navEvent = NotificationNavEvent.GoToNotifications("", "")) }
    }
    fun searchLocal(textSearch: String, list: List<Product>) {
        viewModelScope.launch {
            useCase.searchLocal(textSearch, list).collect { response ->
                when (response) {
                    is NetworkResponse.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is NetworkResponse.Success -> {
                        _state.update { it.copy(pagedProducts = response.data, isLoading = false) }
                    }
                    is NetworkResponse.Error -> {
                        _state.update { it.copy(isLoading = false, errorMessage = response.message ?: "Lỗi không xác định") }
                    }
                }
            }
        }
    }


    fun onItemCarts() {
        _state.update { it.copy(navEvent = CartNavEvent.GoToCats) }
    }

    fun onCategorySelected(index: Int, categoryList: List<Category>, categoryId: String) {
        if (state.value.selectedTab != index) {
            _state.update { it.copy(selectedTab = index, selectedTab1 = 0, selectedTab2 = 0, selectedTab3 = 0) }
            val type = categoryList.getOrNull(index)?.code.orEmpty()
            _state.update { it.copy(type = type) }
            getCategory1(type, categoryId)
        }
    }

    fun onCategory1Selected(index: Int, categoryList: List<Category>, type: String) {
        _state.update { it.copy(selectedTab1 = index, selectedTab2 = 0, selectedTab3 = 0) }
        val id = categoryList.getOrNull(index)?.id.orEmpty()
        getCategory2(type, id)
    }

    fun onCategory2Selected(index: Int, categoryList: List<Category>, type: String) {
        _state.update { it.copy(selectedTab2 = index, selectedTab3 = 0) }
        val id = categoryList.getOrNull(index)?.id.orEmpty()

        getCategory3(type, id)
    }

    fun onCategory3Selected(index: Int, categoryList: List<Category>, type: String) {
        _state.update { it.copy(selectedTab3 = index) }
        val id = categoryList.getOrNull(index)?.id.orEmpty()
        getProductsByIdParent(type, id)
    }

    fun onItemProductSelected(product: Product) {
        _state.update {
            it.copy(navEvent = ProductNavEvent.GoToProductDetail(
                id = product.id ?: "",
                idUnit = product.iddonvichuan ?: "",
                introducerId = "0"
            ))
        }
    }

    fun onAddServiceRequestSelected(product: Product) {
        _state.update {
            it.copy(navEvent = ProductNavEvent.GoToAddServiceRequest(
                id = product.id ?: "",
                serviceName = product.ten ?: "",
                idUnit = product.iddonvichuan ?: "",
                discount = product.iddonvichuan ?: ""
            ))
        }
    }

    private fun getCategory1(type: String, categoryId: String) {
        val user = currentUser ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getCategory("tatcanhomsp", "tatcanhomsp", type, categoryId, user.token)
                .collectResponse(
                    dispatcher = dispatcher,
                    onSuccess = { category1 ->
                        _state.update { it.copy(category1 = category1) }
                        val id1 = category1.firstOrNull()?.id.orEmpty()

                        if (category1.isNullOrEmpty()) {
                            getProductsByIdParent(type, categoryId) // không có cấp 1 thì lấy luôn
                        } else {
                            getCategory2(type, id1) // gọi tiếp cấp 2
                        }

                    },
                    onError = { message ->
                        _state.update { it.copy(errorMessage = message) }
                    }
                )
        }
    }

    private fun getCategory2(type: String, categoryId: String) {
        val user = currentUser ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getCategory( "tatcanhomsp", "tatcanhomsp", type, categoryId, user.token)
                .collectResponse(
                    dispatcher = dispatcher,
                    onSuccess = { category2 ->
                        _state.update { it.copy(category2 = category2) }
                        val id2 = category2.firstOrNull()?.id.orEmpty()


                        if (category2.isNullOrEmpty()) {
                            getProductsByIdParent(type, categoryId) // không có cấp 1 thì lấy luôn
                        } else {
                            getCategory3(type, id2) // gọi tiếp cấp 2
                        }

                    },
                    onError = { message ->
                        _state.update { it.copy(errorMessage = message) }
                    }
                )
        }
    }

    private fun getCategory3(type: String, categoryId: String) {
        val user = currentUser ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getCategory( "tatcanhomsp", "tatcanhomsp", type, categoryId, user.token)

                .collectResponse(
                    dispatcher = dispatcher,
                    onSuccess = { category3 ->
                        _state.update { it.copy(category3 = category3) }

                        val id3 = category3.firstOrNull()?.id.orEmpty()

                        if (category3.isNullOrEmpty()) {
                            getProductsByIdParent(type, categoryId) // không có cấp 1 thì lấy luôn
                        } else {
                            getProductsByIdParent(type, id3) // <-- lấy luôn sản phẩm
                        }
                    },
                    onError = { message ->
                        _state.update { it.copy(errorMessage = message) }
                    }
                )
        }
    }

    private fun getProductsByIdParent(type: String, idParent: String) {
        val user = currentUser ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, products = emptyList(), pagedProducts = emptyList()) }
            useCase.getProductsByIdParent( type, idParent, user.token)
                .collectResponse(
                    dispatcher = dispatcher,
                    onSuccess = { products ->
                        _state.update { it.copy(products = products, isLoading = false) }
                        startPromoCountdownProducts(products)
                    },
                    onError = { message ->
                        _state.update { it.copy(errorMessage = message) }
                    }
                )
        }
    }
    fun startPromoCountdownProducts(products: List<Product>) {
        countdownJob?.cancel()
        countdownJob = startPromoCountdownUseCase.startForProductList(
            products = products,
            stateMap = _promoUiDataMap,
            coroutineScope = viewModelScope,
            dispatcher = dispatcher
        )
    }


    fun setInitialProducts(products: List<Product>) {
        _state.update { it.copy(pagedProducts = products.take(10)) }
    }

    fun resetNavigation() {
        _state.update { it.copy(navEvent = ProductNavEvent.None) }
    }
}
