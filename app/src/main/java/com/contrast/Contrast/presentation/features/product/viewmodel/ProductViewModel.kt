package com.contrast.Contrast.presentation.features.product.viewmodel





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
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.product.ProductDetail

import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.dowloadFile.DownloadImageUseCase

import com.itechpro.domain.usecase.product.ProductUseCase
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
class ProductViewModel @Inject constructor(private val getCurrentUserUseCase: GetCurrentUserUseCase,
                                           private val useCase: ProductUseCase,
                                           private val sellConfigUseCase: SellConfigUseCase,
                                           private val promoCountdownUseCase: PromoCountdownUseCase,
                                           private val stringProvider: StringProvider,
                                           private val downloadImageUseCase: DownloadImageUseCase,
                                           @IoDispatcher private val dispatcher: CoroutineDispatcher,) : ViewModel() {






    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products
    private val _typeReports = MutableStateFlow<List<Product>>(emptyList())
    val typeReports: StateFlow<List<Product>> = _typeReports

    private val _productInfo = MutableStateFlow<ProductDetail?>(null)
    val productInfo: StateFlow<ProductDetail?> = _productInfo




    private val _validationError = MutableStateFlow<String>("")
    val validationError: StateFlow<String> = _validationError
    private val _domain = MutableStateFlow<String>("")
    val domain: StateFlow<String> = _domain
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

    private val _promoUiDataMapInfo = mutableMapOf<String, MutableStateFlow<PromoUiData>>()
    val promoUiDataMapInfo: Map<String, StateFlow<PromoUiData>>
        get() = _promoUiDataMapInfo



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
    fun downloadImage(url: String) {
        downloadImageUseCase(url, url)
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
    fun startPromoCountdownInfo(product: ProductDetail) {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch(dispatcher) {
            val id = product.id ?: return@launch

            // Khởi tạo state nếu chưa có
            _promoUiDataMap.getOrPut(id) { MutableStateFlow(PromoUiData()) }

            while (isActive) {
                val now = LocalDateTime.now()
                val promo = promoCountdownUseCase.calculateProductDetail(product, now)
                _promoUiDataMap[id]?.value = promo

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

    fun onItemProductSelected( obj: Product) {
        _navigationEvent.value = ProductNavEvent.GoToProductDetail(
            id = obj.id ?: "",
            idUnit = obj.iddonvichuan ?: "",
            )
    }

    fun onItemReviewsSelected( id: String) {
        _navigationEvent.value = ProductNavEvent.GoToProductReviews(
            id = id)
    }


    fun onItemAddReviewsSelected( id: String, fileTxt:String, name:String) {
        _navigationEvent.value = ProductNavEvent.GoToAddReviews(
            id = id,
            fileTxt = fileTxt,
            name = name,
            )
    }


    fun onItemNotificationSelected( ) {
        _navigationEvent.value = NotificationNavEvent.GoToNotifications(
            startDate = "",
            endDate = "",

            )


    }
    fun onItemCart( category: Product) {

        // val  objCart = Product()
        //        objCart.id=obj.id
        //        objCart.iddonvichuan=obj.iddonvichuan
        //        objCart.soluong=obj.countNumber
        //        objCart.khuyenmai = obj.discount
        //        objCart.sotien = obj.price
        //        objCart.dongia = obj.price
        //        addCart(objCart)

        // callApAddCart



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


    fun loadData(idParent: String,idUnit: String) {
        if (isLoaded) return
        isLoaded = true

        viewModelScope.launch(dispatcher) {
            try {
                currentUserInfo = getCurrentUserUseCase()
                val user = currentUserInfo ?: return@launch

                _domain.value = user.domain.orEmpty()
                _displayProduct.value = user.displayProduct.orEmpty()
                _displayService.value = user.displayService.orEmpty()
                _displayPriority.value = user.displayPriority.orEmpty()

                val result = sellConfigUseCase.generateConfig(
                    user.displayProduct.orEmpty(),
                    user.displayService.orEmpty(),
                    user.displayPriority.orEmpty()
                )



                Log.d("Timing", "🚀 Bắt đầu loadHomeData song song")

                val totalDuration = measureTimeMillis {
                    val InfoProductJob = async {
                        measureAndRetry("getInfoProduct") {
                            getInfoProduct(idParent, idUnit)
                        }
                    }

                  
                    val productJob = async {
                        measureAndRetry("getProductsByIdParent") {
                            getProductsByIdParent(result.type, "0")
                        }
                    }
                 

                    awaitAll(InfoProductJob, productJob)
                }

                Log.d("Timing", "✅ Tất cả API hoàn tất trong ${totalDuration}ms")

            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
                Log.e("loadHomeData", "❌ Exception tổng: ${e.localizedMessage}")
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
    fun getInfoProduct(idParent: String,idUnit: String,) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getInfoProduct(user.isOfflineMode,idParent ,idUnit,user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                        }
                        is NetworkResponse.Success -> {
                            _productInfo.value = result.data
                            startPromoCountdownInfo(result.data)
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
    fun getUnLike(type: String,idProduct: String,idUnit: String) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getUnLike(type ,idProduct,idUnit,user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                        }
                        is NetworkResponse.Success -> {
//                            _flashSales.value = result.data

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

    fun getTypeReport(obj: String,mode: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {

            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getTypeReport(user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                        }
                        is NetworkResponse.Success -> {
                            _typeReports.value = result.data

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


    fun addEditLike(url: String, obj: Product) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            useCase.addEditLike(url, obj, user.token).collect { result ->
                _isLoading.value = result is NetworkResponse.Loading
                if (result is NetworkResponse.Error) {
                    _validationError.value = result.message
                }
            }
        }
    }

    fun getProductsByIdParent(type: String,idParent: String) {
        val user = currentUserInfo ?: return


        viewModelScope.launch(dispatcher) {
            try {

                // ✅ RESET phân trang mỗi lần gọi mới
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





}
