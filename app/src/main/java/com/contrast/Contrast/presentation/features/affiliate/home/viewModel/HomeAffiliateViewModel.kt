// ✅ Refactored HomeAffiliateViewModel to follow structure similar to ProductViewModel

package com.contrast.Contrast.presentation.features.affiliate.home.viewModel

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
import com.itechpro.domain.model.home.HomeUiState
import com.itechpro.domain.model.navigationEvent.*
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.home.HomeAffiliateUseCase
import com.itechpro.domain.usecase.product.PromoCountdownUseCase
import com.itechpro.domain.usecase.sell.SellConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeAffiliateViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: HomeAffiliateUseCase,
    private val sellConfigUseCase: SellConfigUseCase,
    private val promoCountdownUseCase: PromoCountdownUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state

    private val _promoUiDataMap = mutableMapOf<String, MutableStateFlow<PromoUiData>>()
    val promoUiDataMap: Map<String, StateFlow<PromoUiData>> get() = _promoUiDataMap

    private var currentUserInfo: CurrentUserInfo? = null
    private var countdownJob: Job? = null
    private var allProducts: List<Product> = emptyList()
    private var currentPage = 0
    private val pageSize = 10
    private var isLoadingNextPage = false
    private var isLoaded = false


    init {
        viewModelScope.launch(dispatcher) {
            val user = runCatching { getCurrentUserUseCase() }.getOrNull()
            currentUserInfo = user
            if (user != null) {
                _state.update {
                    it.copy(
                        domain = user.domain.orEmpty(),
                        token = user.token.orEmpty(),
                        pointAffiliate = user.pointAffiliate.orEmpty()
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
    fun onCategorySelected(index: Int, tabs: List<Category>) {
        if (state.value.selectedTab != index) {
            val code = tabs.getOrNull(index)?.code.orEmpty()
            _state.update { it.copy(selectedTab = index) }
            getProductsByIdParent(code, "0")
        }
    }

    fun loadHomeData(forceRefresh: Boolean = false) {
        if (isLoaded && !forceRefresh) return
        isLoaded = true

        viewModelScope.launch(dispatcher) {
            try {
                val user = currentUserInfo ?: return@launch

                val config = sellConfigUseCase.generateConfig(
                    user.displayProduct.orEmpty(),
                    user.displayService.orEmpty(),
                    user.displayPriority.orEmpty()
                )


                _state.update {
                    it.copy(
                        tabs = config.tabs,
                        type = config.type
                    )
                }

                val elapsed = measureTimeMillis {
                    awaitAll(
                        async { getSlideHome("sanphamtrangchu", "modeslide") },
                        async { getCategory("tatcanhomsp", "tatcanhomsp", "", "0") },
                        async { getFlashSale() },
                        async { getProductsByIdParent(config.type, "0") },
                        async { getRotation(user.typeAccount.orEmpty()) }
                    )
                }
                Log.d("Timing", "✅ All API loaded in $elapsed ms")
            } catch (e: Exception) {
                _state.update {
                    it.copy(validationError = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage}")
                }
            } finally {
                isLoaded = false
            }
        }
    }


    private suspend fun getSlideHome(obj: String, mode: String) {
        val user = currentUserInfo ?: return

        Log.e("user.isOfflineMode",user.isOfflineMode.toString())
        useCase.getSlideHome(user.isOfflineMode, obj, mode, user.token).collectResponse(
            dispatcher = dispatcher,
            onSuccess = { data ->
                _state.update { it.copy(slides = data) }
            },
            onError = { message ->
                _state.update { it.copy(validationError = message) }
            }
        )
    }


    private suspend fun getCategory(obj: String, mode: String, type: String, idParent: String) {
        val user = currentUserInfo ?: return
        useCase.getCategory(user.isOfflineMode, obj, mode, type, idParent, user.token).collectResponse(
            dispatcher = dispatcher,
            onSuccess = { data ->
                _state.update { it.copy(categorys = data) }
            },
            onError = { message ->
                _state.update { it.copy(validationError = message) }
            }
        )
    }

    private suspend fun getFlashSale() {
        val user = currentUserInfo ?: return
        useCase.getFlashSale(user.isOfflineMode, user.token).collectResponse(
            dispatcher = dispatcher,
            onSuccess = { data ->
                _state.update { it.copy(flashSales = data) }
            },
            onError = { message ->
                _state.update { it.copy(validationError = message) }
            }
        )
    }

    private suspend fun getRotation(type: String) {
        val user = currentUserInfo ?: return
        useCase.getRotation(type, user.token).collectResponse(
            dispatcher = dispatcher,
            onSuccess = { /* Optional: handle data if needed */ },
            onError = { message ->
                _state.update { it.copy(validationError = message) }
            }
        )
    }

    fun getProductsByIdParent(type: String, idParent: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, products = emptyList(), pagedProducts = emptyList()) }
            currentPage = 0
            allProducts = emptyList()

            useCase.getProductsByIdParent(user.isOfflineMode, type, idParent, user.token).collectResponse(
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
                    _state.update { it.copy(isLoading = false, validationError = message) }
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

    fun onItemNotificationSelected() {
        if (currentUserInfo?.isOfflineMode?:false) {
            // Chuyển màn hình login từ Activity
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        navEvent = SplashNaEvent.GoToLogIn("1")
                    )
                }
            }
        } else {
        _state.update { it.copy(navEvent = NotificationNavEvent.GoToNotifications("", "")) }}
    }

    fun onItemCarts() {
        if (currentUserInfo?.isOfflineMode?:false) {
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
        if (currentUserInfo?.isOfflineMode?:false) {
            // Chuyển màn hình login từ Activity
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        navEvent = SplashNaEvent.GoToLogIn("1")
                    )
                }
            }
        } else {
        _state.update {
            it.copy(navEvent = ProductNavEvent.GoToAddServiceRequest(
                id = product.id ?: "",
                serviceName = product.ten ?: "",
                idUnit = product.iddonvichuan ?: "",
                discount = product.iddonvichuan ?: ""
            ))
        }}
    }

    fun resetNavigation() {
        _state.update { it.copy(navEvent = ProductNavEvent.None) }
    }

    override fun onCleared() {
        countdownJob?.cancel()
        super.onCleared()
    }
}
