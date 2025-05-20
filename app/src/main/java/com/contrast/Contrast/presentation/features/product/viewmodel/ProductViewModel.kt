package com.contrast.Contrast.presentation.features.product.viewmodel





import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.PromoUiData
import com.itechpro.domain.model.product.ShareOption
import com.itechpro.domain.model.navigationEvent.NavEvent

import com.itechpro.domain.model.product.ProductDetail

import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.dowloadFile.DownloadUseCase

import com.itechpro.domain.usecase.product.ProductUseCase
import com.itechpro.domain.usecase.sell.SellConfigUseCase
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.contrast.Contrast.extensions.collectResponse
import com.itechpro.domain.model.LikeProductService
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import com.itechpro.domain.model.product.ProductDetailUiState
import com.itechpro.domain.usecase.product.StartPromoCountdownUseCase

import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ProductViewModel @Inject constructor(private val context: Context,
                                           private val getCurrentUserUseCase: GetCurrentUserUseCase,
                                           private val useCase: ProductUseCase,
                                           private val sellConfigUseCase: SellConfigUseCase,
                                           private var startPromoCountdownUseCase: StartPromoCountdownUseCase,
                                           private val stringProvider: StringProvider,
                                           private val downloadUseCase: DownloadUseCase,
                                           @IoDispatcher private val dispatcher: CoroutineDispatcher,) : ViewModel() {





    private val _type = MutableStateFlow<String>("")
    val type: StateFlow<String> = _type


    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private var currentUserInfo: CurrentUserInfo? = null



    private val _promoUiDataMap = mutableMapOf<String, MutableStateFlow<PromoUiData>>()
    val promoUiDataMap: Map<String, StateFlow<PromoUiData>>
        get() = _promoUiDataMap

    private val _promoUiDataMapInfo = mutableMapOf<String, MutableStateFlow<PromoUiData>>()
    val promoUiDataMapInfo: Map<String, StateFlow<PromoUiData>>
        get() = _promoUiDataMapInfo

    private val _shareIntentFlow = MutableSharedFlow<Intent>()
    val shareIntentFlow: SharedFlow<Intent> = _shareIntentFlow


    private var countdownJobDetail: Job? = null
    private var countdownJob: Job? = null
    private val _pagedProducts = MutableStateFlow<List<Product>>(emptyList())
    val pagedProducts: StateFlow<List<Product>> = _pagedProducts

    private var allProducts: List<Product> = emptyList()
    private var currentPage = 0
    private val pageSize = 10
    private val _notificationToast = MutableSharedFlow<String>()
    val notificationToast = _notificationToast.asSharedFlow()

    private val _state = MutableStateFlow(ProductDetailUiState())
    val state: StateFlow<ProductDetailUiState> = _state
    suspend fun showNotificationToast(message: String) {
        _notificationToast.emit(message)
    }


    fun share(option: ShareOption, shareUrl: String) {
        when (option) {
            ShareOption.Zalo -> {
                // Mở intent share tới Zalo
                shareToApp("com.zing.zalo", shareUrl)
            }
            ShareOption.Facebook -> {
                // Mở intent share tới Facebook
                shareToApp("com.facebook.katana", shareUrl)
            }
            ShareOption.CopyLink -> {
                // Copy link vào clipboard
                copyToClipboard(shareUrl)
            }
        }
    }
    private fun shareToApp(packageName: String, url: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
            setPackage(packageName) // ⚡ quan trọng: chỉ định đúng app
        }

        try {
            _shareIntentFlow.tryEmit(intent)
        } catch (e: Exception) {
            fallbackShare(url)
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

    private fun fallbackShare(url: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }
        _shareIntentFlow.tryEmit(intent)
    }

    private fun copyToClipboard(text: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Link", text)
        clipboardManager.setPrimaryClip(clip)
        Toast.makeText(context, "Đã sao chép liên kết", Toast.LENGTH_SHORT).show()
    }


    fun setInitialProducts(products: List<Product>) {
        allProducts = products
        currentPage = 1
        _pagedProducts.value = products.take(pageSize)
    }
    fun downloadImage(url: String) {
        downloadUseCase(url, url)
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

    fun startPromoCountdownProducts(products: List<Product>) {
        countdownJob?.cancel()
        countdownJob = startPromoCountdownUseCase.startForProductList(
            products = products,
            stateMap = _promoUiDataMap,
            coroutineScope = viewModelScope,
            dispatcher = dispatcher
        )
    }

    fun startPromoCountdownProductDetail(product: ProductDetail) {
        countdownJobDetail?.cancel()
        countdownJobDetail = startPromoCountdownUseCase.startForProductDetail(
            product = product,
            stateMap = _promoUiDataMapInfo,
            coroutineScope = viewModelScope,
            dispatcher = dispatcher
        )
    }

    override fun onCleared() {
        countdownJob?.cancel()
        countdownJobDetail?.cancel()
        super.onCleared()
    }
    
    fun onTabSelected(index: Int, category: Category) {
        _selectedTab.value =index
        _state.update {
            it.copy(
                navEvent =ProductNavEvent.GoToProductsCategory(category.id?:"")
            )
        }

    }

    fun onItemProductSelected( obj: Product) {
        _state.update {
            it.copy(
                navEvent =ProductNavEvent.GoToProductDetail(
                    id = obj.id ?: "",
                    idUnit = obj.iddonvichuan ?: "",
                    introducerId = "0",
                )
            )
        }
    }

    fun onItemReviewsSelected( id: String) {
        _state.update {
            it.copy(
                navEvent =ProductNavEvent.GoToProductReviews(
                    id = id)
            )
        }
    }


    fun onItemAddReviewsSelected( id: String,idUnit: String, fileTxt:String, name:String) {
        if (currentUserInfo?.token?.isEmpty() == true) {
            // Chuyển màn hình login từ Activity
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        navEvent =SplashNaEvent.GoToLogIn("1")
                    )
                }
            }
        } else {

        _state.update {
            it.copy(
                navEvent =ProductNavEvent.GoToAddReviews(
                    id = id,
                    idUnit = idUnit,
                    fileTxt = fileTxt,
                    name = name,
                )
            )
        }
        }
    }
    fun onItemReportSelected( id: String,idUnit: String, fileTxt:String, name:String) {
         if (currentUserInfo?.token?.isEmpty() == true) {
            viewModelScope.launch {


                _state.update {
                    it.copy(
                        navEvent = SplashNaEvent.GoToLogIn("1")
                    )
                }
            }
        } else {


            _state.update {
                it.copy(
                    navEvent =ProductNavEvent.GoToReportProduct(
                        id = id,
                        idUnit = idUnit,
                        fileTxt = fileTxt,
                        name = name,
                    )
                )
            }
        }
    }
    fun onFavorite(isLike: Boolean,idProduct: String, idUnit: String) {
         if (currentUserInfo?.token?.isEmpty() == true) {
            // Chuyển màn hình login từ Activity
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        navEvent =SplashNaEvent.GoToLogIn("1")
                    )
                }
            }
        } else {
            if (isLike) {
                addEditFavorite("/ex/api_Sanpham/addyeuthich",idProduct, idUnit,"","","" )
            } else {
                getUnLike(idProduct, idUnit )
            }
        }
    }


    fun onItemCarts( ) {

         if (currentUserInfo?.token?.isEmpty() == true) {
            // Chuyển màn hình login từ Activity
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        navEvent =SplashNaEvent.GoToLogIn("1")
                    )
                }
            }
        } else {

        _state.update {
            it.copy(
                navEvent =CartNavEvent.GoToCats
            )
        }
        }

    }

    fun increaseProductDetailQuantity(obj: ProductDetail) {
        val newQuantity = (obj.soluong?: 1.0) + 1
        updateQuantityProductDetailById(obj, newQuantity)
    }

    fun decreaseProductDetailQuantity(obj: ProductDetail) {
        val currentQuantity = (obj.soluong?: 1.0)
        if (currentQuantity >= 1) {
            val newQuantity = currentQuantity - 1
            updateQuantityProductDetailById(obj,newQuantity)
        }
    }

    fun updateQuantityProductDetailById(product: ProductDetail, newQuantity: Double) {
        _state.update { it.copy(productInfo = product.copy(soluong = newQuantity)!!) }
    }

    fun onAddServiceRequestSelected( category: Product) {
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

            _state.update {
                it.copy(
                    navEvent = ProductNavEvent.GoToAddServiceRequest(
                        id = category.id ?: "",
                        serviceName = category.ten ?: "",
                        idUnit = category.iddonvichuan ?: "",
                        discount = category.iddonvichuan ?: ""
                    )
                )
            }

        }
    }


    // Sau khi navigate xong, reset lại state
    fun resetNavigation() {
        _state.update {
            it.copy(
                navEvent = ProductNavEvent.None
            )
        }
    }


    init {
        viewModelScope.launch { initUser() }
    }

    private suspend fun initUser() {
        runCatching { getCurrentUserUseCase() }
            .onSuccess { user ->
                currentUserInfo = user
                _state.update {
                    it.copy(
                        domain = user.domain,
                        token = user.token,
                        device = user.device,
                        displayProduct = user.displayProduct,
                        displayService = user.displayService,
                        displayPriority = user.displayPriority,
                        typeAccount = user.typeAccount,
                        pointAffiliate = user.pointAffiliate,
                        customerId = user.customerId,
                        employeeId = user.employeeId,

                    )
                }
            }
            .onFailure {
                _state.update {
                    it.copy(validationError = stringProvider.getString(R.string.error_connection))
                }
            }
    }
    fun loadData(idParent: String,idUnit: String) {


        viewModelScope.launch(dispatcher) {
            try {
                val result = sellConfigUseCase.generateConfig(
                    currentUserInfo?.displayProduct.orEmpty(),
                    currentUserInfo?.displayService.orEmpty(),
                    currentUserInfo?.displayPriority.orEmpty()
                )
                val totalDuration = measureTimeMillis {
                    val InfoProductJob = async {
                        measureAndRetry("getInfoProduct") {
                            getInfoProduct(idParent, idUnit, false)
                        }
                    }
                    val productJob = async {
                        measureAndRetry("getProductsByIdParent") {
                            getProductsByIdParent(result.type, "0")
                        }
                    }


                    awaitAll(InfoProductJob, productJob)
                }



            } catch (e: Exception) {
                _state.update { it.copy(validationError = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}") }
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







    // lấy danh mực 3 cấp
    fun getInfoProduct(idParent: String,idUnit: String,isCallApi: Boolean) {

        viewModelScope.launch(dispatcher) {
            if(isCallApi){
                delay(1000)
            }


            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getInfoProduct(idParent ,idUnit,currentUserInfo?.token?:"")

                    .collectResponse(
                        dispatcher = dispatcher,
                        onSuccess = {
                                data -> _state.update { it.copy(productInfo = data!!) }

                            startPromoCountdownProductDetail(data)

                        },
                        onError = {  it }
                    )

            } catch (e: Exception) {
                _state.update { it.copy(validationError = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}") }
               
            }
        }
    }

    // lấy danh mực 3 cấp
    fun getUnLike(idProduct: String,idUnit: String) {


        viewModelScope.launch(dispatcher) {
            try {
                //offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String
                useCase.getUnLike(currentUserInfo?.typeAccount ?:"",idProduct,idUnit,currentUserInfo?.token?:"").collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                        }
                        is NetworkResponse.Success -> {
                            showNotificationToast(stringProvider.getString(R.string.un_like_product))

                        }
                        is NetworkResponse.Error -> {
                            _state.update { it.copy(validationError = stringProvider.getString(R.string.error_connection) ) }
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(validationError = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}") }
               
            }
        }
    }

   

    fun addEditFavorite( url:String,idProduct: String, idUnit: String, type: String, idReson:String, content:String) {

        val obj = LikeProductService()
        obj.idsanpham =idProduct
        obj.iddonvi=idUnit
        if(type=="report"){
            obj.idlydobaocao=idReson
            obj.noidung=content
        }

        obj.loaitk=currentUserInfo?.typeAccount?:""
        obj.mamenu="yeuthich"
        obj.os="android"
        obj.device=currentUserInfo?.device?:""
        obj.hanhdong = "add"
        viewModelScope.launch(dispatcher) {
            useCase.addEditLikeReport(url, obj, currentUserInfo?.token?:"").collect { result ->
                _isLoading.value = result is NetworkResponse.Loading
                if (result is NetworkResponse.Success) {
                    if(type=="report"){
                        showNotificationToast(stringProvider.getString(R.string.report_product_success))

                    }else{
                        showNotificationToast(stringProvider.getString(R.string.like_product))

                    }


                }else if (result is NetworkResponse.Error) {
                    showNotificationToast(result.message)

                }
            }
        }
    }
    fun getProductsByIdParent(type: String,idParent: String) {

        viewModelScope.launch(dispatcher) {
            try {

                // ✅ RESET phân trang mỗi lần gọi mới
                _pagedProducts.value = emptyList()
                currentPage = 0
                allProducts = emptyList()
                useCase.getProductsByIdParent(type, idParent,  currentUserInfo?.token?:"")
                    .collectResponse(
                        dispatcher = dispatcher,
                        onSuccess = {
                                data -> _state.update { it.copy(products = data) }
                            startPromoCountdownProducts(data)
                        },
                        onError = {  it }
                    )

            } catch (e: Exception) {
                showNotificationToast( stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}")
                _isLoading.value = false

            }
        }
    }

    fun getProductsCategory(idParent: String,idProduct: String) {

        viewModelScope.launch(dispatcher) {
            try {

                // ✅ RESET phân trang mỗi lần gọi mới
                _pagedProducts.value = emptyList()
                currentPage = 0
                allProducts = emptyList()
                useCase.getProductsCategory(_type.value, idParent,idProduct,  currentUserInfo?.token?:"")
                    .collectResponse(
                        dispatcher = dispatcher,
                        onSuccess = {
                                data -> _state.update { it.copy(productsCategory = data) }
                            startPromoCountdownProducts(data)
                        },
                        onError = {  it }
                    )
            } catch (e: Exception) {

                _state.update { it.copy(validationError = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}") }
               
            }
        }
    }
 fun getProductsOther(idProduct: String) {

        viewModelScope.launch(dispatcher) {
            try {

                // ✅ RESET phân trang mỗi lần gọi mới
                _pagedProducts.value = emptyList()
                currentPage = 0
                allProducts = emptyList()
                useCase.getProductsOther(_type.value, "0",idProduct,  currentUserInfo?.token?:"")

                    .collectResponse(
                        dispatcher = dispatcher,
                        onSuccess = {
                            data -> _state.update { it.copy(products = data) }
                            startPromoCountdownProducts(data)
                                    },
                        onError = {  it }
                    )

            } catch (e: Exception) {
                _state.update { it.copy(validationError = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}") }
               
            }
        }
    }


}
