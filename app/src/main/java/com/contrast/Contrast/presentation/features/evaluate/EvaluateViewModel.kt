package com.contrast.Contrast.presentation.features.evaluate





import android.net.Uri
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
import com.itechpro.domain.enumApp.ReviewFilterType
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.evaluate.Evaluate
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import com.itechpro.domain.model.evaluate.EvaluateDetail
import com.itechpro.domain.model.evaluate.EvaluateInput
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.product.ProductDetail

import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.dowloadFile.DownloadImageUseCase
import com.itechpro.domain.usecase.evaluate.EvaluateUseCase

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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class EvaluateViewModel @Inject constructor(private val getCurrentUserUseCase: GetCurrentUserUseCase,
                                           private val useCase: EvaluateUseCase,
                                           private val sellConfigUseCase: SellConfigUseCase,
                                           private val promoCountdownUseCase: PromoCountdownUseCase,
                                           private val stringProvider: StringProvider,
                                           private val downloadImageUseCase: DownloadImageUseCase,
                                           @IoDispatcher private val dispatcher: CoroutineDispatcher,) : ViewModel() {







    private val _typeReports = MutableStateFlow<List<Product>>(emptyList())
    val typeReports: StateFlow<List<Product>> = _typeReports


    private val _evaluates = MutableStateFlow<List<EvaluateDetail>>(emptyList())// xuống use case tính lại  list
    val evaluates: StateFlow<List<EvaluateDetail>> = _evaluates

    private val _totalEvaluate = MutableStateFlow<Int>(0)// xuống use case tính lại  list
    val totalEvaluate: StateFlow<Int> = _totalEvaluate

    private val _ratingScore = MutableStateFlow<Float>(0f)// xuống use case tính lại  list
    val ratingScore: StateFlow<Float> = _ratingScore



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
    // Thêm dòng này vào trong EvaluateViewModel
    private val _filterType = MutableStateFlow(ReviewFilterType.ALL)
    val filterType: StateFlow<ReviewFilterType> = _filterType.asStateFlow()



    private val _rating = MutableStateFlow(5)
    val rating: StateFlow<Int> = _rating

    private val _noteRating = MutableStateFlow("")
    val noteRating: StateFlow<String> = _noteRating

    fun setRatingNote(star: Int) {
        _rating.value = star
        _noteRating.value = when (star) {
            5 -> stringProvider.getString(R.string.rating_excellent)
            4 -> stringProvider.getString(R.string.rating_good)
            else -> stringProvider.getString(R.string.rating_average)
        }
    }

    fun setFilter(type: ReviewFilterType) {
        _filterType.value = type
    }


    private val _allReviews = MutableStateFlow<List<EvaluateDetail>>(emptyList())

    val reviews: StateFlow<List<EvaluateDetail>> = combine(_allReviews, _filterType) { list, filter ->
        when (filter) {
            ReviewFilterType.ALL -> list
            ReviewFilterType.COMMENT_ONLY -> list.filter { !it.noidung.isNullOrBlank() }
            ReviewFilterType.IMAGE_ONLY -> list.filter { !it.lst_dinhkem.isNullOrEmpty() }
            ReviewFilterType.STAR_5 -> list.filter { (it.diem ?: 0.0).toInt() == 5 }
            ReviewFilterType.STAR_4 -> list.filter { (it.diem ?: 0.0).toInt() == 4 }
            ReviewFilterType.STAR_3 -> list.filter { (it.diem ?: 0.0).toInt() == 3 }
            ReviewFilterType.STAR_2 -> list.filter { (it.diem ?: 0.0).toInt() == 2 }
            ReviewFilterType.STAR_1 -> list.filter { (it.diem ?: 0.0).toInt() == 1 }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    override fun onCleared() {
        countdownJob?.cancel()
        super.onCleared()
    }

    private val _reviewInput = MutableStateFlow(EvaluateInput())
    val reviewInput: StateFlow<EvaluateInput> = _reviewInput

    private val _submitStatus = MutableStateFlow<Result<Unit>?>(null)
    val submitStatus: StateFlow<Result<Unit>?> = _submitStatus

    fun setRating(star: Int) {
        _reviewInput.update { it.copy(rating = star) }
    }

    fun setComment(comment: String) {
        _reviewInput.update { it.copy(comment = comment) }
    }

    fun addImage(uri: Uri) {
        _reviewInput.update {
            if (it.imageUris.size < 5) it.copy(imageUris = it.imageUris + uri)
            else it
        }
    }

    fun removeImage(uri: Uri) {
        _reviewInput.update { it.copy(imageUris = it.imageUris - uri) }
    }

    fun setVideo(uri: Uri) {
        _reviewInput.update { it.copy(videoUri = uri) }
    }

    fun removeVideo() {
        _reviewInput.update { it.copy(videoUri = null) }
    }

    fun submitReview(productId: String) {
        viewModelScope.launch {
            val input = _reviewInput.value
            if (input.rating == 0) {
                _submitStatus.value = Result.failure(Exception("Chưa chọn sao đánh giá"))
                return@launch
            }

            _submitStatus.value = runCatching {
//                useCase.addEditLike(productId, input).getOrThrow()
            }
        }
    }


    // Sau khi navigate xong, reset lại state
    fun resetNavigation() {
        _navigationEvent.value = ProductNavEvent.None
    }


    fun loadEvaluate(idProduct: String,count: String) {
        if (isLoaded) return
        isLoaded = true

        viewModelScope.launch(dispatcher) {
            try {
                currentUserInfo = getCurrentUserUseCase()
                val user = currentUserInfo ?: return@launch

                _domain.value = user.domain.orEmpty()
                val totalDuration = measureTimeMillis {
                    val evaluateJob = async {
                        measureAndRetry("getEvaluates") {
                            getEvaluates(idProduct, count) // 👈 nên dùng getEvaluates (để set _allReviews)
                        }
                    }

                    awaitAll(evaluateJob)
                }


            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"

            }
        }
    }
    private suspend fun measureAndRetry(tag: String, block: suspend () -> Unit) {
        val start = System.currentTimeMillis()
        try {
            block()

        } catch (e: Exception) {

            delay(1000)
            try {
                block()

            } catch (ex: Exception) {

            }
        }
    }










    fun getEvaluates(idProduct: String, count:String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            try {
                useCase.getEvaluates(user.isOfflineMode,idProduct, count, user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                        }
                        is NetworkResponse.Success -> {
                            _isLoading.value = false
                            val evaluateList = result.data.evaluateList
                            val totalEvaluate = result.data.totalEvaluate
                            val ratingScore = result.data.ratingScore

                            _evaluates.value = evaluateList
                            _totalEvaluate.value = totalEvaluate
                            _ratingScore.value = ratingScore
                            _allReviews.value  = evaluateList
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




}
