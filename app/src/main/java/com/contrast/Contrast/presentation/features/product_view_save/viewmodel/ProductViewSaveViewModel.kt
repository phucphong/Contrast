package com.contrast.Contrast.presentation.features.product_view_save.viewmodel


import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse
import com.contrast.Contrast.extensions.formatToYYYYMMDD
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.*
import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.income.Income
import com.itechpro.domain.model.income.ShareInComeUiState

import com.itechpro.domain.model.navigationEvent.*
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.product.PromoCountdownUseCase
import com.itechpro.domain.usecase.product_view_save.ProductViewSaveUseCase
import com.itechpro.domain.usecase.sell.SellConfigUseCase
import com.itechpro.domain.usecase.share_product_income.ShareProductInComeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ProductViewSaveViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: ProductViewSaveUseCase,
    private val sellConfigUseCase: SellConfigUseCase,
    private val promoCountdownUseCase: PromoCountdownUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(ShareInComeUiState())
    val state: StateFlow<ShareInComeUiState> = _state

    private val _promoUiDataMap = mutableMapOf<String, MutableStateFlow<PromoUiData>>()
    val promoUiDataMap: Map<String, StateFlow<PromoUiData>> get() = _promoUiDataMap

    private var currentUserInfo: CurrentUserInfo? = null
    private var countdownJob: Job? = null
    private var allProducts: List<Product> = emptyList()
    private var allOders: List<Income> = emptyList()
    private var currentPage = 0
    private val pageSize = 10
    private var isLoadingNextPage = false
    private var isLoaded = false
    private val _shareIntentFlow = MutableSharedFlow<Intent>()
    val shareIntentFlow: SharedFlow<Intent> = _shareIntentFlow

    init {
        viewModelScope.launch(dispatcher) {
            val user = runCatching { getCurrentUserUseCase() }.getOrNull()
            currentUserInfo = user
            if (user != null) {
                _state.update {
                    it.copy(
                        domain = user.domain,
                        token = user.token,
                        device = user.device,
                        pointAffiliate = user.pointAffiliate,
                        employeeId = user.employeeId,
                    )
                }
            }
        }
    }


    fun setInitialProducts(products: List<Product>) {
        allProducts = products
        currentPage = 1
        _state.update {
            it.copy(pagedProducts = products.take(pageSize))
        }
    }

    fun onCategorySelected(type :String,index: Int, tabs: List<Category>) {
        if (state.value.selectedTab != index) {
            val code = tabs.getOrNull(index)?.code.orEmpty()
            _state.update { it.copy(selectedTab = index) }
            if(type=="sanphamdaxem"){
                getLikeView("laydulieu", "laydsdaxem",code)
            }else if(type=="sanphamdaluu"){

                getLikeView("laydulieu", "laydsyeuthich",code)
            }
        }
    }

    fun loadHomeData(forceRefresh: Boolean = false,  type : String) {
        if (isLoaded && !forceRefresh) return
        isLoaded = true

        viewModelScope.launch(dispatcher) {
            try {
                val user = currentUserInfo ?: return@launch
                val config = sellConfigUseCase.generateConfig(
                    user.displayProduct,
                    user.displayService,
                    user.displayPriority
                )

                _state.update { it.copy(categoryCode =  config.tabs[0].code?:"") }
               if(type=="sanphamdaxem"){
                    _state.update { it.copy(tabs = config.tabs, type = config.type) }
                    getLikeView("laydulieu", "laydsdaxem",config.tabs[0].code?:"")
                }else if(type=="sanphamdaluu"){
                    _state.update { it.copy(tabs = config.tabs, type = config.type) }
                    getLikeView("laydulieu", "laydsyeuthich",config.tabs[0].code?:"")
                }



            } catch (e: Exception) {
                _state.update {
                    it.copy(error = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage}")
                }
            } finally {
                isLoaded = false
            }
        }
    }





    fun getLikeView(obj: String, mode: String, code: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, products = emptyList(), pagedProducts = emptyList()) }
            currentPage = 0
            allProducts = emptyList()

            useCase.getLikeView( obj, mode,user.typeAccount,code, user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    allProducts = data
                    _state.update {
                        it.copy(
                            products = data,
                            pagedProducts = data.take(pageSize),
                            isLoading = false
                        )
                    }
                    startPromoCountdown(data)
                },
                onError = { message ->
                    _state.update { it.copy(isLoading = false, error = message) }
                }
            )
        }
    }



    fun startPromoCountdown(products: List<Product>) {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch(dispatcher) {
            products.forEach { product ->
                val id = product.id ?: return@forEach
                _promoUiDataMap.getOrPut(id) { MutableStateFlow(PromoUiData()) }
            }
            while (isActive) {
                val now = LocalDateTime.now()
                products.forEach { product ->
                    val id = product.id ?: return@forEach
                    _promoUiDataMap[id]?.value = promoCountdownUseCase.calculate(product, now)
                }
                delay(1000)
            }
        }
    }

    fun loadNextPage() {
        if (isLoadingNextPage || currentPage * pageSize >= allProducts.size) return
        isLoadingNextPage = true
        currentPage++
        _state.update { it.copy(pagedProducts = allProducts.take(currentPage * pageSize)) }
        isLoadingNextPage = false
    }

    fun onTabSelected(index: Int, category: Category) {
        _state.update { it.copy(selectedTab = index, navEvent = ProductNavEvent.GoToProductsCategory(category.id ?: "")) }
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


    fun onItemCarts() {
        if (currentUserInfo?.token?.isEmpty() == true) {
            // Chuyển màn hình login từ Activity
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        navEvent = SplashNaEvent.GoToLogIn("1")
                    )
                }
            }
        } else {
            _state.update { it.copy(navEvent = CartNavEvent.GoToCats) }}
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

    fun resetNavigation() {
        _state.update { it.copy(navEvent = ProductNavEvent.None) }
    }

    override fun onCleared() {
        countdownJob?.cancel()
        super.onCleared()
    }
}
