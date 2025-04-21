package com.contrast.Contrast.presentation.features.affiliate.home




import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.enumApp.ProductSectionType
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.SliderHome
import com.itechpro.domain.model.navigationEvent.HomeNavEvent
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.product.ProductSection

import com.itechpro.domain.usecase.account.GetCurrentUserUseCase

import com.itechpro.domain.usecase.home.HomeAffiliateUseCase
import com.itechpro.domain.usecase.sell.SellConfigUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeAffiliateViewModel @Inject constructor(private val getCurrentUserUseCase: GetCurrentUserUseCase,
                                                 private val useCase: HomeAffiliateUseCase,
                                                 private val sellConfigUseCase: SellConfigUseCase,
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
    private val _displayProduct = MutableStateFlow<String>("")
    val displayProduct: StateFlow<String> = _displayProduct
    private val _displayService = MutableStateFlow<String>("")
    val displayService: StateFlow<String> = _displayService
    private val _displayPriority = MutableStateFlow<String>("")
    val displayPriority: StateFlow<String> = _displayPriority

    private val _type = MutableStateFlow<String>("")
    val type: StateFlow<String> = _type
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private var currentUserInfo: CurrentUserInfo? = null

    private val _navigationEvent = MutableStateFlow<NavEvent>(ProductNavEvent.None)
    val navigationEvent: StateFlow<NavEvent> = _navigationEvent
    private val _sections = MutableStateFlow<List<ProductSection>>(emptyList())
    val sections: StateFlow<List<ProductSection>> = _sections

//    private val _promoUiDataMap = MutableStateFlow<Map<String, PromoUiData>>(emptyMap())
//    val promoUiDataMap: StateFlow<Map<String, PromoUiData>> = _promoUiDataMap

    private val _promoUiDataMap = mutableMapOf<String, MutableStateFlow<PromoUiData>>()
    val promoUiDataMap: Map<String, StateFlow<PromoUiData>>
        get() = _promoUiDataMap


    private var countdownJob: Job? = null


    fun startPromoCountdown(products: List<Product>) {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch(dispatcher) {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

            // Khởi tạo state cho từng item nếu chưa có
            products.forEach { product ->
                val id = product.id ?: return@forEach
                _promoUiDataMap.getOrPut(id) { MutableStateFlow(PromoUiData()) }
            }

            while (isActive) {
                val now = LocalDateTime.now()

                products.forEach { product ->
                    val id = product.id ?: return@forEach

                    val start = runCatching {
                        LocalDate.parse("10/04/2025", formatter).atStartOfDay()
                    }.getOrNull()

                    val end = runCatching {
                        LocalDate.parse("22/04/2025", formatter).atTime(23, 59, 59)
                    }.getOrNull()

                    if (start != null && end != null) {
                        val total = Duration.between(start, end).toMillis().toFloat()
                        val elapsed = Duration.between(start, now).toMillis().coerceAtLeast(0)
                        val progress = if (total > 0f) (elapsed / total).coerceIn(0f, 1f) else 1f

                        val remain = Duration.between(now, end).coerceAtLeast(Duration.ZERO)
                        val h = remain.toHours()
                        val m = remain.toMinutes() % 60
                        val s = remain.seconds % 60
                        val timeStr = String.format("%02d:%02d:%02d", h, m, s)

                        _promoUiDataMap[id]?.value = PromoUiData(progress, timeStr)
                    }
                }

                delay(1000)
            }
        }
    }


    override fun onCleared() {
        countdownJob?.cancel()
        super.onCleared()
    }
    private fun updateSections() {
        val data = mutableListOf<ProductSection>()

        if (_slides.value.isNotEmpty()) {
            data.add(ProductSection(id = "slide",domain = _domain.value, type = ProductSectionType.SLIDE, slides = _slides.value))
        }

        if (_categorys.value.isNotEmpty()) {
            data.add(ProductSection(id = "category", domain = _domain.value, type = ProductSectionType.CATEGORY, categories = _categorys.value))
        }

        if (_flashSales.value.isNotEmpty()) {
            data.add(ProductSection(id = "flashsale", domain = _domain.value, type = ProductSectionType.FLASH_SALE, products = _flashSales.value, title = "Ưu đãi chớp nhoáng"))
        }

        if (_products.value.isNotEmpty()) {
            data.add(ProductSection(id = "product",domain = _domain.value,  type = ProductSectionType.PRODUCT_ALL, products = _products.value, title = "Sản phẩm hot", headerTab = true))
        }

        _sections.value = data
    }

    fun onTabSelected(index: Int, category: Category) {
        _selectedTab.value =index

        _navigationEvent.value = ProductNavEvent.GoToProductsCategory(category.id?:"")

    }

    fun onItemProductSelected( category: Product) {
        _navigationEvent.value = ProductNavEvent.GoToAddServiceRequest(
            id = category.id ?: "",
            serviceName = category.ten ?: "",
            idUnit = category.iddonvichuan ?: "",
            discount = category.iddonvichuan ?: ""
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

    init {
        viewModelScope.launch(dispatcher) {
            try {
                currentUserInfo = getCurrentUserUseCase()
                _domain.value = currentUserInfo!!.domain?:""
                _displayProduct.value = currentUserInfo!!.displayProduct?:""
                _displayService.value = currentUserInfo!!.displayService?:""
                _displayPriority.value = currentUserInfo!!.displayPriority?:""

                getSlideHome("sanphamtrangchu","modeslide")
                getCategory("tatcanhomsp","tatcanhomsp","","0")
                getFlashSale()
                initCategory(currentUserInfo!!.displayPriority,currentUserInfo!!.displayPriority,currentUserInfo!!.displayPriority)

                getRotation(currentUserInfo!!.typeAccount)
            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }



    fun initCategory(displayProduct: String, displayService: String, displayPriority: String) {

        val result = sellConfigUseCase.generateConfig(displayProduct, displayService, displayPriority)
        _tabs.value = result.tabs
        _type.value = result.type
        Log.e("getProductsByIdParent","getProductsByIdParent")
        getProductsByIdParent(result.type,"0")

    }



    fun onCategorySelected(index: Int, categoryList: List<Category>) {
        _selectedTab.value=index

        val code = categoryList.getOrNull(index)?.code.orEmpty()
        _type.value = code
        Log.e("getProductsByIdParent","getProductsByIdParent")
        getProductsByIdParent(code,"0")
    }




    // lấy danh mực 3 cấp
    fun getCategory(obj: String,mode: String,type: String,idParent: String) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            val startTime = System.currentTimeMillis()
            Log.d("Timing", "📤 Start getCategory at $startTime")
            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getCategory(user.isOfflineMode, obj,mode,type,idParent ,user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {

                        }
                        is NetworkResponse.Success -> {

                            _categorys.value = result.data
                            val endTime = System.currentTimeMillis()
                            val duration = endTime - startTime
                            Log.d("Timing", "✅ Success getCategory in $duration ms")
                            updateSections()
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
                            updateSections()
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
            val startTime = System.currentTimeMillis()
            Log.d("Timing", "📤 Start getSlideHome at $startTime")
            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getSlideHome(user.isOfflineMode, obj,mode,user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                        }
                        is NetworkResponse.Success -> {
                            _slides.value = result.data
                            val endTime = System.currentTimeMillis()
                            val duration = endTime - startTime
                            Log.d("Timing", "✅ Success getSlideHome in $duration ms")
                            updateSections()
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
                val startTime = System.currentTimeMillis()
                Log.d("Timing", "📤 Start getProductsByIdParent at $startTime")

                useCase.getProductsByIdParent(user.isOfflineMode,type, idParent,  user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                            _isLoading.value = true
                        }
                        is NetworkResponse.Success -> {
                            val endTime = System.currentTimeMillis()
                            val duration = endTime - startTime
                            Log.d("Timing", "✅ Success getProductsByIdParent in $duration ms")

                            _isLoading.value = false
                            _products.value = result.data
                            updateSections()
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
