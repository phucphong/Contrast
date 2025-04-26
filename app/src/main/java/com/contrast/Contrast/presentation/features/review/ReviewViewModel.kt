package com.contrast.Contrast.presentation.features.review

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.utils.NetworkMonitor
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.enumApp.ReviewFilterType
import com.itechpro.domain.enumApp.ReviewSelectedFilter

import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.review.ReviewDetail
import com.itechpro.domain.model.review.ReviewFilter
import com.itechpro.domain.model.review.ReviewInput
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.dowloadFile.DownloadImageUseCase
import com.itechpro.domain.usecase.media.MediaUrisUseCase
import com.itechpro.domain.usecase.product.PromoCountdownUseCase
import com.itechpro.domain.usecase.review.ReviewUseCase
import com.itechpro.domain.usecase.sell.SellConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: ReviewUseCase,
    private val sellConfigUseCase: SellConfigUseCase,
    private val stringProvider: StringProvider,
    private val downloadImageUseCase: DownloadImageUseCase,
    private val mediaUrisUseCase: MediaUrisUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _reviews = MutableStateFlow<List<ReviewDetail>>(emptyList())
    val reviews: StateFlow<List<ReviewDetail>> = _reviews.asStateFlow()
    private val _totalReview = MutableStateFlow(0)
    val totalReview: StateFlow<Int> = _totalReview.asStateFlow()
    private val _domain = MutableStateFlow("")
    val domain: StateFlow<String> = _domain
    private val _ratingScore = MutableStateFlow(0f)
    val ratingScore: StateFlow<Float> = _ratingScore.asStateFlow()

    private val _rating = MutableStateFlow(5)
    val rating: StateFlow<Int> = _rating.asStateFlow()

    private val _noteRating = MutableStateFlow("")
    val noteRating: StateFlow<String> = _noteRating.asStateFlow()

    private val _selectedFilter = MutableStateFlow<ReviewSelectedFilter>(ReviewSelectedFilter.Type(ReviewFilterType.ALL))
    val selectedFilter: StateFlow<ReviewSelectedFilter> = _selectedFilter.asStateFlow()

    private val _filteredReviews = MutableStateFlow<List<ReviewDetail>>(emptyList())
    val filteredReviews: StateFlow<List<ReviewDetail>> = _filteredReviews.asStateFlow()


    private val _reviewInput = MutableStateFlow(ReviewInput())
    val reviewInput: StateFlow<ReviewInput> = _reviewInput

    private val _compressedFiles = MutableStateFlow<List<File>>(emptyList())
    val compressedFiles: StateFlow<List<File>> = _compressedFiles

    private val _submitStatus = MutableStateFlow<Result<Unit>?>(null)
    val submitStatus: StateFlow<Result<Unit>?> = _submitStatus

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _validationError = MutableStateFlow("")
    val validationError: StateFlow<String> = _validationError

    private val _navigationEvent = MutableStateFlow<NavEvent>(ProductNavEvent.None)
    val navigationEvent: StateFlow<NavEvent> = _navigationEvent
    private val _filterCounts = MutableStateFlow<Map<ReviewFilterType, Int>>(emptyMap())
    val filterCounts: StateFlow<Map<ReviewFilterType, Int>> = _filterCounts

    private var currentUserInfo: CurrentUserInfo? = null
    private var isLoaded = false
    init {
        // Tính filterCounts khi reviews thay đổi
        reviews.onEach { reviewsList ->
            _filterCounts.value = calculateFilterCounts(reviewsList)
        }.launchIn(viewModelScope)

        // Kết hợp reviews + selectedFilter để lọc + tính starCounts luôn
        combine(reviews, selectedFilter) { reviews, filter ->
            val filtered = applyFilter(reviews, filter)
            filtered
        }.onEach { filteredList ->
            _filteredReviews.value = filteredList

        }.launchIn(viewModelScope)


    }
    val ratingCountMap: StateFlow<Map<Int, Int>> = reviews.map { reviews ->
        (5 downTo 1).associateWith { star ->
            reviews.count { it.diem == star }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        (5 downTo 1).associateWith { 0 }
    )

    private fun calculateFilterCounts(reviews: List<ReviewDetail>): Map<ReviewFilterType, Int> {
        val all = reviews.size
        val commentOnly = reviews.count { it.noidung?.isNotBlank() == true }
        val imageOnly = reviews.count { it.lst_dinhkem.isNotEmpty() == true }

        return mapOf(
            ReviewFilterType.ALL to all,
            ReviewFilterType.COMMENT_ONLY to commentOnly,
            ReviewFilterType.IMAGE_ONLY to imageOnly
        )
    }



    fun compressUris(uris: List<Uri>) {
        viewModelScope.launch {
            val result = mediaUrisUseCase.invoke(uris)
            _compressedFiles.value = result
        }
    }

    fun setFilter(type: ReviewFilterType) {
        _selectedFilter.value = ReviewSelectedFilter.Type(type)
    }

    fun setStarFilter(star: Int) {
        _selectedFilter.value = ReviewSelectedFilter.Star(star)
    }

    fun setRatingNote(star: Int) {
        _rating.value = star
        _noteRating.value = when (star) {
            5 -> stringProvider.getString(R.string.rating_excellent)
            4 -> stringProvider.getString(R.string.rating_good)
            else -> stringProvider.getString(R.string.rating_average)
        }
    }


    private fun applyFilter(reviews: List<ReviewDetail>, filter: ReviewSelectedFilter): List<ReviewDetail> {
        return useCase.filter(reviews, filter)
    }


    fun submitReview(productId: String) {
        viewModelScope.launch {
            val input = _reviewInput.value
            if (input.rating == 0) {
                _submitStatus.value = Result.failure(Exception("Chưa chọn sao đánh giá"))
                return@launch
            }
            _submitStatus.value = runCatching {
                // useCase.addEditLike(productId, input).getOrThrow()
            }
        }
    }


    fun loadReview(idProduct: String, count: String) {
        if (isLoaded) return
        isLoaded = true

        viewModelScope.launch(dispatcher) {
            try {
                currentUserInfo = getCurrentUserUseCase()
                val user = currentUserInfo ?: return@launch
                _domain.value = user.domain.orEmpty()
                val reviewJob = async {
                    getReviews(idProduct, count)
                }
                awaitAll(reviewJob)
            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

  fun getReviews(idProduct: String, count: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getReviews(user.isOfflineMode, idProduct, count, user.token).collect { result ->
                when (result) {
                    is NetworkResponse.Success -> {
                        _reviews.value = result.data.reviewList
                        _totalReview.value = result.data.totalReview
                        _ratingScore.value = result.data.ratingScore
                    }
                    is NetworkResponse.Error -> _validationError.value = result.message
                    else -> {}
                }
            }
        }
    }

    fun resetNavigation() {
        _navigationEvent.value = ProductNavEvent.None
    }

    fun setComment(comment: String) {
        _reviewInput.update { it.copy(comment = comment) }
    }

    fun addImage(uri: Uri) {
        _reviewInput.update {
            if (it.imageUris.size < 5) it.copy(imageUris = it.imageUris + uri) else it
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
}
