package com.contrast.Contrast.presentation.features.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.*
import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.cart.Cart
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.product.ProductDetail
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.cart.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: CartUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _carts = MutableStateFlow<List<CartItem>>(emptyList())
    val carts: StateFlow<List<CartItem>> = _carts

    private val _cartTable = MutableStateFlow<Cart?>(null)
    val cartTable: StateFlow<Cart?> = _cartTable

    private val _validationError = MutableStateFlow("")
    val validationError: StateFlow<String> = _validationError

    private val _device = MutableStateFlow("")

    private val _domain = MutableStateFlow("")
    val domain: StateFlow<String> = _domain

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> = _statusMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _totalCartItems = MutableStateFlow(0)
    val totalCartItems: StateFlow<Int> = _totalCartItems

    private val _totalValue = MutableStateFlow(0.0)// tổng tiền
    val totalValue: StateFlow<Double> = _totalValue

    private val _totalIntoMoney = MutableStateFlow(0.0)// tổng tền thanh toán
    val totalIntoMoney: StateFlow<Double> = _totalIntoMoney
        private val _amountMoneyDiscount = MutableStateFlow(0.0)// tổng số tiền giảm  giá
    val amountMoneyDiscount: StateFlow<Double> = _amountMoneyDiscount


    private val _totalnotificationToastItems = MutableStateFlow(0)
    val totalnotificationToastItems: StateFlow<Int> = _totalnotificationToastItems
    private val _isAllSelected= MutableStateFlow(true)
    val isAllSelected: StateFlow<Boolean> = _isAllSelected

    private val _navigationEvent = MutableStateFlow<NavEvent>(CartNavEvent.None)
    val navigationEvent: StateFlow<NavEvent> = _navigationEvent

    private val _notificationToast = MutableSharedFlow<String>()
    val notificationToast = _notificationToast.asSharedFlow()

    suspend fun showNotificationToast(message: String) {
        _notificationToast.emit(message)
    }


    private var currentUserInfo: CurrentUserInfo? = null

    init {
        viewModelScope.launch(dispatcher) {
            try {
                currentUserInfo = getCurrentUserUseCase()
                _domain.value = currentUserInfo?.domain.orEmpty()
                _domain.value = currentUserInfo?.domain.orEmpty()
                _device.value = currentUserInfo?.device.orEmpty()




            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage.orEmpty()}"
            }
        }
    }
    fun onTabSelected(index: Int, category: Category) {
        _navigationEvent.value = ProductNavEvent.GoToProductsCategory(category.id.orEmpty())
    }
    fun onCheckedChangeAll(carts:  List<CartItem>, isChecked:Boolean) {
       _carts.value=useCase.onCheckedChangeAll(carts, isChecked)
        updateCartTotal(_carts.value)
    }
    fun isAllSelected(carts:  List<CartItem>,  isChecked:Boolean) {
       _isAllSelected.value=isChecked
        _carts.value=useCase.onCheckedChangeAll(carts, isChecked)
        updateCartTotal(_carts.value)
    }


    fun updateQuantityById(cartItem: CartItem,idsp: String,type: String, newQuantity: Double) {
        _carts.update { list ->
            list.map { item ->
                if (item.idsp == idsp) item.copy(soluong = newQuantity)
                else item
            }
        }
        addEditCart("/ex/apiaffiliate/addgiohang", cartItem,type,newQuantity, true)
    }


    fun increaseCartQuantity(cartItem: CartItem,type: String) {
        val newQuantity = (cartItem.soluong?: 1.0) + 1
        updateQuantityById(cartItem,cartItem.idsp.orEmpty(),type, newQuantity)
    }

    fun onQuantityChange(cartItem: CartItem,type: String, newQuantity: Double) {

        updateQuantityById(cartItem,cartItem.idsp.orEmpty(),type, newQuantity)
    }

    fun decreaseCartQuantity(cartItem: CartItem,type: String) {
        val currentQuantity = (cartItem.soluong?: 1.0)
        if (currentQuantity >= 1.0) {
            val newQuantity = currentQuantity - 1
            updateQuantityById(cartItem,cartItem.idsp.orEmpty(), type,newQuantity)
        }
    }



    fun onItemAddCart( product: Product) {

        val cartItem= CartItem(id= "0",idsp =product.id ,
            iddonvi =product.iddonvichuan ,
            soluong =1.0 ,
            dongia =product.sotien  ,
            ck =product.khuyenmai  ,
            loaicapnhat ="add"  ,
            mamenu ="giohang"  ,
            hanhdong ="add"  ,
            device =_device.value  ,
            )

        addEditCart("/ex/apiaffiliate/addgiohang", cartItem,"add",1.0, false)
    }

    fun onItemAddCartToProductDetail( product: ProductDetail, introducerId:String) {

        val cartItem= CartItem(id= "0",idsp =product.id ,
            iddonvi =product.iddonvichuan ,
            idnguoigioithieu =introducerId ,
            soluong =product.soluong?:1.0 ,
            dongia =product.sotien  ,
            ck =product.khuyenmai  ,
            loaicapnhat ="add"  ,
            mamenu ="giohang"  ,
            hanhdong ="add"  ,
            device =_device.value  ,
        )

        addEditCart("/ex/apiaffiliate/addgiohang", cartItem,"add",product.soluong?:1.0, false)
    }





    fun deleteAll(carts: List<CartItem>) {
        val ids = useCase.idsDeleteCart(carts)
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getDeleteCart(ids, user.device, "", user.token).collect { result ->
                when (result) {
                    is NetworkResponse.Loading -> {
                        _isLoading.value = true
                    }
                    is NetworkResponse.Success -> {
                        _isLoading.value = false
                        getCarts(true)
                    }
                    is NetworkResponse.Error -> {
                        _isLoading.value = false

                    }
                }
            }
        }
    }



    // Hàm update tổng
    fun updateCartTotal(carts: List<CartItem>) {
        val result = useCase.calculateCartTotal(carts, false)

        _totalValue.value = result.totalValue
        _totalIntoMoney.value = result.totalIntoMoney
        _amountMoneyDiscount.value = result.amountMoneyDiscount
    }



    fun getCarts(isTotalOder:Boolean) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getCarts(isTotalOder,user.typeAccount, user.token).collect { result ->
                when (result) {
                    is NetworkResponse.Loading -> _isLoading.value = true
                    is NetworkResponse.Success -> {
                        _isLoading.value = false
                        _carts.value = result.data.items
                        _totalCartItems.value = result.data.totalCount?:0
                        _totalValue.value = result.data.totalValue?:0.0
                        _totalIntoMoney.value = result.data.totalIntoMoney?:0.0
                        _amountMoneyDiscount.value = result.data.amountMoneyDiscount?:0.0

                        // val totalValue: Double,
                        //    val totalIntoMoney: Double,
                        //    val amountMoneyDiscount: Double
                    }
                    is NetworkResponse.Error -> {
                        _isLoading.value = false
                        _validationError.value = result.message
                    }
                }
            }
        }
    }

    fun getCheckOder(ids: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getCheckOder(ids, user.token).collect { result ->
                _isLoading.value = result is NetworkResponse.Loading
                if (result is NetworkResponse.Success) _statusMessage.value = result.data
                if (result is NetworkResponse.Error) _validationError.value = result.message
            }
        }
    }

    fun getDeleteCart(ids: String, content: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getDeleteCart(ids, user.device, content, user.token).collect { result ->
                _isLoading.value = result is NetworkResponse.Loading
                if (result is NetworkResponse.Success) _statusMessage.value = result.data
                if (result is NetworkResponse.Error) _validationError.value = result.message
            }
        }
    }

    fun getCheckProduct(ids: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getCheckProduct(ids, user.token).collect { result ->
                _isLoading.value = result is NetworkResponse.Loading
                if (result is NetworkResponse.Success) _statusMessage.value = result.data
                if (result is NetworkResponse.Error) _validationError.value = result.message
            }
        }
    }

    fun addEditCart(url: String, cartItem: CartItem, type:String, quantity:Double, isTotalOder:Boolean) {


      if(type=="update"){
          cartItem.ido = cartItem.id
      }else{
          cartItem.ido="0"
      }
        cartItem.soluong = quantity
        cartItem.loaicapnhat = type
        cartItem. mamenu = "giohang"
        cartItem.  device = _device.value
        cartItem. hanhdong = if(type=="add") "add" else "edit"
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            useCase.addEditCart(url, cartItem, user.token).collect { result ->
                _isLoading.value = result is NetworkResponse.Loading
                if (result is NetworkResponse.Error) {
                    _validationError.value = result.message
                }
                if (result is NetworkResponse.Success) {
                   getCarts(isTotalOder)

                    showNotificationToast(stringProvider.getString(R.string.add_product_on_cart))
                }
            }
        }
    }


    fun addOder(url: String, obj: CartItem) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            useCase.addOder(url, obj, user.token).collect { result ->
                _isLoading.value = result is NetworkResponse.Loading
                if (result is NetworkResponse.Error) {
                    _validationError.value = result.message
                }
            }
        }
    }




    fun resetNavigation() {
        _navigationEvent.value = ProductNavEvent.None
    }
}
