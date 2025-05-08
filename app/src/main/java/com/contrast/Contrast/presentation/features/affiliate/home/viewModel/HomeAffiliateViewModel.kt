package com.contrast.Contrast.presentation.features.affiliate.home.viewModel




import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.utils.NetworkMonitor
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.PromoUiData
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.SliderHome
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent

import com.itechpro.domain.usecase.account.GetCurrentUserUseCase

import com.itechpro.domain.usecase.home.HomeAffiliateUseCase
import com.itechpro.domain.usecase.product.PromoCountdownUseCase
import com.itechpro.domain.usecase.sell.SellConfigUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeAffiliateViewModel @Inject constructor(private val getCurrentUserUseCase: GetCurrentUserUseCase,
                                                 private val useCase: HomeAffiliateUseCase,
                                                 private val sellConfigUseCase: SellConfigUseCase,
                                                 private val promoCountdownUseCase: PromoCountdownUseCase,
                                                 private val stringProvider: StringProvider,
                                                 @IoDispatcher private val dispatcher: CoroutineDispatcher,) : ViewModel() {





    private val _obj = MutableStateFlow<Product?>(null)
    val obj: StateFlow<Product?> = _obj


    private val _categorys = MutableStateFlow<List<Category>>(emptyList())
    val categorys: StateFlow<List<Category>> = _categorys

    private val _flashSales = MutableStateFlow<List<Product>>(emptyList())
    val flashSales: StateFlow<List<Product>> = _flashSales
    private val _slides = MutableStateFlow<List<SliderHome>>(emptyList())
    val slides: StateFlow<List<SliderHome>> = _slides
    private val _tabs = MutableStateFlow<List<Category>>(emptyList())
    val tabs: StateFlow<List<Category>> = _tabs
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _rotations = MutableStateFlow<List<Rotation>>(emptyList())
    val rotations: StateFlow<List<Rotation>> = _rotations


    private val _validationError = MutableStateFlow<String>("")
    val validationError: StateFlow<String> = _validationError
    private val _domain = MutableStateFlow<String>("")
    val domain: StateFlow<String> = _domain
        private val _token = MutableStateFlow<String>("")
    val token: StateFlow<String> = _token
        private val _pointAffiliate = MutableStateFlow<String>("")
    val pointAffiliate: StateFlow<String> = _pointAffiliate


    private val _displayProduct = MutableStateFlow<String>("")
    private val _displayService = MutableStateFlow<String>("")

    private val _displayPriority = MutableStateFlow<String>("")

    private var isLoaded = false
    private val _type = MutableStateFlow<String>("")
    val type: StateFlow<String> = _type
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private var currentUserInfo: CurrentUserInfo? = null

    private val _navigationEvent = MutableStateFlow<NavEvent>(ProductNavEvent.None)
    val navigationEvent: StateFlow<NavEvent> = _navigationEvent

    val isOnline = NetworkMonitor.isOnline

    private val _promoUiDataMap = mutableMapOf<String, MutableStateFlow<PromoUiData>>()
    val promoUiDataMap: Map<String, StateFlow<PromoUiData>>
        get() = _promoUiDataMap


    private var countdownJob: Job? = null
    private val _pagedProducts = MutableStateFlow<List<Product>>(emptyList())
    val pagedProducts: StateFlow<List<Product>> = _pagedProducts

    private var allProducts: List<Product> = emptyList()
    private var currentPage = 0
    private val pageSize = 10


    fun setInitialProducts(products: List<Product>) {
        allProducts = products
        currentPage = 1
        _pagedProducts.value = products.take(pageSize)
    }

    private var isLoadingNextPage = false

    fun loadNextPage() {
        if (isLoadingNextPage || currentPage * pageSize >= allProducts.size) return

        isLoadingNextPage = true
        val nextPage = currentPage + 1
        val nextItems = allProducts.take(nextPage * pageSize)

        _pagedProducts.value = nextItems
        currentPage = nextPage

        // Nếu có delay fake load, có thể đặt trong coroutine rồi reset
        isLoadingNextPage = false
    }

    fun startPromoCountdown(products: List<Product>) {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch(dispatcher) {
            // Khởi tạo state cho từng item nếu chưa có
            products.forEach { product ->
                val id = product.id ?: return@forEach
                _promoUiDataMap.getOrPut(id) { MutableStateFlow(PromoUiData()) }
            }
            while (isActive) {
                val now = LocalDateTime.now()
                products.forEach { product ->
                    val id = product.id ?: return@forEach
                    val promo = promoCountdownUseCase.calculate(product, now)
                    _promoUiDataMap[id]?.value = promo
                }
                delay(1000)
            }
        }
    }


    override fun onCleared() {
        countdownJob?.cancel()
        super.onCleared()
    }


    fun onTabSelected(index: Int, category: Category) {
        _selectedTab.value =index

        _navigationEvent.value = ProductNavEvent.GoToProductsCategory(category.id?:"")

    }

    fun onItemProductSelected( category: Product) {
        _navigationEvent.value = ProductNavEvent.GoToProductDetail(
            id = category.id ?: "",
            idUnit = category.iddonvichuan ?: "",
            introducerId = "0",

        )


    }



    fun onItemNotificationSelected( ) {
        _navigationEvent.value = NotificationNavEvent.GoToNotifications(
            startDate = "",
            endDate = "",

        )
    }
    fun onItemCarts( ) {
        _navigationEvent.value = CartNavEvent.GoToCats
    }

    fun onAddServiceRequestSelected( category: Product) {
        _navigationEvent.value = ProductNavEvent.GoToAddServiceRequest(
            id = category.id ?: "",
            serviceName = category.ten ?: "",
            idUnit = category.iddonvichuan ?: "",
            discount = category.iddonvichuan ?: ""
        )


    }


    // Sau khi navigate xong, reset lại state
    fun resetNavigation() {
        _navigationEvent.value = ProductNavEvent.None
    }


    fun loadHomeData(forceRefresh: Boolean = false) {
        if (isLoaded && !forceRefresh) return
        isLoaded = true
        viewModelScope.launch(dispatcher) {
            try {
                currentUserInfo = getCurrentUserUseCase()
                val user = currentUserInfo ?: return@launch

                _domain.value = user.domain.orEmpty()
                _token.value = user.token.orEmpty()
                _pointAffiliate.value = user.pointAffiliate.orEmpty()
                _displayProduct.value = user.displayProduct.orEmpty()
                _displayService.value = user.displayService.orEmpty()
                _displayPriority.value = user.displayPriority.orEmpty()

                val result = sellConfigUseCase.generateConfig(
                    user.displayProduct.orEmpty(),
                    user.displayService.orEmpty(),
                    user.displayPriority.orEmpty()
                )

                _tabs.value = result.tabs
                _type.value = result.type

                Log.d("Timing", "🚀 Bắt đầu loadHomeData song song")

                val totalDuration = measureTimeMillis {
                    val slideJob = async {
                        measureAndRetry("getSlideHome") {
                            getSlideHome("sanphamtrangchu", "modeslide")
                        }
                    }
                    val categoryJob = async {
                        measureAndRetry("getCategory") {
                            getCategory("tatcanhomsp", "tatcanhomsp", "", "0")
                        }
                    }
                    val flashJob = async {
                        measureAndRetry("getFlashSale") {
                            getFlashSale()
                        }
                    }
                    val productJob = async {
                        measureAndRetry("getProductsByIdParent") {
                            getProductsByIdParent(result.type, "0")
                        }
                    }
                    val rotationJob = async {
                        measureAndRetry("getRotation") {
                            getRotation(user.typeAccount.orEmpty())
                        }
                    }

                    awaitAll(slideJob, categoryJob, flashJob, productJob, rotationJob)
                }

                Log.d("Timing", "✅ Tất cả API hoàn tất trong ${totalDuration}ms")

            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
                Log.e("loadHomeData", "❌ Exception tổng: ${e.localizedMessage}")
            } finally {
                isLoaded = false
                _isLoading.value = false
            }
        }
    }
    private suspend fun measureAndRetry(tag: String, block: suspend () -> Unit) {
        val start = System.currentTimeMillis()
        try {
            block()
            Log.d("Timing", "✅ $tag thành công trong ${System.currentTimeMillis() - start}ms")
        } catch (e: Exception) {
            Log.w("Timing", "⚠️ $tag lỗi: ${e.message}, retry sau 1s")
            delay(1000)
            try {
                block()
                Log.d("Timing", "🔁 $tag retry thành công trong ${System.currentTimeMillis() - start}ms")
            } catch (ex: Exception) {
                Log.e("Timing", "❌ $tag retry thất bại sau ${System.currentTimeMillis() - start}ms: ${ex.message}")
            }
        }
    }





    fun onCategorySelected(index: Int, categoryList: List<Category>) {
        if (_selectedTab.value != index) {
            _selectedTab.value = index
            val code = categoryList.getOrNull(index)?.code.orEmpty()
            _type.value = code
            getProductsByIdParent(code, "0")
        }
    }




    // lấy danh mực 3 cấp
    fun getCategory(obj: String,mode: String,type: String,idParent: String) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getCategory(user.isOfflineMode, obj,mode,type,idParent ,user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {

                        }
                        is NetworkResponse.Success -> {

                            _categorys.value = result.data

                        }
                        is NetworkResponse.Error -> {

                            _validationError.value = result.message
                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    // lấy danh mực 3 cấp
    fun getFlashSale() {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getFlashSale(user.isOfflineMode, user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                        }
                        is NetworkResponse.Success -> {
                            _flashSales.value = result.data
                            if(selectedTab.value>0){
                                _selectedTab.value = 0
                            }

                        }
                        is NetworkResponse.Error -> {
                            _validationError.value = result.message
                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    fun getSlideHome(obj: String,mode: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {

            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getSlideHome(user.isOfflineMode, obj,mode,user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                        }
                        is NetworkResponse.Success -> {
                            _slides.value = result.data

                        }
                        is NetworkResponse.Error -> {
                            _validationError.value = result.message
                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    fun getProductsByIdParent(type: String,idParent: String) {
        val user = currentUserInfo ?: return


        viewModelScope.launch(dispatcher) {
            try {

                // ✅ RESET phân trang mỗi lần gọi mới
                _products. value = emptyList()
                _pagedProducts.value = emptyList()

                currentPage = 0
                allProducts = emptyList()
                useCase.getProductsByIdParent(user.isOfflineMode,type, idParent,  user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                            _isLoading.value = true
                        }
                        is NetworkResponse.Success -> {
                            _isLoading.value = false
                            _products.value = result.data

                            startPromoCountdown(result.data)
                        }
                        is NetworkResponse.Error -> {
                            _isLoading.value = false
                            _validationError.value = result.message

                        }
                    }
                }
            } catch (e: Exception) {

                _isLoading.value = false
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }
    fun getRotation(type: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            try {
                useCase.getRotation(type,  user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                        }
                        is NetworkResponse.Success -> {
                            _rotations.value = result.data
                        }
                        is NetworkResponse.Error -> {
                            _validationError.value = result.message
                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }





}
