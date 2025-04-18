package com.contrast.Contrast.presentation.features.affiliate.home




import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.presentation.navigator.event.HomeNavEvent
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.SliderHome
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase

import com.itechpro.domain.usecase.home.HomeAffiliateUseCase
import com.itechpro.domain.usecase.sell.SellConfigUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeAffiliateModel @Inject constructor(private val getCurrentUserUseCase: GetCurrentUserUseCase,
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

    private val _navigationEvent = MutableStateFlow<HomeNavEvent>(HomeNavEvent.None)
    val navigationEvent: StateFlow<HomeNavEvent> = _navigationEvent

    fun onTabSelected(index: Int, category: Category) {
        _selectedTab.value =index
        Log.e("categoryId",category.id?:"")
        _navigationEvent.value = HomeNavEvent.GoToProduct(category.id?:"")
    }

    // Sau khi navigate xong, reset lại state
    fun resetNavigation() {
        _navigationEvent.value = HomeNavEvent.None
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
        getProductsByIdParent(result.type,"0")

    }



    fun onCategorySelected(index: Int, categoryList: List<Category>) {
        _selectedTab.value=index

        val code = categoryList.getOrNull(index)?.code.orEmpty()
        _type.value = code

        getProductsByIdParent(code,"0")
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
                            _isLoading.value = true
                        }
                        is NetworkResponse.Success -> {
                            _isLoading.value = false
                            _categorys.value = result.data
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


                useCase.getProductsByIdParent(user.isOfflineMode,type, idParent,  user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                            _isLoading.value = true
                        }
                        is NetworkResponse.Success -> {
                            _isLoading.value = false
                            _products.value = result.data
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
