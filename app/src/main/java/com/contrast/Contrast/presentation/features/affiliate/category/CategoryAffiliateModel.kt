package com.contrast.Contrast.presentation.features.affiliate.category


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.NetworkResponse

import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.category.CategoryAffiliateUseCase
import com.itechpro.domain.usecase.product.PromoCountdownUseCase
import com.itechpro.domain.usecase.sell.SellConfigUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CategoryAffiliateModel @Inject constructor(private val getCurrentUserUseCase: GetCurrentUserUseCase,
                                                 private val useCase: CategoryAffiliateUseCase,
                                                 private val sellConfigUseCase: SellConfigUseCase,
                                                 private val promoCountdownUseCase: PromoCountdownUseCase,
                                                 private val stringProvider: StringProvider,
                                                 @IoDispatcher private val dispatcher: CoroutineDispatcher,) : ViewModel() {



    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products


    private val _obj = MutableStateFlow<Product?>(null)
    val obj: StateFlow<Product?> = _obj

    private val _tabs = MutableStateFlow<List<Category>>(emptyList())
    val tabs: StateFlow<List<Category>> = _tabs

    private val _category1 = MutableStateFlow<List<Category>>(emptyList())
    val category1: StateFlow<List<Category>> = _category1

    private val _category2 = MutableStateFlow<List<Category>>(emptyList())
    val category2: StateFlow<List<Category>> = _category2

    private val _category3 = MutableStateFlow<List<Category>>(emptyList())
    val category3: StateFlow<List<Category>> = _category3

    private val _category1Heart = MutableStateFlow<List<Category>>(emptyList())
    val category1Heart: StateFlow<List<Category>> = _category1Heart

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

    private val _idParent1 = MutableStateFlow<String>("")

    private val _idParent = MutableStateFlow<String>("")
    private val _idParent2 = MutableStateFlow<String>("")
    private val _idParent3= MutableStateFlow<String>("")
    private val _type = MutableStateFlow<String>("")
    val type: StateFlow<String> = _type
    private val _objApi = MutableStateFlow<String>("")
    private val _modeApi = MutableStateFlow<String>("")

    private var countdownJob: Job? = null

    private val _promoUiDataMap = mutableMapOf<String, MutableStateFlow<PromoUiData>>()
    val promoUiDataMap: Map<String, StateFlow<PromoUiData>>
        get() = _promoUiDataMap

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab
    private val _selectedTab1 = MutableStateFlow(0)
    val selectedTab1: StateFlow<Int> = _selectedTab1
    private val _selectedTab2 = MutableStateFlow(0)
    val selectedTab2: StateFlow<Int> = _selectedTab2

    private val _selectedTab3 = MutableStateFlow(0)
    val selectedTab3: StateFlow<Int> = _selectedTab3


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private var currentUserInfo: CurrentUserInfo? = null
    private val _navigationEvent = MutableStateFlow<NavEvent>(ProductNavEvent.None)
    val navigationEvent: StateFlow<NavEvent> = _navigationEvent

    private val _pagedProducts = MutableStateFlow<List<Product>>(emptyList())
    val pagedProducts: StateFlow<List<Product>> = _pagedProducts
    private var allProducts: List<Product> = emptyList()
    private var currentPage = 0
    private val pageSize = 10
    init {
        viewModelScope.launch(dispatcher) {
            try {
                currentUserInfo = getCurrentUserUseCase()
                _domain.value = currentUserInfo!!.domain?:""
                _displayProduct.value = currentUserInfo!!.displayProduct?:""
                _displayService.value = currentUserInfo!!.displayService?:""
                _displayPriority.value = currentUserInfo!!.displayPriority?:""


            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }
    fun setInitialProducts(products: List<Product>) {
        allProducts = products
        currentPage = 1
        _pagedProducts.value = products.take(pageSize)
    }

    fun handleIdParentResult(idParent1: String, idParent2: String, idParent3: String, type: String) {
        _idParent.value = when {
            idParent3.isNotEmpty() -> idParent3
            idParent2.isNotEmpty() -> idParent2
            else -> idParent1
        }

        getProductsByIdParent(type, _idParent.value)
    }

    fun initCategory(displayProduct: String, displayService: String, displayPriority: String, categoryId: String) {

        val result = sellConfigUseCase.generateConfig(displayProduct, displayService, displayPriority)
        _tabs.value = result.tabs
        _objApi.value = result.objApi
        _modeApi.value = result.modeApi
        _type.value = result.type

        handleIdParentResult(
            idParent1 = _idParent1.value,
            idParent2 = _idParent2.value,
            idParent3 = _idParent3.value,
            result.type
        )
        // Gọi API lấy cấp đầu tiên
        getCategory1("tatcanhomsp", "tatcanhomsp", result.type, categoryId, 1)
    }

    fun onTabSelected(index: Int) {
        _selectedTab.value = index
    }

    fun onTabSelected1(index: Int) {
        _selectedTab1.value = index
    }

    fun onTabSelected2(index: Int) {
        _selectedTab2.value = index
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
    fun onCategorySelected(index: Int, categoryList: List<Category>, categoryId: String) {

        if (_selectedTab.value != index) {
            _selectedTab.value = index
            onTabSelected1(0)
            onTabSelected2(0)
            onTabSelected3(0)
            val code = categoryList.getOrNull(index)?.code.orEmpty()
            _type.value = code
            getCategory1("tatcanhomsp", "tatcanhomsp", code, categoryId, 1)
        } else {

        }
    }


    fun onCategory1Selected(index: Int, categoryList: List<Category>, type: String) {
        onTabSelected1(index)
        onTabSelected2(0)
        onTabSelected3(0)
        val id = categoryList.getOrNull(index)?.id.orEmpty()
        _idParent1.value = id

        getCategory2("tatcanhomsp", "tatcanhomsp", type, id, 2)
    }


    fun onCategory2Selected(index: Int, categoryList: List<Category>, type: String) {
        onTabSelected2(index)
        onTabSelected3(0)
        val id = categoryList.getOrNull(index)?.id.orEmpty()
        _idParent2.value = id

        getCategory3("tatcanhomsp", "tatcanhomsp", type, id, 3)


    }
    fun onItemProductSelected( category: Product) {
        _navigationEvent.value = ProductNavEvent.GoToAddServiceRequest(
            id = category.id ?: "",
            serviceName = category.ten ?: "",
            idUnit = category.iddonvichuan ?: "",
            discount = category.iddonvichuan ?: ""
        )


    }


    fun onAddServiceRequestSelected( category: Product) {
        _navigationEvent.value = ProductNavEvent.GoToAddServiceRequest(
            id = category.id ?: "",
            serviceName = category.ten ?: "",
            idUnit = category.iddonvichuan ?: "",
            discount = category.iddonvichuan ?: ""
        )


    }

    fun onTabSelected3(index: Int) {
        _selectedTab3.value = index
    }

    fun onCategory3Selected(index: Int, categoryList: List<Category>, type: String) {
        onTabSelected3(index)
        val id = categoryList.getOrNull(index)?.id.orEmpty()
        _idParent3.value = id
//        getCategory3("tatcanhomsp", "tatcanhomsp", type, id, 3)

        handleIdParentResult(
            idParent1 = _idParent1.value,
            idParent2 = _idParent2.value,
            idParent3 = _idParent3.value,
            type
        )
    }

    // lấy danh mực 3 cấp
  private  fun getCategory1(obj: String,mode: String,type: String,idParent: String, level:Int) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getCategory(user.isOfflineMode, obj,mode,type,idParent ,user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                            _isLoading.value = true
                        }
                        is NetworkResponse.Success -> {
                            _isLoading.value = false
                            _category1.value = result.data
                            if(result.data.isNotEmpty()){
                                _idParent1.value = result.data[0].id.toString()
                            }else{
                                _idParent1.value = ""
                            }
                            handleIdParentResult(
                                idParent1 = _idParent1.value,
                                idParent2 = _idParent2.value,
                                idParent3 = _idParent3.value,
                               type
                            )
                            getCategory2("tatcanhomsp", "tatcanhomsp", type, result.data[0].id.toString(), 2)

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

    // lấy danh mực 3 cấp
    private  fun getCategory2(obj: String,mode: String,type: String,idParent: String, level:Int) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getCategory(user.isOfflineMode, obj,mode,type,idParent ,user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {

                        }
                        is NetworkResponse.Success -> {

                            _category2.value = result.data
                            if(result.data.isNotEmpty()){
                                _idParent2.value = result.data[0].id.toString()
                            }else{
                                _idParent2.value = ""
                            }
                            handleIdParentResult(
                                idParent1 = _idParent1.value,
                                idParent2 = _idParent2.value,
                                idParent3 = _idParent3.value,
                                type
                            )
                            getCategory3("tatcanhomsp", "tatcanhomsp", type, result.data[0].id.toString(), 3)
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
    private  fun getCategory3(obj: String,mode: String,type: String,idParent: String, level:Int) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getCategory(user.isOfflineMode, obj,mode,type,idParent ,user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {

                        }
                        is NetworkResponse.Success -> {

                            _category3.value = result.data
                            if(result.data.isNotEmpty()){
                                _idParent3.value = result.data[0].id.toString()
                            }else{
                                _idParent3.value = ""
                            }


                            handleIdParentResult(
                                idParent1 = _idParent1.value,
                                idParent2 = _idParent2.value,
                                idParent3 = _idParent3.value,
                                type
                            )

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




   private fun getProductsByIdParent(type: String, idParent: String) {
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
