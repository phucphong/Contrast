package com.contrast.Contrast.presentation.features.share.viewModel

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
import com.itechpro.domain.usecase.sell.SellConfigUseCase
import com.itechpro.domain.usecase.share_product_income.ShareProductInComeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ShareProductViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: ShareProductInComeUseCase,
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

    fun gotoHome() {


        _state.update {
            it.copy(navEvent = HomeNavEvent.GoToHome)
        }
    }
    fun setInitialProducts(products: List<Product>) {
        allProducts = products
        currentPage = 1
        _state.update {
            it.copy(pagedProducts = products.take(pageSize))
        }
    }
    fun setInitialOders(oders: List<Income>) {
        allOders = oders
        currentPage = 1
        _state.update {
            it.copy(pagedOders = oders.take(pageSize))
        }
    }
    fun onCategorySelected(index: Int, tabs: List<Category>) {
        if (state.value.selectedTab != index) {
            val code = tabs.getOrNull(index)?.code.orEmpty()
            _state.update { it.copy(selectedTab = index) }
            getProductsByIdParent(code, "0","")
        }
    }
    fun shareProduct(shareLink: String) {


        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareLink)
        }
        // Emit intent ra để Composable xử lý
        viewModelScope.launch {
            _shareIntentFlow.emit(Intent.createChooser(shareIntent, "Chia sẻ sản phẩm"))
        }
    }
    fun loadHomeData(forceRefresh: Boolean = false,  type : String) {
        if (isLoaded && !forceRefresh) return
        isLoaded = true

        viewModelScope.launch(dispatcher) {
            try {
                val user = currentUserInfo ?: return@launch

                if(type=="layhoahongthucte"){
                    _state.update { it.copy(tabs = useCase.generateCategory()) }
                }else if(type=="layhoahongtamtinh"){
                    val config = sellConfigUseCase.generateConfig(
                        user.displayProduct.orEmpty(),
                        user.displayService.orEmpty(),
                        user.displayPriority.orEmpty()
                    )
                    _state.update { it.copy(tabs = config.tabs, type = config.type) }

                    getProductsByIdParent(config.type, "0","")
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




    fun getProductsByIdParent(type: String, idParent: String, searchText: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, products = emptyList(), pagedProducts = emptyList()) }
            currentPage = 0
            allProducts = emptyList()
            Log.e("getProductsByIdParent",user.token ?: "")
            useCase.getProductsByIdParent( type, idParent,searchText, user.token).collectResponse(
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



    fun getReportShareLink(mode: String, startDate: String, endDate: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, products = emptyList(), pagedProducts = emptyList()) }
            currentPage = 0
            allProducts = emptyList()
//mode: String,startDate: String,endDate: String,authen: String
            useCase.getReportShareLink( mode,   formatToYYYYMMDD(startDate),
                formatToYYYYMMDD(endDate),
                user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = {
                        result ->
                    if (result != null) {
                        _state.update {
                            it.copy(
                                diemtamtinh = result.diemtamtinh?:0.0,
                                soluongdonhang = result.soluongdonhang?:0.0,
                                sotiendoanhthu = result.sotiendoanhthu?:0.0,
                                sotientamtinhhuong = result.sotientamtinhhuong?:0.0,
                                sotienhuong = result.sotienhuong?:0.0,
                                isLoading = false
                            )
                        }
                    }

                },
                onError = { message ->
                    _state.update { it.copy(isLoading = false, error = message) }
                }


            )
        }
    }
    fun getActualCommissionByIdOder(status: String,mode: String, startDate: String, endDate: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true, products = emptyList(), pagedProducts = emptyList()) }
            currentPage = 0
            allOders = emptyList()
//mode: String,startDate: String,endDate: String,authen: String
            useCase.getActualCommissionByIdOder( status,mode,   formatToYYYYMMDD(startDate),
                formatToYYYYMMDD(endDate),
                user.token).collectResponse(
                dispatcher = dispatcher,
                onSuccess = { data ->
                    allOders = data
                    _state.update {
                        it.copy(
                            oders = data,
                            pagedOders = data.take(pageSize),
                            isLoading = false
                        )
                    }
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
