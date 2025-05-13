package com.contrast.Contrast.presentation.features.news.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.Video

import com.itechpro.domain.model.news.News
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.NewsNavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent

import com.itechpro.domain.model.news.NewsUiState
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.news.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: NewsUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(NewsUiState())
    val state: StateFlow<NewsUiState> = _state

    private var currentUserInfo: CurrentUserInfo? = null

    init {
        viewModelScope.launch(dispatcher) {
            try {
                currentUserInfo = getCurrentUserUseCase()
                _state.update { it.copy(domain = currentUserInfo?.domain ?: "") }

                getCategory()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        validationError = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
                    )
                }
            }
        }
    }

    fun setInitialNews(products: List<News>) {
        _state.update { it.copy(pagedNews = products.take(10)) }
    }

    fun onTabSelected(index: Int) {
        _state.update { it.copy(selectedTab = index) }
    }

    fun resetNavigation() {
        _state.update { it.copy(navEvent = ProductNavEvent.None) }
    }

    fun onItemNotificationSelected() {
        _state.update { it.copy(navEvent = NotificationNavEvent.GoToNotifications("", "")) }
    }

    fun onItemCarts() {
        _state.update { it.copy(navEvent = CartNavEvent.GoToCats) }
    }

    fun onItemNewSelected(product: News) {
        _state.update {
            it.copy(navEvent = NewsNavEvent.GoToNewDetail(
                id = product.id ?: "",
            ))
        }
    }

    fun getNews(idCategory: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getNews(user.isOfflineMode, idCategory, user.token).collectResponse(
                dispatcher = dispatcher,
                onLoading = { _state.update { it.copy(isLoading = true) } },
                onSuccess = { news -> _state.update { it.copy(news = news, isLoading = false) } },
                onError = { msg -> _state.update { it.copy(validationError = msg, isLoading = false) } }
            )
        }
    }

    fun getNewDetail( id: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getNewDetail(user.isOfflineMode,id, user.token).collectResponse(
                dispatcher = dispatcher,
                onLoading = {}, // Optional: handle if needed
                onSuccess = { detail -> _state.update { it.copy(newDetail = detail) } },
                onError = { msg -> _state.update { it.copy(validationError = msg) } }
            )
        }
    }

    fun getCategory() {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getCategory(user.isOfflineMode, user.token).collectResponse(
                dispatcher = dispatcher,
                onLoading = { _state.update { it.copy(isLoading = true) } },
                onSuccess = { list -> _state.update { it.copy(categoryNews = list, isLoading = false) } },
                onError = { msg -> _state.update { it.copy(validationError = msg, isLoading = false) } }
            )
        }
    }

    fun searchLocal(textSearch: String, list: List<News>) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            useCase.searchLocal(textSearch, list).collect { filteredList ->
                _state.update {
                    it.copy(
                        pagedNews = filteredList,
                        isLoading = false,
                        validationError = null
                    )
                }
            }
        }
    }
}
